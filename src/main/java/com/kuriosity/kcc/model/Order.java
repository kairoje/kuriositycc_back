package com.kuriosity.kcc.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private Double orderTotal;

    @Column(nullable = false)
    private String status;

    public Order(User user, List<Product> products, Date orderDate, String status) {
        this.user = user;
        this.products = products;
        this.orderDate = orderDate;
        this.status = status;
        this.orderTotal = calculateOrderTotal();
    }

    public Order() {
    }

    private Double calculateOrderTotal() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.orderTotal = calculateOrderTotal();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                ", orderDate=" + orderDate +
                ", orderTotal=" + orderTotal +
                ", status='" + status + '\'' +
                '}';
    }
}
