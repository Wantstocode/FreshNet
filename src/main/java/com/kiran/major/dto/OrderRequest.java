package com.kiran.major.dto;

import com.kiran.major.model.OrderAddress;
import com.kiran.major.model.Product;
import com.kiran.major.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;

@Data
@ToString
public class OrderRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNo;

    private String address;

    private String city;

    private String pinCode;

    private String additionalInfo;

    private String PaymentType;
}
