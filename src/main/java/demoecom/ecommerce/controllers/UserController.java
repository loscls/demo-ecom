package demoecom.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demoecom.ecommerce.entities.User;
import demoecom.ecommerce.services.JwtService;
import demoecom.ecommerce.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PutMapping("/updateUser")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> updateUser (@RequestBody User user) {
        try {
            return new ResponseEntity<Object>(userService.updateUser(user), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/upgradeRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> upgradeRole (@RequestParam String email) {
        try {
            return new ResponseEntity<Object>(userService.upgradeRole(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/downgradeRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> downgradeRole (@RequestParam String email) {
        try {
            return new ResponseEntity<Object>(userService.downgradeRole(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> removeUser (@RequestParam String email) {
        try {
            String response = userService.removeUser(email);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAccount")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> deleteAccount (HttpServletRequest request, @RequestParam String password) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            String response = userService.deleteAccount(email, password);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }

    }
    
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAll() {
        try {
            return new ResponseEntity<Object>(userService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);            
        }
    }

    @PutMapping("/addToCart")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> addToCart (HttpServletRequest request, @RequestParam("unicode") String uniCode, @RequestParam("quantity") int quantity) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.addToCart(email, uniCode, quantity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeFromCart")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> removeFromCart (HttpServletRequest request, @RequestParam("unicode") String uniCode, @RequestParam("quantity") int quantity) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.removeFromCart(email, uniCode, quantity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/getCart")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> getCart (HttpServletRequest request) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.getCart(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUserByProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getUserByProduct (@RequestParam("unicode") String uniCode) {
        try {
            return new ResponseEntity<Object>(userService.getUserByProduct(uniCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }


}
