package ru.sfedu.voccards.service.userDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sfedu.voccards.entity.Role;
import ru.sfedu.voccards.entity.UserApp;


import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;


    private Collection<? extends GrantedAuthority> authorities;

    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> role){
        return  role.stream().map(r -> new SimpleGrantedAuthority(r.getName().toString()))
                .collect(Collectors.toList());
    }

    public static UserDetailsImpl build(UserApp user){

        return new UserDetailsImpl(user.getId(),
                                    user.getUsername(),
                                    user.getEmail(),
                                    user.getPassword(),
                                    mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
