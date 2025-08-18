package com.grimrepos.biopay.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//you can use a record here
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    @NotBlank(message = "Full name is required")
    @Size(min = 2,max = 100,message = "Full name must be 2-100 characters")
    private String fullName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "ID number is required")
    @Size(min = 16,max = 16,message = "ID number must be 16 characters")
    private String idNumber;

    // New fields for linking bank account
    @NotBlank private String accountNumber;
    @NotBlank private String bankName;
}
