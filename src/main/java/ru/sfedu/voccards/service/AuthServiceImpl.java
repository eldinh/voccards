package ru.sfedu.voccards.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sfedu.voccards.config.jwt.JwtUtils;
import ru.sfedu.voccards.dao.RoleDao;
import ru.sfedu.voccards.dao.UserDao;
import ru.sfedu.voccards.dto.JwtResponse;
import ru.sfedu.voccards.dto.LoginRequest;
import ru.sfedu.voccards.dto.MessageResponse;
import ru.sfedu.voccards.dto.SignupRequest;
import ru.sfedu.voccards.entity.ERole;
import ru.sfedu.voccards.entity.Role;
import ru.sfedu.voccards.entity.UserApp;
import ru.sfedu.voccards.service.userDetail.UserDetailsImpl;
import static ru.sfedu.voccards.Constants.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{

    private static final Logger log = LogManager.getLogger(AuthServiceImpl.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;



    @Override
    public ResponseEntity<?> register(SignupRequest signupRequest){

        log.info("Starting AuthService register[0]");
        log.info("register[1]: signupRequest - {}", signupRequest);
        try {
            if (userDao.existsByUsername(signupRequest.getUsername()))
                throw new Exception(USERNAME_EXIST);
            if (userDao.existsByEmail(signupRequest.getEmail()))
                throw new Exception(EMAIL_EXIST);

            log.debug("register[2]: Saving user: username - {}, email - {}",
                    signupRequest.getUsername(),
                    signupRequest.getEmail());

            UserApp user = new UserApp();
            user.setUsername(signupRequest.getUsername());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setRoles(new HashSet<Role>(Set.of(roleDao.findByName(ERole.ROLE_USER))));
            userDao.save(user);

            return ResponseEntity.ok(new MessageResponse(USER_CREATED));
        }catch (Exception e){
            log.error("Function AuthService register had failed[3]: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> authUser(LoginRequest loginRequest) {
        log.info("Starting AuthService authUser[0]");
        log.info("authUser[1]: User {} authorizing",loginRequest.getUsername() );
        try {
            log.debug("authUser[2]: Putting auth to security context");
//            Authentication authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(
//                            loginRequest.getUsername(),
//                            loginRequest.getPassword()));

//            // security context used to store data about current user
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.debug("authUser[3]: Generating jwt token");
//            String jwt = jwtUtils.generateJwtToken(authentication);
//
//            log.debug("authUser[4]: Getting user details");
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            Set<String> roles = userDetails.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toSet());

            return null;

        }catch (Exception e){
            log.error("Function AuthService authUser had failed[5]: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}
