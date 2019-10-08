package com.microservices.controller;

import com.microservices.model.AddPayment;
import com.microservices.model.Payment;
import com.microservices.service.PaymentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("payments")
public class PaymentController {
    private PaymentService paymentService;
    private static final Logger log = Logger.getLogger(PaymentController.class);

    @Autowired
    public PaymentController (PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewPayment(@Valid @RequestBody AddPayment addPayment) {
        try {
            paymentService.addPayment(addPayment);
            log.info("New payment was added: " + addPayment.toString());
        } catch (SQLException e) {
            log.error("Error with adding the payment: " + e.toString());
        }
    }

    @GetMapping
    public ArrayList<Payment> showAllPayments() {
        try {
            ArrayList<Payment> temp = paymentService.showAllPayments();
            log.info("All existing payments were found");
            return temp;
        } catch (SQLException e) {
            log.error("Error with finding payments: " +  e.toString());
            return null;
        }
    }

    @GetMapping(value = "id/{id}")
    public Payment getItemById (@PathVariable int id) {
        try {
            Payment temp = paymentService.getPaymentById(id);
            log.info("Payment with id = " + id + " was found");
            return temp;
        } catch (SQLException e) {
            log.error("Payment with id = " + id + "was not found: " + e.toString());
            return null;
        }
    }

    @GetMapping(value = "{orderId}")
    public ArrayList<Payment> getPaymentsByOrderId(@PathVariable int orderId) {
        try {
            ArrayList<Payment> temp =  paymentService.getPaymentsByOrderId(orderId);
            log.info("All payments for order with id = " +  orderId + " was found");
            return temp;
        } catch (SQLException e) {
            log.error("All payments for order with id = " +  orderId + " was not found: " + e.toString());
            return null;
        }
    }


}