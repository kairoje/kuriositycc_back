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

    public Order(Long id, User user, List<Product> products, Date orderDate, Double orderTotal, String status) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.status = status;
    }

    public Order() {
    }


}
