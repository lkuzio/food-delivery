package xyz.javista.web.dto;

import java.util.UUID;

public class OrderLineNumberDTO {

    private UUID id;
    private Double price;
    private Boolean isPaid;
    private UserDTO purchaser;
    private UUID orderId;
    private String dishName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public UserDTO getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(UserDTO purchaser) {
        this.purchaser = purchaser;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
