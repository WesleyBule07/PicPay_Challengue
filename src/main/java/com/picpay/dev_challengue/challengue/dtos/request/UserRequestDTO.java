package com.picpay.dev_challengue.challengue.dtos.request;

import com.picpay.dev_challengue.challengue.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record UserRequestDTO(

        @NotBlank(message = "Name field cannot be blank")
        String name,
        @Email(message = "Please provide a valid email")
        String email,
        @NotBlank(message = "CPF field cannot be blank")
        String cpf,
        @NotBlank(message = "Please provide a password for account")
        String password,
        UserRole role


) {
}
