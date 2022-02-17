package com.example.appcompanyrestfullapi.payload;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    @NotNull
    private String departmentName;

    @NotNull
    private Long companyId;

}
