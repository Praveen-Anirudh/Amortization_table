package com.ults.amortization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Loan_Details")
public class MonthlyAmortizationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "Start_Date")
    private Date startDate;
    @Column(name = "Initial_Balance")
    private double initialBalance;
    @Column(name = "Interest_Rate")
    private double interestRate;
    @Column(name = "Months")
    private int durationInMonths;
    private double futureValue;
    private int paymentType;
    @Column(name = "Monthly_Payment")
    private double monthlyPayment;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "MaC_id", referencedColumnName = "id")
    private List<Payment> paymentList = new ArrayList<Payment>();

    public void addAllPayments(List<Payment> paymentList) {
        this.paymentList.addAll(paymentList);
    }
}
