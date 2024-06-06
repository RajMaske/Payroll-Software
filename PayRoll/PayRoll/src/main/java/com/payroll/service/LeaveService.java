package com.payroll.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.entity.Leave;
import com.payroll.repository.LeaveRepository;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Leave createLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    public Leave updateLeave(Long id, Leave leave) {
        Optional<Leave> optionalLeave = leaveRepository.findById(id);
        if (optionalLeave.isPresent()) {
            Leave existingLeave = optionalLeave.get();
            existingLeave.setEmployeeId(leave.getEmployeeId());
            existingLeave.setStartDate(leave.getStartDate());
            existingLeave.setEndDate(leave.getEndDate());
            existingLeave.setLeaveType(leave.getLeaveType());
            existingLeave.setStatus(leave.getStatus());
            existingLeave.setLeaveBalance(leave.getLeaveBalance());
            return leaveRepository.save(existingLeave);
        } else {
            throw new RuntimeException("Leave not found with id " + id);
        }
    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }

    public Leave approveLeave(Long id) {
        Optional<Leave> optionalLeave = leaveRepository.findById(id);
        if (optionalLeave.isPresent()) {
            Leave leave = optionalLeave.get();
            leave.setStatus("Approved");
            return leaveRepository.save(leave);
        } else {
            throw new RuntimeException("Leave not found with id " + id);
        }
    }

    public Leave rejectLeave(Long id) {
        Optional<Leave> optionalLeave = leaveRepository.findById(id);
        if (optionalLeave.isPresent()) {
            Leave leave = optionalLeave.get();
            leave.setStatus("Rejected");
            return leaveRepository.save(leave);
        } else {
            throw new RuntimeException("Leave not found with id " + id);
        }
    }
}

