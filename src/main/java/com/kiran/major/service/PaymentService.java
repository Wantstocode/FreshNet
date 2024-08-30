package com.kiran.major.service;

import com.kiran.major.model.Payment;

import java.util.Map;

public interface PaymentService {

    boolean createPayment(Map<String, String> paymentDetails);
}
