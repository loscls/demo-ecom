package demoecom.ecommerce.DTOs;

import demoecom.ecommerce.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;
    private String surname;
    private String email;
    private double balance;
    private demoecom.ecommerce.entities.Role Role;

    public UserDTO(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.balance = user.getBalance();
        this.Role = user.getRole();
    }
    
}
