package xyz.javista.web.command;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateOrderLineItemCommand {
    @NotNull(message = "The dish price cannot be null")
    private Double price;
    private boolean isPaid;
    private UUID purchaserId;
    @NotNull(message = "The dish name cannot be empty")
    @Length(min = 1, max = 255, message = "The dish name must be between 1 and 255 characters")
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
