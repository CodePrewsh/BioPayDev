package com.grimrepos.biopay.service;

import com.grimrepos.biopay.dto.PaymentRequest;
import com.grimrepos.biopay.dto.PaymentResponse;
import com.grimrepos.biopay.entity.Payment;

public interface PaymentService {
    //PaymentResponse processPayment(PaymentRequest request);
    PaymentResponse makePayment(PaymentRequest request);
}
