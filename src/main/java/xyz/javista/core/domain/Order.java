package xyz.javista.core.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Length(min = 1, max = 255, message = "The restaurant name must be between 1 and 255 characters")
    private String restaurantName;

    @Length(min = 1, max = 255, message = "The description must be between 1 and 255 characters")
    private String description;

    @NotNull
    @Column(name = "end_datetime")
    private LocalDateTime endDatetime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "order")
    private List<OrderLineNumber> orderLineNumberList;

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<OrderLineNumber> getOrderLineNumberList() {
        return orderLineNumberList;
    }

    public void setOrderLineNumberList(List<OrderLineNumber> orderLineNumberList) {
        this.orderLineNumberList = orderLineNumberList;
    }
}
