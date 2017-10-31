package xyz.javista.core.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class OrderLineNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Double price;
    @Column(name = "paid")
    private Boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User purchaser;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

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

    public User getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
