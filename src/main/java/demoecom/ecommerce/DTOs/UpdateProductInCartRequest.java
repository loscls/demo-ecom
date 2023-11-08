package demoecom.ecommerce.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductInCartRequest {

    private String unicode;
    private int quantity;
    
}
