package xyz.javista.web.command;

import java.util.UUID;

public class CreateOrderLineItemCommand {
    private Double price;
    private boolean isPaid;
    private UUID purchaserId;
    private String dishName;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public UUID getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(UUID purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}
