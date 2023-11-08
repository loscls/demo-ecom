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
    private double price;
    private String uniCode;
    private String image;

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.brand = product.getBrand();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.uniCode = product.getUniCode();
        this.image = product.getImage();
    }

    public ProductDTO(ProductInPurchase pIP) {
        this.name = pIP.getProduct().getName();
        this.brand = pIP.getProduct().getBrand();
        this.quantity = pIP.getProduct().getQuantity();
        this.price = pIP.getProduct().getPrice();
        this.uniCode = pIP.getProduct().getUniCode();
    }
}
