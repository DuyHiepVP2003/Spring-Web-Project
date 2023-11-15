package com.project.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFormDTO {
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String address;
}
