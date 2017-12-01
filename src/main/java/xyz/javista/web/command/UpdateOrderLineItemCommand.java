package xyz.javista.web.command;

import java.util.Optional;
import java.util.UUID;

public class UpdateOrderLineItemCommand {
    private UUID orderId;
    private UUID orderLineItemId;
    private Optional<Double> price;
    private Optional<Boolean> paid;
    private Optional<String> dishName;

    public UpdateOrderLineItemCommand() {
        this.price = Optional.empty();
        this.paid = Optional.empty();
        this.dishName = Optional.empty();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderLineItemId() {
        return orderLineItemId;
    }

    public void setOrderLineItemId(UUID orderLineItemId) {
        this.orderLineItemId = orderLineItemId;
    }

    public Optional<Double> getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = Optional.of(price);
    }

    public Optional<Boolean> getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = Optional.of(paid);
    }

    public Optional<String> getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = Optional.of(dishName);
    }
}
