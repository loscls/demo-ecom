package demoecom.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demoecom.ecommerce.DTOs.UserDTO;
import demoecom.ecommerce.entities.Role;
import demoecom.ecommerce.entities.User;
import demoecom.ecommerce.exceptions.IncorrectPasswordException;
import demoecom.ecommerce.exceptions.UserAlreadyAdminException;
import demoecom.ecommerce.exceptions.UserAlreadyUserException;
import demoecom.ecommerce.exceptions.UserNotFoundException;
import demoecom.ecommerce.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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

    public String deleteAccount(String email, String password) {
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

    public List<UserDTO> getAll() {
        List<UserDTO> listDTO = new ArrayList<>();

        List<User> listUser = userRepository.findAll();

        for (User user : listUser) {
            UserDTO uDTO = new UserDTO(user);
            listDTO.add(uDTO);
        }        
        
        return listDTO;
    }

    
}
