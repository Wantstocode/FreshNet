package com.kiran.major.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderReferenceId;

    private Date orderDate;

    @ManyToOne
    private Product product;

    private Double price;

    private Double Quantity;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderAddress orderAddress;

    private String orderStatus;

    private String paymentType;

}
