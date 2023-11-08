package demoecom.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demoecom.ecommerce.DTOs.AuthenticationResponse;
import demoecom.ecommerce.DTOs.AuthenticatonRequest;
import demoecom.ecommerce.controllers.RegisterRequest;
import demoecom.ecommerce.entities.Role;
import demoecom.ecommerce.entities.User;
import demoecom.ecommerce.exceptions.TypeDataMismatchException;
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

    public boolean isValidEmail(String email) throws RuntimeException{
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public AuthenticationResponse register(RegisterRequest request) {

        if(!isValidEmail(request.getEmail())){
            throw new TypeDataMismatchException();
        }

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        
        return AuthenticationResponse.builder().token(jwtToken).user(user).build();
    }

    public AuthenticationResponse authenticate(AuthenticatonRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(user);

        // return AuthenticationResponse.builder().token(jwtToken).build();

        return new AuthenticationResponse(jwtToken, user);
        //Aggiunto questo return per tornare lo user nel front
    }

    public AuthenticationResponse reloadUser(String email) {

        User user = userRepository.findByEmail(email);

        String jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken, user);

    }



}
