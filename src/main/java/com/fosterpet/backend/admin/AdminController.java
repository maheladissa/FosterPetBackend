package com.fosterpet.backend.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardData(){
        try {
            return ResponseEntity.ok(adminService.getDashboardData());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/period-filter")
    public ResponseEntity<?> getPeriodFilterData(String startDate, String endDate){
        try {
            return ResponseEntity.ok(adminService.getPeriodFilterData(startDate, endDate));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
