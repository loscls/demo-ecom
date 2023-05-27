package demoecom.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demoecom.ecommerce.entities.Product;
import demoecom.ecommerce.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    //add
    @PostMapping("/addProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> addProduct (@RequestBody Product p) {
        try {
            return new ResponseEntity<Object>(productService.addProduct(p), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }

    }

    //update
    @PutMapping("/updateProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateProduct (@RequestBody Product p) {
        try {
            return new ResponseEntity<Object>(productService.updateProduct(p), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    
    //remove
    @DeleteMapping("/removeProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> removeProduct (@RequestParam("unicode") String uniCode) {
        try {
            return new ResponseEntity<Object>(productService.removeProduct(uniCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }

    }

    //getAll
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Object> getAll() {
        try {
            return new ResponseEntity<Object>(productService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    
}
