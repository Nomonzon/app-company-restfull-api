package com.example.appcompanyrestfullapi.service;

import com.example.appcompanyrestfullapi.entity.Address;
import com.example.appcompanyrestfullapi.entity.Department;
import com.example.appcompanyrestfullapi.entity.Worker;
import com.example.appcompanyrestfullapi.payload.Message;
import com.example.appcompanyrestfullapi.payload.WorkerDto;
import com.example.appcompanyrestfullapi.repository.AddressRepository;
import com.example.appcompanyrestfullapi.repository.DepartmentRepository;
import com.example.appcompanyrestfullapi.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerServiceImpl {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     * TO GET ALL WORKERS FORM DATABASE
     * @return LIST<WORKER>
     */
    public List<Worker> getAllWorker(){
        return workerRepository.findAll();
    }

    /**
     * TO GET WORKER BY ID FORM DATABASE
     * @param id
     * @return Worker or Null
     */
    public Worker getWorkerById(Long id){
        Optional<Worker> workerOptional = workerRepository.findById(id);
        return workerOptional.orElse(null);
    }

    /**
     * TO DELETE WORKER FROM DATABASE BY ID
     * @param id
     * @return Message(message, false or true)
     */
    public Message deleteWorkerById(Long id){
        try {
            workerRepository.deleteById(id);
            return new Message("Success worker is deleted", true);
        }
        catch (Exception e){
            return new Message("Error worker is not delted", false);
        }
    }

    /**
     * TO ADD WORKER TO DATABASE
     * @param workerDto
     * @return Message(message, false or true)
     */
    public Message addWorker(WorkerDto workerDto){
        boolean existsWorker = workerRepository
                .existsByFullNameAndDepartment_id(workerDto.getFullName(), workerDto.getDepartmentId());
        if (existsWorker){
            return new Message("worker was already existed", false);
        }

        boolean existsAddress = addressRepository
                .existsByStreetAndHomeNumber(workerDto.getStreet(), workerDto.getHomeNumber());
        if (existsAddress){
            return new Message("Address was already existed",false);
        }
        Address address = new Address(
                null,
                workerDto.getStreet(),
                workerDto.getHomeNumber()
        );
        Optional<Department> departmentOptional = departmentRepository.findById(workerDto.getDepartmentId());
        if (departmentOptional.isEmpty()){
            return new Message("Department id is not found", false);
        }

        Address savedAddress = addressRepository.save(address);

        Worker worker = new Worker(
                null,
                workerDto.getFullName(),
                savedAddress,
                departmentOptional.get()
        );
        workerRepository.save(worker);
        return new Message("Success worker is added",false);
    }

    /**
     * TO UPDATE WORKER WHICH WORKER ALREADY EXISTS IN DATABASE
     * @param workerDto YOU CAN FIND THIS CLASS IN THE PACKAGE payload
     * @param id WORKER'S ID
     * @return Message(message, false or true)
     */
    public Message editWorker(WorkerDto workerDto, Long id){
        boolean existsWorker = workerRepository
                .existsByFullNameAndDepartment_IdAndIdNot(
                        workerDto.getFullName(),
                        workerDto.getDepartmentId(),
                        id
                );
        if (existsWorker){
            return new Message("Error you cannot edit, Worker was already exited", false);
        }

        Optional<Worker> workerOptional = workerRepository.findById(id);
        if (workerOptional.isEmpty())
            return new Message("Error worker is not found", false);

        Optional<Department> departmentOptional = departmentRepository.findById(workerDto.getDepartmentId());
        if (departmentOptional.isEmpty())
            return new Message("Error department is not found", false);

        Worker worker = workerOptional.get();
        Address address = worker.getAddress();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        addressRepository.save(address);

        worker.setAddress(address);

        worker.setFullName(workerDto.getFullName());
        worker.setDepartment(departmentOptional.get());
        workerRepository.save(worker);
        return new Message("Success department edited", true);
    }
}
