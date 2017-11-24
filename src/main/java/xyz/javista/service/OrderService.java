package xyz.javista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import xyz.javista.core.domain.Order;
import xyz.javista.core.domain.OrderLineNumber;
import xyz.javista.core.query.GetOrderListQuery;
import xyz.javista.core.repository.OrderLineNumberRepository;
import xyz.javista.core.repository.OrderRepository;
import xyz.javista.core.specification.OrderSpecification;
import xyz.javista.exception.OrderLineItemException;
import xyz.javista.mapper.OrderMapper;
import xyz.javista.web.command.CreateOrderCommand;
import xyz.javista.web.dto.OrderDTO;

import java.util.UUID;

import static xyz.javista.exception.OrderLineItemException.FailReason.ORDER_NOT_EXIST;

@Service
public class OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderLineNumberRepository orderLineNumberRepository;


    public Page<OrderDTO> getOrders(GetOrderListQuery query) {
        OrderSpecification specification = new OrderSpecification(query);
        Page<Order> result = orderRepository.findAll(specification, query);
        return result.map(x -> orderMapper.toDto(x));
    }

    public OrderDTO createOrder(CreateOrderCommand createOrderCommand) {
        Order entity = orderMapper.toEntity(createOrderCommand);
        return orderMapper.toDto(orderRepository.saveAndFlush(entity));
    }

    public OrderDTO getOrder(String orderId) {
        return orderMapper.toDto(orderRepository.findOne(UUID.fromString(orderId)));
    }

    public void removeOrderItem(String orderId, String orderItemId) throws OrderLineItemException {
        OrderLineNumber orderItem = orderLineNumberRepository.findOne(UUID.fromString(orderItemId));
        if (orderItem.getOrder().getId().equals(UUID.fromString(orderId))) {
            orderLineNumberRepository.delete(orderItem);
        } else {
            throw new OrderLineItemException(ORDER_NOT_EXIST);
        }
    }
}
