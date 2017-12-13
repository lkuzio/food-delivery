package xyz.javista.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderDTO {

    private UUID id;
    private String restaurantName;
    private String url;
    private String description;
    private LocalDateTime endDatetime;
    private UserDTO author;
    private List<OrderLineNumberDTO> orderLineNumberList;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public List<OrderLineNumberDTO> getOrderLineNumberList() {
        return orderLineNumberList;
    }

    public void setOrderLineNumberList(List<OrderLineNumberDTO> orderLineNumberList) {
        this.orderLineNumberList = orderLineNumberList;
    }
}
