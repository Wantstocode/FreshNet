package com.kiran.major.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonIgnore
    @ManyToOne
    private Product product;

    private Double price;

    private Double Quantity;


    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    private OrderAddress orderAddress;

    private String orderStatus;

    private String paymentType;

    private String RezorPayOrderId;

//    @ManyToOne(cascade = CascadeType.ALL)
//    private Payment payment;

}
