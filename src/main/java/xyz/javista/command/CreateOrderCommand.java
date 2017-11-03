package xyz.javista.command;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

public class CreateOrderCommand {

    @NotNull
    @Length(min = 1, max = 255, message = "The restaurant name must be between 1 and 255 characters")
    private String restaurantName;

    @Length(min = 1, max = 255, message = "The description must be between 1 and 255 characters")
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private String endDatetime;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }
}
