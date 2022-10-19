package com.ults.amortization.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    private Long id;
    private int paymentNumber;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date paymentDate;
    private double balance;
    private double principalPaid;
    private double interestPaid;
    private double accumulatedInterest;

    public Payment(int paymentNumber, Date paymentDate, double balance, double principalPaid, double interestPaid,
            double accumulatedInterest) {
        setPaymentNumber(paymentNumber);
        setPaymentDate(paymentDate);
        setBalance(balance);
        setPrincipalPaid(principalPaid);
        setInterestPaid(interestPaid);
        setAccumulatedInterest(accumulatedInterest);
    }
}
