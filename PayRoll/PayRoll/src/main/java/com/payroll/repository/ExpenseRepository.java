package com.payroll.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payroll.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}

