package xyz.javista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import xyz.javista.config.AuditorAwareImpl;
import xyz.javista.core.domain.Order;
import xyz.javista.core.query.GetOrderListQuery;
import xyz.javista.core.repository.OrderRepository;
import xyz.javista.core.specification.OrderSpecification;
import xyz.javista.exception.OrderException;
import xyz.javista.mapper.OrderMapper;
import xyz.javista.web.command.CreateOrderCommand;
import xyz.javista.web.command.UpdateOrderCommand;
import xyz.javista.web.dto.OrderDTO;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

import static xyz.javista.exception.OrderException.FailReason.ORDER_NOT_EXIST;


@Service
@Transactional
public class OrderService {

    private static final String OPERATION_NOT_ALLOWED_MESSAGE = "Operation not allowed!";
    private static final String ORDER_EXPIRED_MESSAGE = "Order expired!";
    private static final String ORDER_NOT_EXISTS_MESSAGE = "Order not exists!";
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    AuditorAwareImpl auditorAware;

    public OrderDTO createOrder(CreateOrderCommand createOrderCommand) {
        Order entity = orderMapper.toEntity(createOrderCommand);
        return orderMapper.toDto(orderRepository.saveAndFlush(entity));
    }

    public OrderDTO getOrder(String orderId) throws OrderException {
        return orderMapper.toDto(getOrderIfExist(orderId));
    }

    public Page<OrderDTO> getOrders(GetOrderListQuery query) {
        OrderSpecification specification = new OrderSpecification(query);
        Page<Order> result = orderRepository.findAll(specification, query);
        return result.map(x -> orderMapper.toDto(x));
    }

    public void removeOrder(String orderId) throws OrderException {
        Order order = getOrderIfExist(orderId);
        canUpdateOrDelete(order);
        orderRepository.delete(order);
    }

    public OrderDTO updateOrder(UpdateOrderCommand updateOrderCommand) throws OrderException {
        Order order = getOrderIfExist(updateOrderCommand.getId());
        canUpdateOrDelete(order);
        order.setDescription(updateOrderCommand.getDescription());
        order.setRestaurantName(updateOrderCommand.getRestaurantName());
        order.setEndDatetime(LocalDateTime.parse(updateOrderCommand.getEndDatetime()));
        order.setUrl(updateOrderCommand.getUrl());
        return orderMapper.toDto(orderRepository.saveAndFlush(order));
    }

    private void canUpdateOrDelete(Order order) throws OrderException {
        if (!order.getCreatedBy().getLogin().equals(auditorAware.getCurrentAuditor().getLogin())) {
            throw new OrderException(OrderException.FailReason.NOT_ALLOWED, OPERATION_NOT_ALLOWED_MESSAGE);
        }
        if (order.getEndDatetime().isBefore(LocalDateTime.now())) {
            throw new OrderException(OrderException.FailReason.ORDER_EXPIRED, ORDER_EXPIRED_MESSAGE);
        }
    }


    private Order getOrderIfExist(String orderId) throws OrderException {
        Order order = orderRepository.findOne(UUID.fromString(orderId));
        if (order == null) {
            throw new OrderException(ORDER_NOT_EXIST, ORDER_NOT_EXISTS_MESSAGE);
        }
        return order;
    }


}
