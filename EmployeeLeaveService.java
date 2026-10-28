package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.EmployeeLeave;

@Service
public class EmployeeLeaveService {

    private final List<EmployeeLeave> leaves = new ArrayList<>();
    private long nextId = 1;

    //   Apply for leave
    
    public EmployeeLeave applyLeave(EmployeeLeave leave) {
        leave.setEmployeeId(nextId++);
        leaves.add(leave);
        return leave;
    }

    //   View all leave applications
    
    public List<EmployeeLeave> getAllLeaves() {
        return new ArrayList<>(leaves);
    }

    //     Find leave application by ID
    
    public EmployeeLeave getLeaveById(Long id) {
        return leaves.stream()
                .filter(leave -> leave.getEmployeeId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //    Search by employee name
    
    public List<EmployeeLeave> searchByName(String name) {
        return leaves.stream()
                .filter(leave -> leave.getEmployeeName()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();
    }

    //    Update a leave application
    
    public EmployeeLeave updateLeave(Long id,
                                     EmployeeLeave updatedLeave) {
        EmployeeLeave existing = getLeaveById(id);

        if (existing == null) {
            return null;
        }

        updatedLeave.setEmployeeId(id);

        int index = leaves.indexOf(existing);
        leaves.set(index, updatedLeave);

        return updatedLeave;
    }

    //  Update leave status
    
    public EmployeeLeave updateStatus(Long id, String status) {
        EmployeeLeave leave = getLeaveById(id);

        if (leave == null) {
            return null;
        }

        leave.setLeaveStatus(status);
        return leave;
    }

    //   Delete leave application
    
    public boolean deleteLeave(Long id) {
        EmployeeLeave leave = getLeaveById(id);

        if (leave == null) {
            return false;
        }

        leaves.remove(leave);
        return true;
    }
}