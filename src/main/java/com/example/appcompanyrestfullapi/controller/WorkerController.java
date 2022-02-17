package com.example.appcompanyrestfullapi.controller;

import com.example.appcompanyrestfullapi.entity.Worker;
import com.example.appcompanyrestfullapi.payload.Message;
import com.example.appcompanyrestfullapi.payload.WorkerDto;
import com.example.appcompanyrestfullapi.service.WorkerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private WorkerServiceImpl workerService;

    /**
     * TO GET ALL WORKERS FORM DATABASE
     * @return LIST<WORKER>
     */
    @GetMapping
    public ResponseEntity<?> getAllWorker(){
        List<Worker> allWorker = workerService.getAllWorker();
        return allWorker != null && !allWorker.isEmpty()
                ? new ResponseEntity<>(allWorker, HttpStatus.OK)
                : new ResponseEntity<>("Data base is empty!", HttpStatus.NOT_FOUND);
    }

    /**
     * TO GET WORKER BY ID FORM DATABASE
     * @param id
     * @return Worker or Null
     */
    @GetMapping
    public ResponseEntity<?> getWorkerById(@PathVariable Long id){
        Worker worker = workerService.getWorkerById(id);
        return worker != null
                ? new ResponseEntity<>(worker, HttpStatus.OK)
                : new ResponseEntity<>("Worker is not found!", HttpStatus.NOT_FOUND);
    }

    /**
     * TO ADD WORKER TO DATABASE
     * @param workerDto
     * @return Message(message, false or true)
     */
    @PostMapping
    public ResponseEntity<Message> addWorker(@RequestBody WorkerDto workerDto){
        Message message = workerService.addWorker(workerDto);
        return message.isSuccess()
                ? new ResponseEntity<>(message, HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    /**
     * TO UPDATE WORKER WHICH WORKER ALREADY EXISTS IN DATABASE
     * @param workerDto YOU CAN FIND THIS CLASS IN THE PACKAGE payload
     * @param id WORKER'S ID
     * @return Message(message, false or true)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> editWorker(@RequestBody WorkerDto workerDto, @PathVariable Long id){
        Message message = workerService.editWorker(workerDto, id);
        return message.isSuccess()
                ? new ResponseEntity<>(message, HttpStatus.OK)
                : new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    /**
     * TO DELETE WORKER FROM DATABASE BY ID
     * @param id
     * @return Message(message, false or true)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable Long id){
        Message message = workerService.deleteWorkerById(id);
        return message.isSuccess()
                ? new ResponseEntity<>(message, HttpStatus.OK)
                : new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

}
