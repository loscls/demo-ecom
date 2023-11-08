package demoecom.ecommerce.DTOs;

import java.util.List;

import demoecom.ecommerce.entities.ProductInPurchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCartResponse {
    private List<ProductInPurchase> cart;
    private int totalQuantity;
    private float totalPrice;
}
