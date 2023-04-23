package kz.sportify.gateway.controllers;

import kz.sportify.gateway.models.Role;
import kz.sportify.gateway.models.Users;
import kz.sportify.gateway.payload.request.LoginRequest;
import kz.sportify.gateway.payload.request.SignupRequest;
import kz.sportify.gateway.repository.RoleRepository;
import kz.sportify.gateway.security.jwt.JwtUtils;
import kz.sportify.gateway.models.ERole;
import kz.sportify.gateway.models.dto.UserDTO;
import kz.sportify.gateway.payload.response.JwtResponse;
import kz.sportify.gateway.payload.response.MessageResponse;
import kz.sportify.gateway.payload.response.RoleResponse;
import kz.sportify.gateway.repository.UserRepository;
import kz.sportify.gateway.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Controller()
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/api/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                userDetails.getName(),
                userDetails.getSurname()
        ));
    }
    Date convertDate(String dateStr) {
        String sDate1 = "31/12/1998";
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    @RequestMapping(value = "/api/auth/signup", method = RequestMethod.POST)
//    public ResponseEntity<?> registerUser(
//            @RequestParam("email") String email,
//            @RequestParam("name") String name,
//            @RequestParam("surname") String surname,
//            @RequestParam("birthDate") String birthDate,
//            @RequestParam("role") String role,
//            @RequestParam("password") String password
//    )
        public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest)
    {
        System.out.println(signupRequest.toString());
        String role = signupRequest.getRole();
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Users user = Users.builder()
                .username(signupRequest.getEmail())
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .surname(signupRequest.getSurname())
//                .birthDate(convertDate(signupRequest.getBirthDate().toString()))
                .password(encoder.encode(signupRequest.getPassword()))
                .build();
        Set<Role> roles = new HashSet<>();

        if (signupRequest.getRole() == null || signupRequest.getRole().equals("")) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            if (role.equals("student")) {
                Role student = roleRepository.findByName(ERole.ROLE_STUDENT).orElseThrow();
//                        .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
                roles.add(student);
            }
            else if(role.equals("teacher")) {
                Role teacher = roleRepository.findByName(ERole.ROLE_SUBJECT_TEACHER).orElseThrow();
//                        .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
                roles.add(teacher);
            }
            else{
                Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
//                        .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
                roles.add(userRole);
            }
        }
        user.setRoles(roles);
        userRepository.save(user);


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/api/auth/roles")
    public ResponseEntity<?> getRoles() {
        List<RoleResponse> roles = new ArrayList<>();
        roles.add(RoleResponse.builder()
                .code("user")
                .kkName("Пользователь")
                .ruName("Қолданушы")
                .enName("User")
                .build());
        roles.add(RoleResponse.builder()
                .code("teacher")
                .kkName("Мұғалім")
                .ruName("Учитель")
                .enName("Teacher")
                .build());
        return ResponseEntity.ok(roles);
    }

    @RequestMapping(value = "/api/auth/password", method = RequestMethod.PUT)
    public String updatePassword(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestHeader(value = "username", required = false) String username) {
        Users user = userRepository.findByUsername(username).orElseThrow();
//                .orElseThrow(() -> new BadRequestException("Error: User is not found."));
        if (!this.encoder.matches(oldPwd, user.getPassword())){
            return "Old password is not correct";
        }
        user.setPassword(this.encoder.encode(newPwd));
        userRepository.saveAndFlush(user);
        return "Password is changed";
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        Users user = userRepository.findById(Long.valueOf(id)).orElseThrow();
        return ResponseEntity.ok(new UserDTO().toDto(user));
    }
}
