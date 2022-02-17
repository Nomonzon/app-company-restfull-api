package com.example.appcompanyrestfullapi.payload;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class CompanyDto {
    @NotNull
    private String homeNumber;

    @NotNull
    private String street;

    @NotNull
    private String corpName;

    @NotNull
    private String directorName;
}
