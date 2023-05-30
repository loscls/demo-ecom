package demoecom.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demoecom.ecommerce.DTOs.ProductDTO;
import demoecom.ecommerce.DTOs.UserDTO;
import demoecom.ecommerce.entities.Product;
import demoecom.ecommerce.entities.ProductInPurchase;
import demoecom.ecommerce.entities.Role;
import demoecom.ecommerce.entities.User;
import demoecom.ecommerce.exceptions.BalanceNotEnoughException;
import demoecom.ecommerce.exceptions.BalanceNotValidException;
import demoecom.ecommerce.exceptions.IncorrectPasswordException;
import demoecom.ecommerce.exceptions.ProductNotEnoughException;
import demoecom.ecommerce.exceptions.ProductNotFoundException;
import demoecom.ecommerce.exceptions.ProductNotInCartException;
import demoecom.ecommerce.exceptions.UserAlreadyAdminException;
import demoecom.ecommerce.exceptions.UserAlreadyUserException;
import demoecom.ecommerce.exceptions.UserNotFoundException;
import demoecom.ecommerce.repositories.ProductInPurchaseRepository;
import demoecom.ecommerce.repositories.ProductRepository;
import demoecom.ecommerce.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductInPurchaseRepository pIPRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

   

    public UserDTO updateUser(User user) throws RuntimeException {

        User tmp = userRepository.findByEmail(user.getEmail());

        if(tmp == null) {
            throw new UserNotFoundException();
        }

        tmp.setName(user.getName());
        tmp.setSurname(user.getSurname());
        tmp.setEmail(user.getEmail());
        tmp.setRole(user.getRole());
        
        userRepository.save(tmp);

        User u = userRepository.findByEmail(user.getEmail());

        UserDTO uDTO = new UserDTO(u);

        return uDTO;
    }

    public UserDTO upgradeRole(String email) throws RuntimeException {
        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException();
        }

        if (u.getRole() == Role.ADMIN) {
            throw new UserAlreadyAdminException();
        }

        u.setRole(Role.ADMIN);
        userRepository.save(u);

        UserDTO uDTO = new UserDTO(u);
        
        return uDTO;
    }

    public UserDTO downgradeRole(String email) throws RuntimeException {
        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException();
        }

        if (u.getRole() == Role.USER) {
            throw new UserAlreadyUserException();
        }

        u.setRole(Role.USER);
        userRepository.save(u);
        
        UserDTO uDTO = new UserDTO(u);
        
        return uDTO;
    }

    public String removeUser(String email) throws RuntimeException {
        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException();
        }

        userRepository.delete(u);

        return "User: " + email + " has been removed!";

    }

    public String deleteAccount(String email, String password) throws RuntimeException {
        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException();
        }

        System.out.println(password);
        System.out.println(u.getPassword());

        if (!(passwordEncoder.matches(password, u.getPassword()))) {
            throw new IncorrectPasswordException();
        }

        userRepository.delete(u);

        return "Your account has been deleted";
    }

    public User addBalance(String email, float balance) throws RuntimeException {
        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException();
        }

        if (balance <= 0) {
            throw new BalanceNotValidException();
        }

        u.setBalance(u.getBalance()+balance);
        userRepository.save(u);

        return u;
    }

    public List<UserDTO> getAll() {
        List<UserDTO> listDTO = new ArrayList<>();

        List<User> listUser = userRepository.findAll();

        for (User user : listUser) {
            UserDTO uDTO = new UserDTO(user);
            listDTO.add(uDTO);
        }        
        
        return listDTO;
    }

    public ProductInPurchase addToCart (String email, String uniCode, int quantity) throws RuntimeException {
        User u = userRepository.findByEmail(email);
        Product p = productRepository.findByUniCode(uniCode);
        ProductInPurchase pIP = pIPRepository.findByUsersAndProduct(u, p);

        if (u == null) {
            throw new UserNotFoundException();
        }

        if(p == null) {
            throw new ProductNotFoundException();
        }

        if(quantity > p.getQuantity()){
            throw new ProductNotEnoughException();
        }
        
        if(pIP == null) {
            pIP = new ProductInPurchase(null, p, u, p.getName(), p.getBrand(), p.getUniCode(), p.getPrice(), quantity);
            u.getCart().add(pIP);
            pIPRepository.save(pIP);
            userRepository.save(u);
            return pIP;
        }

        if((pIP.getQuantity()+quantity) <= p.getQuantity()) {
            pIP.setQuantity(pIP.getQuantity()+quantity);
            pIPRepository.save(pIP);            
            return pIP;
        } else {
            throw new ProductNotEnoughException();
        }
    }

    public List<ProductDTO> removeFromCart (String email, String uniCode, int quantity) throws RuntimeException {
        User u = userRepository.findByEmail(email);
        Product p = productRepository.findByUniCode(uniCode);
        ProductInPurchase pIP = pIPRepository.findByUsersAndProduct(u, p);

        if (u == null) {
            throw new UserNotFoundException();
        }

        if(p == null) {
            throw new ProductNotFoundException();
        }

        if(!(u.getCart().contains(pIP))) {
            throw new ProductNotInCartException();
        }

        if(quantity < pIP.getQuantity()) {
            pIP.setQuantity(pIP.getQuantity()-quantity);
            pIPRepository.save(pIP);
            return getCart(pIP.getUsers().getEmail());
        } else {
            u.getCart().remove(pIP);
            userRepository.save(u);
            return getCart(pIP.getUsers().getEmail());
        }
    }

    public List<ProductDTO> getCart (String email) throws RuntimeException {
        List<ProductDTO> productDTOs = new ArrayList<>();
        User u = userRepository.findByEmail(email);

        List<ProductInPurchase> Cart = u.getCart();

        for(ProductInPurchase pIP : Cart) {
            ProductDTO pDTO = new ProductDTO(pIP);
            productDTOs.add(pDTO);
        }

        return productDTOs;
    }

    @Transactional
    public List<ProductDTO> buyProduct (String email, String uniCode, int quantity) throws RuntimeException {
        User u = userRepository.findByEmail(email);
        Product p = productRepository.findByUniCode(uniCode);
        ProductInPurchase pIP = pIPRepository.findByUsersAndProduct(u, p);

        if (u == null) {
            throw new UserNotFoundException();
        }

        if(!(u.getCart().contains(pIP))) {
            throw new ProductNotInCartException();
        }

        if((pIP.getPrice()*quantity) > u.getBalance()) {
            throw new BalanceNotEnoughException();
        }

        if((quantity <= pIP.getQuantity()) && pIP.getQuantity() <= p.getQuantity()) {
            pIP.setQuantity(pIP.getQuantity()-quantity);

            if(pIP.getQuantity() == 0) {
                u.getCart().remove(pIP);
            }

            p.setQuantity(p.getQuantity()-quantity);

            float total = pIP.getPrice()*quantity;
            u.setBalance(u.getBalance()-total);

            pIPRepository.save(pIP);
            productRepository.save(p);
            userRepository.save(u);

            System.out.println(u);            
            return getCart(u.getEmail());
        } else {
            throw new ProductNotEnoughException();
        }
    }

    public List<User> getUserByProduct (String uniCode) throws RuntimeException {
        Product p = productRepository.findByUniCode(uniCode);
        List<User> userList = new ArrayList<>();
        List<ProductInPurchase> pIPList = pIPRepository.findByProduct(p);

        for(ProductInPurchase pIP : pIPList) {
            userList.add(pIP.getUsers());
        }

        if(userList.size() == 0) {
            throw new ProductNotInCartException();
        }

        return userList;
    }

    
}
