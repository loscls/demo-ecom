package demoecom.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demoecom.ecommerce.controllers.AuthenticationResponse;
import demoecom.ecommerce.controllers.AuthenticatonRequest;
import demoecom.ecommerce.controllers.RegisterRequest;
import demoecom.ecommerce.entities.Role;
import demoecom.ecommerce.entities.User;
import demoecom.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        //AGGIUNGERE CONTROLLI AGGIUNTA UTENTE 
        //REGEX

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticatonRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail());

        String jwtToken =  jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }


}
