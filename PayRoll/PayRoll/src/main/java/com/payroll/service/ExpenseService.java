package com.payroll.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.entity.Expense;
import com.payroll.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expense) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Expense existingExpense = optionalExpense.get();
            existingExpense.setType(expense.getType());
            existingExpense.setAmount(expense.getAmount());
            existingExpense.setDate(expense.getDate());
            existingExpense.setStatus(expense.getStatus());
            existingExpense.setDescription(expense.getDescription());
            return expenseRepository.save(existingExpense);
        } else {
            throw new RuntimeException("Expense not found with id " + id);
        }
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Expense approveExpense(Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setStatus("Approved");
            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("Expense not found with id " + id);
        }
    }

    public Expense rejectExpense(Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setStatus("Rejected");
            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("Expense not found with id " + id);
        }
    }
}
