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

import demoecom.ecommerce.DTOs.AddBalanceRequest;
import demoecom.ecommerce.DTOs.AddToCartRequest;
import demoecom.ecommerce.DTOs.UpdateProductInCartRequest;
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
    public ResponseEntity<Object> updateUser (@RequestParam String email, @RequestBody User user) {
        try {
            System.out.println("ENTRATO CONTROLLER UPDATE USER");
            return new ResponseEntity<Object>(userService.updateUser(email, user), HttpStatus.OK);
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
    public ResponseEntity<Object> deleteAccount (HttpServletRequest request, @RequestParam("password") String password) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            String response = userService.deleteAccount(email, password);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/addBalance")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> addBalance (HttpServletRequest request, @RequestBody AddBalanceRequest addBalanceRequest) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.addBalance(email, addBalanceRequest.getBalance()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> getUser(HttpServletRequest request) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.getUser(email), HttpStatus.OK);
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
    public ResponseEntity<Object> addToCart (HttpServletRequest request, @RequestBody AddToCartRequest addToCartRrequest) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.addToCart(email, addToCartRrequest.getUniCode(), addToCartRrequest.getQuantity()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateProductInCart")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> updateProductInCart (HttpServletRequest request, @RequestBody UpdateProductInCartRequest req) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.updateProductInCart(email, req.getUnicode(), req.getQuantity()), HttpStatus.OK);
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

    @GetMapping("/getTotalPrice")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> getCartPrice (HttpServletRequest request) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.getTotalPrice(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buyProduct")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> buyProduct (HttpServletRequest request, @RequestParam("unicode") String uniCode, @RequestParam("quantity") int quantity) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity<Object>(userService.buyProduct(email, uniCode, quantity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        } 
    }

    // @GetMapping("/buyAll")
    // public ResponseEntity<Object> buyAll (HttpServletRequest request) {
    //     try {
    //         String email = jwtService.extractEmailFromRequest(request);
    //         return new ResponseEntity<Object>(userService.buyAll(email), HttpStatus.OK)
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
    //     }
    // }

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
