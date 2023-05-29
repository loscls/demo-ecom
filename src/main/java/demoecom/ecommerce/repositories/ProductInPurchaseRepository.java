package demoecom.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demoecom.ecommerce.entities.Product;
import demoecom.ecommerce.entities.ProductInPurchase;
import demoecom.ecommerce.entities.User;

@Repository
public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Integer> {

    ProductInPurchase findByUsersAndProduct(User u, Product p);
    List<ProductInPurchase> findByProduct(Product p);

}
