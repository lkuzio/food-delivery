package xyz.javista.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import xyz.javista.exception.OrderLineItemException;
import xyz.javista.service.OrderLineItemService;
import xyz.javista.web.command.CreateOrderCommand;
import xyz.javista.web.command.CreateOrderLineItemCommand;
import xyz.javista.core.query.GetOrderListQuery;
import xyz.javista.web.dto.OrderDTO;
import xyz.javista.service.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderLineItemService orderLineItemService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<OrderDTO> getOrders(@RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                    @RequestParam(name = "limit", defaultValue = "0", required = false) int limit,
                                    @RequestParam(name = "endDate", required = false) String endDate) {
        GetOrderListQuery query = new GetOrderListQuery(size, limit);
        query.setEndDate(endDate);
        return orderService.getOrders(query);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    public OrderDTO getOrder(@PathVariable(name = "orderId") String orderId) {
        return orderService.getOrder(orderId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{orderId}/lineItem/{orderItemId}")
    public void removeOrderItem(@PathVariable(name = "orderId") String orderId,
                                @PathVariable(name = "orderItemId") String orderItemId) throws OrderLineItemException {
        orderService.removeOrderItem(orderId, orderItemId);
    }


    @RequestMapping(method = RequestMethod.POST)
    public OrderDTO createOrder(@RequestBody @Valid CreateOrderCommand createOrderCommand) {
        return orderService.createOrder(createOrderCommand);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{orderId}/lineItem")
    public OrderDTO createOrderItem(@PathVariable(name = "orderId") String orderId,
                                @RequestBody @Valid CreateOrderLineItemCommand createOrderLineItemCommand) throws OrderLineItemException {
        return orderLineItemService.createOrderLineItem(createOrderLineItemCommand, orderId);
    }

}
