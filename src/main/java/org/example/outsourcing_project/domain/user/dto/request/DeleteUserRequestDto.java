package org.example.outsourcing_project.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteUserRequestDto {
    
    private final String password;
    
}
