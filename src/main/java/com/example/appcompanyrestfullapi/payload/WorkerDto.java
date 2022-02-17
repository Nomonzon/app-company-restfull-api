package com.example.appcompanyrestfullapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDto {
    private String fullName;
    private Long departmentId;
    private String homeNumber;
    private String street;
}
