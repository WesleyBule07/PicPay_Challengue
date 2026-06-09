package com.picpay.dev_challengue.challengue.dtos.response;

import com.picpay.dev_challengue.challengue.enums.UserRole;

public record UserResponseDTO(

        Long id,
        String name,
        String email,
        UserRole role
){
}
