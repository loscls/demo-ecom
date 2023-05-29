package demoecom.ecommerce.DTOs;

import demoecom.ecommerce.entities.Product;
import demoecom.ecommerce.entities.ProductInPurchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;
    private String brand;
    private int quantity;
    private float price;
    private String uniCode;

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.brand = product.getBrand();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.uniCode = product.getUniCode();
    }

    public ProductDTO(ProductInPurchase pIP) {
        this.name = pIP.getName();
        this.brand = pIP.getBrand();
        this.quantity = pIP.getQuantity();
        this.price = pIP.getPrice();
        this.uniCode = pIP.getUniCode();
    }
}
