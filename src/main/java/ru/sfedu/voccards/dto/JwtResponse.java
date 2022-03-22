package ru.sfedu.voccards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sfedu.voccards.entity.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private String type;
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;


}
