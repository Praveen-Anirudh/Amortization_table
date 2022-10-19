package com.ults.amortization.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ults.amortization.model.MonthlyAmortizationSchedule;
import com.ults.amortization.model.Payment;

@Service
public class AmortizationService {

    @Autowired
    private PaymentService paymentService;

    public void initializeUnknownFields(MonthlyAmortizationSchedule monthlyAmortizationSchedule) {
        // extract required parameters
        Date startDate = monthlyAmortizationSchedule.getStartDate();
        double initialBalance = monthlyAmortizationSchedule.getInitialBalance();
        double interestRate = monthlyAmortizationSchedule.getInterestRate();
        int durationInMonths = monthlyAmortizationSchedule.getDurationInMonths();
        double futureValue = monthlyAmortizationSchedule.getFutureValue();
        int paymentType = monthlyAmortizationSchedule.getPaymentType();

        // compute monthly payment
        double monthlyPayment = paymentService.pmt(paymentService.getMonthlyInterestRate(interestRate),
                durationInMonths, initialBalance, futureValue, paymentType);
        monthlyAmortizationSchedule.setMonthlyPayment(monthlyPayment);

        // calculate detailed payment list
        List<Payment> paymentList = calculatePaymentList(startDate, initialBalance, durationInMonths, paymentType,
                interestRate, futureValue);
        monthlyAmortizationSchedule.addAllPayments(paymentList);
    }

    /**
     * Calculates the monthly payment and the list of payments given parameters.
     * 
     * @param startDate        loan start date
     * @param initialBalance   loan initial balance
     * @param durationInMonths loan duration in months
     * @param paymentType      loan payment type
     * @param interestRate     loan interest rate
     * @param futureValue      loan expected future value
     * @return List of payments and the monthly payment amount
     */
    public List<Payment> calculatePaymentList(Date startDate, double initialBalance, int durationInMonths,
            int paymentType, double interestRate, double futureValue) {
        List<Payment> paymentList = new ArrayList<Payment>();
        Date loopDate = startDate;
        double balance = initialBalance;
        double accumulatedInterest = 0;
        for (int paymentNumber = 1; paymentNumber <= durationInMonths; paymentNumber++) {
            if (paymentType == 0) {
                loopDate = addOneMonth(loopDate);
            }
            double principalPaid = paymentService.ppmt(paymentService.getMonthlyInterestRate(interestRate),
                    paymentNumber, durationInMonths, initialBalance, futureValue, paymentType);
            double interestPaid = paymentService.ipmt(paymentService.getMonthlyInterestRate(interestRate),
                    paymentNumber, durationInMonths, initialBalance, futureValue, paymentType);
            balance = balance + principalPaid;
            accumulatedInterest += interestPaid;

            Payment payment = new Payment(paymentNumber, loopDate, balance, principalPaid, interestPaid,
                    accumulatedInterest);

            paymentList.add(payment);

            if (paymentType == 1) {
                loopDate = addOneMonth(loopDate);
            }
        }
        return paymentList;
    }

    /**
     * Adds a month to a date, for purposes of putting a date on each Payment
     * object.
     * 
     * @param date any arbitrary date
     * @return Date a date one month later
     */
    private Date addOneMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

}
