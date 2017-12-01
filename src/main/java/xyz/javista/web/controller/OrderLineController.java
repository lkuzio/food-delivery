package xyz.javista.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.javista.exception.OrderLineItemException;
import xyz.javista.service.OrderLineItemService;
import xyz.javista.web.command.CreateOrderLineItemCommand;
import xyz.javista.web.command.UpdateOrderLineItemCommand;
import xyz.javista.web.dto.OrderDTO;
import xyz.javista.web.dto.OrderLineNumberDTO;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders/{orderId}/lineItem")
public class OrderLineController {


    @Autowired
    OrderLineItemService orderLineItemService;

    @RequestMapping(method = RequestMethod.POST)
    public OrderDTO createOrderItem(@PathVariable(name = "orderId") String orderId,
                                    @RequestBody @Valid CreateOrderLineItemCommand createOrderLineItemCommand) throws OrderLineItemException {
        return orderLineItemService.createOrderLineItem(createOrderLineItemCommand, orderId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{orderItemId}")
    public void removeOrderItem(@PathVariable(name = "orderId") String orderId,
                                @PathVariable(name = "orderItemId") String orderItemId) throws OrderLineItemException {
        orderLineItemService.removeOrderItem(orderId, orderItemId);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "{orderItemId}")
    public OrderLineNumberDTO updateOrderItem(@PathVariable(name = "orderId") String orderId,
                                              @PathVariable(name = "orderItemId") String orderItemId,
                                              @RequestBody @Valid UpdateOrderLineItemCommand updateOrderLineItemCommand) throws OrderLineItemException {
        updateOrderLineItemCommand.setOrderId(UUID.fromString(orderId));
        updateOrderLineItemCommand.setOrderLineItemId(UUID.fromString(orderItemId));
        return orderLineItemService.updateOrderItem(updateOrderLineItemCommand);
    }

}
