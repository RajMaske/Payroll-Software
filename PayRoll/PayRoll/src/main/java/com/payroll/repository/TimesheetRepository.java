package com.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payroll.entity.Timesheet;
@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

}
