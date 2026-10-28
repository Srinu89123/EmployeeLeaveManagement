package com.example.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.model.EmployeeLeave;
import com.example.service.EmployeeLeaveService;

@RestController
@RequestMapping("/leaves")
@Validated
public class EmployeeLeaveController {

    private final EmployeeLeaveService service;

    public EmployeeLeaveController(EmployeeLeaveService service) {
        this.service = service;
    }

    //  Apply for leave
    
    @PostMapping("/apply")
    public ResponseEntity<EmployeeLeave> applyLeave(
            @Valid @RequestBody EmployeeLeave leave) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.applyLeave(leave));
    }

    //   View all applications
    
    @GetMapping
    public List<EmployeeLeave> getAllLeaves() {
        return service.getAllLeaves();
    }

    //   Find application by ID
    
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeLeave> getLeaveById( @PathVariable Long id)
    {

        EmployeeLeave leave = service.getLeaveById(id);

        if (leave == null)
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(leave);
    }

    //    Search by employee name
    
    @GetMapping("/search")
    public List<EmployeeLeave> searchByName(@RequestParam String name) 
    {

        return service.searchByName(name);
    }

    //    Update a leave application
    
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeLeave> updateLeave(@PathVariable Long id,
            @Valid @RequestBody EmployeeLeave updatedLeave)
    {

        EmployeeLeave result = service.updateLeave(id, updatedLeave);

        if (result == null) 
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    //    Approve or reject a leave application
    
    @PatchMapping("/status/{id}")
    public ResponseEntity<EmployeeLeave> updateStatus(
            @PathVariable Long id,
            @RequestParam
            @Pattern( regexp = "PENDING|APPROVED|REJECTED",
                      message = "Status must be PENDING, APPROVED, or REJECTED" )
            String status)
    {

        EmployeeLeave result = service.updateStatus(id, status);

        if (result == null)
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    //    Delete a leave application
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLeave(
            @PathVariable Long id) {

        boolean deleted = service.deleteLeave(id);

        if (!deleted)
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}