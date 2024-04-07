package com.example.mapping;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/demo")
    public List<department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Other CRUD endpoints
}
