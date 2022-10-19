package com.ults.amortization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ults.amortization.model.MonthlyAmortizationSchedule;
import com.ults.amortization.model.Payment;
import com.ults.amortization.repository.DataRepository;
import com.ults.amortization.service.AmortizationService;
import com.ults.amortization.service.PaymentService;

@RestController
@RequestMapping("/api/")
public class MortgageCalculatorController {

    @Autowired
    private DataRepository dataRepository;
    @Autowired
    private AmortizationService amortizationService;
    private MonthlyAmortizationSchedule monthlyAmortizationSchedule;

    @PostMapping("user")
    public MonthlyAmortizationSchedule userDetails(
            @RequestBody MonthlyAmortizationSchedule monthlyAmortizationSchedule) {
        return dataRepository.save(monthlyAmortizationSchedule);
    }

    @GetMapping("payment")
    public double calculatePayments(@RequestBody MonthlyAmortizationSchedule monthlyAmortizationSchedule) {
        return monthlyAmortizationSchedule.getMonthlyPayment();
    }

    @GetMapping("pay")
    public List<Payment> calculatePayment(@RequestBody AmortizationService amortizationService) {
        return amortizationService.calculatePaymentList(null, monthlyAmortizationSchedule.getInitialBalance(),
                monthlyAmortizationSchedule.getDurationInMonths(), monthlyAmortizationSchedule.getPaymentType(),
                monthlyAmortizationSchedule.getInterestRate(), monthlyAmortizationSchedule.getFutureValue());

    }

}
