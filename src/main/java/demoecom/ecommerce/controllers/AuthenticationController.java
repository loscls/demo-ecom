package demoecom.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demoecom.ecommerce.services.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    AuthenticationService authService;

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> registration (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticatonRequest request) {
        
        return ResponseEntity.ok(authService.authenticate(request));
    }
    
}
