package demoecom.ecommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoController")
public class DemoController {
    
    @GetMapping("/demoUSER")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> sayHelloUser() {
        return ResponseEntity.ok("Hello User");
    }

    @GetMapping("/demoADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> sayHelloAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }



}
