package com.payroll.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payroll.entity.Leave;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
}
