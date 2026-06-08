package com.picpay.dev_challengue.challengue.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

public record UserRequestDTO(
        String name,
        String email,
        String cpf,
        String password
) {
}
