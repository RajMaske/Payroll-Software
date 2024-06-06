package com.payroll.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.entity.Salary;
import com.payroll.repository.SalaryRepository;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public Salary createSalary(Salary salary) {
        calculateNetSalary(salary);
        return salaryRepository.save(salary);
    }

    public Salary updateSalary(Long id, Salary salary) {
        Optional<Salary> optionalSalary = salaryRepository.findById(id);
        if (optionalSalary.isPresent()) {
            Salary existingSalary = optionalSalary.get();
            existingSalary.setEmployeeId(salary.getEmployeeId());
            existingSalary.setBasicSalary(salary.getBasicSalary());
            existingSalary.setPf(salary.getPf());
            existingSalary.setIncomeTax(salary.getIncomeTax());
            existingSalary.setLateDeductions(salary.getLateDeductions());
            existingSalary.setOvertimePay(salary.getOvertimePay());
            calculateNetSalary(existingSalary);
            return salaryRepository.save(existingSalary);
        } else {
            throw new RuntimeException("Salary not found with id " + id);
        }
    }

    private void calculateNetSalary(Salary salary) {
        double deductions = salary.getPf() + salary.getIncomeTax() + salary.getLateDeductions();
        double earnings = salary.getBasicSalary() + salary.getOvertimePay();
        salary.setNetSalary(earnings - deductions);
    }

    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }
}

