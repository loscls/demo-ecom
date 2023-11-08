package demoecom.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demoecom.ecommerce.DTOs.AuthenticationResponse;
import demoecom.ecommerce.DTOs.AuthenticatonRequest;
import demoecom.ecommerce.services.AuthenticationService;
import demoecom.ecommerce.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    AuthenticationService authService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> registration (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticatonRequest request) {

        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/reloadUser")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<AuthenticationResponse> reloadUser (HttpServletRequest request) {
        return ResponseEntity.ok(authService.reloadUser(jwtService.extractEmailFromRequest(request)));
    }
    
}
