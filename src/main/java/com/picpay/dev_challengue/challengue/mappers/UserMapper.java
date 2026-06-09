package com.picpay.dev_challengue.challengue.mappers;

import com.picpay.dev_challengue.challengue.dtos.request.UserRequestDTO;
import com.picpay.dev_challengue.challengue.dtos.response.UserResponseDTO;
import com.picpay.dev_challengue.challengue.models.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser toEntity(UserRequestDTO request){

        var user = new AppUser();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setCpf(request.cpf());
        user.setRole(request.role());

        return user;
    }

    public UserResponseDTO toResponse(AppUser user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCpf(),
                user.getRole()
        );
    }

}
