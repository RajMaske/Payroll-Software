package com.payroll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.entity.Timesheet;
import com.payroll.repository.TimesheetRepository;
@Service
public class TimesheetService {

    @Autowired
    private TimesheetRepository timesheetRepository;

    public List<Timesheet> getAllTimesheets() {
        return timesheetRepository.findAll();
    }

    public Timesheet createTimesheet(Timesheet timesheet) {
        calculateLateAndOvertime(timesheet);
        return timesheetRepository.save(timesheet);
    }

    public Timesheet updateTimesheet(Long id, Timesheet timesheet) {
        Optional<Timesheet> optionalTimesheet = timesheetRepository.findById(id);
        if (optionalTimesheet.isPresent()) {
            Timesheet existingTimesheet = optionalTimesheet.get();
            existingTimesheet.setEmployeeId(timesheet.getEmployeeId());
            existingTimesheet.setDate(timesheet.getDate());
            existingTimesheet.setCheckInTime(timesheet.getCheckInTime());
            existingTimesheet.setCheckOutTime(timesheet.getCheckOutTime());
            existingTimesheet.setPresent(timesheet.isPresent());
            existingTimesheet.setLate(timesheet.isLate());
            existingTimesheet.setOvertimeHours(timesheet.getOvertimeHours());
            existingTimesheet.setShift(timesheet.getShift());
            existingTimesheet.setWeeklyOff(timesheet.isWeeklyOff());
            existingTimesheet.setShiftStartTime(timesheet.getShiftStartTime());
            existingTimesheet.setShiftEndTime(timesheet.getShiftEndTime());
            calculateLateAndOvertime(existingTimesheet);
            return timesheetRepository.save(existingTimesheet);
        } else {
            throw new RuntimeException("Timesheet not found with id " + id);
        }
    }

    private void calculateLateAndOvertime(Timesheet timesheet) {
        if (timesheet.getCheckInTime().isAfter(timesheet.getShiftStartTime())) {
            timesheet.setLate(true);
        } else {
            timesheet.setLate(false);
        }

        if (timesheet.getCheckOutTime().isAfter(timesheet.getShiftEndTime())) {
            timesheet.setOvertimeHours(timesheet.getCheckOutTime().getHour() - timesheet.getShiftEndTime().getHour());
        } else {
            timesheet.setOvertimeHours(0);
        }
    }

    public void deleteTimesheet(Long id) {
        timesheetRepository.deleteById(id);
    }

    public Timesheet assignShift(Long id, String shift) {
        Optional<Timesheet> optionalTimesheet = timesheetRepository.findById(id);
        if (optionalTimesheet.isPresent()) {
            Timesheet timesheet = optionalTimesheet.get();
            timesheet.setShift(shift);
            return timesheetRepository.save(timesheet);
        } else {
            throw new RuntimeException("Timesheet not found with id " + id);
        }
    }

    public Timesheet markWeeklyOff(Long id, boolean isWeeklyOff) {
        Optional<Timesheet> optionalTimesheet = timesheetRepository.findById(id);
        if (optionalTimesheet.isPresent()) {
            Timesheet timesheet = optionalTimesheet.get();
            timesheet.setWeeklyOff(isWeeklyOff);
            return timesheetRepository.save(timesheet);
        } else {
            throw new RuntimeException("Timesheet not found with id " + id);
        }
    }
}
