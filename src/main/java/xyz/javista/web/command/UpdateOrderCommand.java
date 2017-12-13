package xyz.javista.web.command;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

public class UpdateOrderCommand {

    @NotNull
    @Length(min = 1, max = 255, message = "The restaurant name must be between 1 and 255 characters")
    private String restaurantName;

    @Length(max = 65536, message = "The restaurant menu url must be between 0 and 65 536")
    private String url;

    @Length(min = 1, max = 255, message = "The description must be between 1 and 255 characters")
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private String endDatetime;

    private String id;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
