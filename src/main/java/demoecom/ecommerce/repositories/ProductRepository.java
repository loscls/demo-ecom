package demoecom.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demoecom.ecommerce.entities.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public boolean existsByUniCode(String uniCode);
    public Product findByUniCode(String uniCode);
    
}
