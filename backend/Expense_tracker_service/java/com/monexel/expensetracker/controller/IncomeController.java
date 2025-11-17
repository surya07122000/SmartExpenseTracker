package com.monexel.expensetracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monexel.expensetracker.request.IncomeRequest;
import com.monexel.expensetracker.response.IncomeResponse;
import com.monexel.expensetracker.service.IncomeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
	

	@Autowired
    private IncomeService incomeService;

    @PostMapping("/addIncome")
    public ResponseEntity<IncomeResponse> addIncome(@RequestBody @Valid IncomeRequest request) {
        return new ResponseEntity<>(incomeService.addIncome(request), HttpStatus.CREATED);
    }

    @PutMapping("/updateIncome/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(@PathVariable Long id, @RequestBody @Valid IncomeRequest request) {
        return ResponseEntity.ok(incomeService.updateIncome(id, request));
    }

    @DeleteMapping("/deleteIncome/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("getIncomeById/{id}")
    public ResponseEntity<IncomeResponse> getIncomeById(@PathVariable Long id) {
        return ResponseEntity.ok(incomeService.getIncomeById(id));
    }

    @GetMapping("/getAllIncomeByUser/{userId}")
    public ResponseEntity<List<IncomeResponse>> getAllIncomesByUser(@PathVariable Long userId,
    		@RequestParam(required = false) LocalDate startDate,
    		@RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(incomeService.getAllIncomesByUser(userId, startDate, endDate));
    }


}
