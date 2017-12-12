package xyz.javista.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import xyz.javista.core.query.GetOrderListQuery;
import xyz.javista.exception.OrderException;
import xyz.javista.service.OrderLineItemService;
import xyz.javista.service.OrderService;
import xyz.javista.web.command.CreateOrderCommand;
import xyz.javista.web.command.UpdateOrderCommand;
import xyz.javista.web.dto.OrderDTO;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderLineItemService orderLineItemService;

    @RequestMapping(method = RequestMethod.POST)
    public OrderDTO createOrder(@RequestBody @Valid CreateOrderCommand createOrderCommand) {
        return orderService.createOrder(createOrderCommand);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<OrderDTO> getOrders(@RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                    @RequestParam(name = "limit", defaultValue = "0", required = false) int limit,
                                    @RequestParam(name = "endDate", required = false) String endDate) {
        GetOrderListQuery query = new GetOrderListQuery(size, limit);
        query.setEndDate(endDate);
        return orderService.getOrders(query);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    public OrderDTO getOrder(@PathVariable(name = "orderId") String orderId) throws OrderException {
        return orderService.getOrder(orderId);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{orderId}")
    public void removeOrder(@PathVariable(name = "orderId") String orderId) throws OrderException {
        orderService.removeOrder(orderId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{orderId}")
    public OrderDTO updateOrder(@PathVariable(name = "orderId") String orderId,
                                @RequestBody @Valid UpdateOrderCommand updateOrderCommand) throws OrderException {
        updateOrderCommand.setId(orderId);
        return orderService.updateOrder(updateOrderCommand);
    }


}
