package com.example.appcompanyrestfullapi.controller;

import com.example.appcompanyrestfullapi.entity.Department;
import com.example.appcompanyrestfullapi.payload.DepartmentDto;
import com.example.appcompanyrestfullapi.payload.Message;
import com.example.appcompanyrestfullapi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartment(){
        List<Department> allDepartment = departmentService.getAllDepartment();
        return allDepartment != null && !allDepartment.isEmpty()
                ? new ResponseEntity<>(allDepartment, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping({"/id"})
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id){
        Department departmentById = departmentService.getDepartmentById(id);
//        return departmentById !=null
//                ? new ResponseEntity<>(departmentById, HttpStatus.OK)
//                : new ResponseEntity<>(new Department(),HttpStatus.NOT_FOUND);
        return departmentById != null
                ? ResponseEntity.status(200).body(departmentById)
                : ResponseEntity.status(404).body(new Department());
    }

    @PostMapping
    public ResponseEntity<Message> addDepartment(@RequestBody DepartmentDto departmentDto){
        Message message = departmentService.addDepartment(departmentDto);
        return message.isSuccess()
                ? ResponseEntity.status(201).body(message)
                : ResponseEntity.status(409).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateDepartment(@RequestBody DepartmentDto departmentDto, @PathVariable Long id){
        Message message = departmentService.editDepartment(departmentDto, id);
        return message.isSuccess()
                ? ResponseEntity.status(200).body(message)
                : ResponseEntity.status(404).body(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteDepartment(@PathVariable Long id){
        Message message = departmentService.deleteDepartment(id);
        return message.isSuccess()
                ? new ResponseEntity<>(message, HttpStatus.ACCEPTED)
                : new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

}
