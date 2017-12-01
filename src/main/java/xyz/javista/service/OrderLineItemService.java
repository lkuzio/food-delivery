package xyz.javista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.javista.core.domain.Order;
import xyz.javista.core.domain.OrderLineNumber;
import xyz.javista.core.domain.User;
import xyz.javista.core.repository.OrderLineNumberRepository;
import xyz.javista.core.repository.OrderRepository;
import xyz.javista.core.repository.UserRepository;
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

    public OrderDTO createOrderLineItem(CreateOrderLineItemCommand createOrderLineItemCommand, String orderId) throws OrderLineItemException {
        UUID parentOrderId;
        try {
            parentOrderId = UUID.fromString(orderId);
        } catch (IllegalArgumentException ex) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_NOT_EXIST);
        }
        Order order = orderRepository.findOne(parentOrderId);
        if (order == null) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_NOT_EXIST);
        }

        if (order.getEndDatetime().isBefore(LocalDateTime.now())) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_EXPIRED);
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
        if (orderItem.getOrder().getId().equals(UUID.fromString(orderId))) {
            orderLineNumberRepository.delete(orderItem);
        } else {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_NOT_EXIST);
        }
    }

    public OrderLineNumberDTO updateOrderItem(UpdateOrderLineItemCommand updateOrderLineItemCommand) throws OrderLineItemException {
        OrderLineNumber orderLineNumber = orderLineNumberRepository.findOne(updateOrderLineItemCommand.getOrderLineItemId());
        if (orderLineNumber == null) {
            throw new OrderLineItemException(OrderLineItemException.FailReason.ORDER_ITEM_NOT_EXIST);
        }
        if(updateOrderLineItemCommand.getDishName().isPresent()) {
            orderLineNumber.setDishName(updateOrderLineItemCommand.getDishName().get());
        }
        if(updateOrderLineItemCommand.getPrice().isPresent()) {
            orderLineNumber.setPrice(updateOrderLineItemCommand.getPrice().get());
        }
        if(updateOrderLineItemCommand.getPaid().isPresent()){
            orderLineNumber.setPaid(updateOrderLineItemCommand.getPaid().get());
        }
        return orderLineNumberMapper.toDTO(orderLineNumberRepository.saveAndFlush(orderLineNumber));

    }
}
