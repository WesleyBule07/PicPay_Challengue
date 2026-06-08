package com.picpay.dev_challengue.challengue.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserResponseDTO(

        Long id,
        String name,
        String email
){
}
