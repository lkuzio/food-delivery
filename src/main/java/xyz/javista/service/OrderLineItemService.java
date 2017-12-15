package xyz.javista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.javista.config.AuditorAwareImpl;
import xyz.javista.core.domain.Order;
import xyz.javista.core.domain.OrderLineNumber;
import xyz.javista.core.domain.User;
import xyz.javista.core.repository.OrderLineNumberRepository;
import xyz.javista.core.repository.OrderRepository;
import xyz.javista.core.repository.UserRepository;
import xyz.javista.exception.DateTimeConverterException;
import xyz.javista.exception.OrderLineItemException;
import xyz.javista.mapper.OrderLineNumberMapper;
import xyz.javista.mapper.OrderMapper;
import xyz.javista.web.command.CreateOrderLineItemCommand;
import xyz.javista.web.command.UpdateOrderLineItemCommand;
import xyz.javista.web.dto.OrderDTO;
import xyz.javista.web.dto.OrderLineNumberDTO;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class OrderLineItemService {

    private static final String ORDER_NOT_EXISTS_MESSAGE = "Order not exists!";
    private static final String ORDER_EXPIRED_MESSAGE = "Order expired!";
    private static final String ORDER_ITEM_NOT_EXISTS_MESSAGE = "Order item not exists!";
    private static final String OPERATION_NOT_ALLOWED_MESSAGE = "Operation not allowed!";
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderLineNumberMapper orderLineNumberMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderLineNumberRepository orderLineNumberRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    AuditorAwareImpl auditorAware;

    public OrderDTO createOrderLineItem(CreateOrderLineItemCommand createOrderLineItemCommand, String orderId) throws OrderLineItemException, DateTimeConverterException {
        UUID parentOrderId;
        try {
            parentOrderId = UUID.fromString(orderId);
        } catch (IllegalArgumentException ex) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_NOT_EXIST, ORDER_NOT_EXISTS_MESSAGE);
        }
        Order order = orderRepository.findOne(parentOrderId);
        if (order == null) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_NOT_EXIST, ORDER_NOT_EXISTS_MESSAGE);
        }

        User user = auditorAware.getCurrentAuditor();
        if (!order.getCreatedBy().getLogin().equals(user.getLogin()) && order.getEndDatetime().isBefore(LocalDateTime.now())) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_EXPIRED, ORDER_EXPIRED_MESSAGE);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = ((User) authentication.getPrincipal()).getId();
        User purchaser = userRepository.findOne(userId);

        OrderLineNumber orderLineNumber = orderLineNumberMapper.toEntity(createOrderLineItemCommand);
        orderLineNumber.setOrder(order);
        orderLineNumber.setPurchaser(purchaser);
        orderLineNumberRepository.saveAndFlush(orderLineNumber);
        return orderMapper.toDto(orderRepository.findOne(parentOrderId));
    }


    public void removeOrderItem(String orderId, String orderItemId) throws OrderLineItemException {
        OrderLineNumber orderItem = orderLineNumberRepository.findOne(UUID.fromString(orderItemId));
        canUpdateOrDelete(orderItem);
        if (orderItem.getOrder().getId().equals(UUID.fromString(orderId))) {
            orderLineNumberRepository.delete(orderItem);
        } else {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_NOT_EXIST, ORDER_NOT_EXISTS_MESSAGE);
        }
    }

    public OrderLineNumberDTO updateOrderItem(UpdateOrderLineItemCommand updateOrderLineItemCommand) throws OrderLineItemException, DateTimeConverterException {
        OrderLineNumber orderLineNumber = orderLineNumberRepository.findOne(updateOrderLineItemCommand.getOrderLineItemId());
        if (orderLineNumber == null) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_ITEM_NOT_EXIST, ORDER_ITEM_NOT_EXISTS_MESSAGE);
        }
        canUpdateOrDelete(orderLineNumber);

        if (updateOrderLineItemCommand.getDishName().isPresent()) {
            orderLineNumber.setDishName(updateOrderLineItemCommand.getDishName().get());
        }
        if (updateOrderLineItemCommand.getPrice().isPresent()) {
            orderLineNumber.setPrice(updateOrderLineItemCommand.getPrice().get());
        }
        if (updateOrderLineItemCommand.getPaid().isPresent()) {
            orderLineNumber.setPaid(updateOrderLineItemCommand.getPaid().get());
        }
        return orderLineNumberMapper.toDTO(orderLineNumberRepository.saveAndFlush(orderLineNumber));

    }

    private void canUpdateOrDelete(OrderLineNumber orderLineNumber) throws OrderLineItemException {
        User user = auditorAware.getCurrentAuditor();
        if (!orderLineNumber.getOrder().getCreatedBy().getLogin().equals(user.getLogin()) && orderLineNumber.getOrder().getEndDatetime().isBefore(LocalDateTime.now())) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_EXPIRED, ORDER_EXPIRED_MESSAGE);
        }
        if (!orderLineNumber.getPurchaser().getLogin().equals(user.getLogin())) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.NOT_ALLOWED, OPERATION_NOT_ALLOWED_MESSAGE);
        }
    }
}
