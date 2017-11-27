package xyz.javista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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
import xyz.javista.web.dto.OrderDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
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


}
