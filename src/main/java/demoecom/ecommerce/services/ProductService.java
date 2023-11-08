package demoecom.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import demoecom.ecommerce.DTOs.ProductDTO;
import demoecom.ecommerce.entities.Product;
import demoecom.ecommerce.exceptions.ProductNotFoundException;
import demoecom.ecommerce.exceptions.UniCodeAlreadyExistException;
import demoecom.ecommerce.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    public Product addProduct(Product product) throws RuntimeException {
        if(productRepository.existsByUniCode(product.getUniCode())) {
            throw new UniCodeAlreadyExistException();
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) throws RuntimeException {
        Product tmp = productRepository.findByUniCode(product.getUniCode());

        if(tmp == null) {
            throw new ProductNotFoundException();
        }

        tmp.setName(product.getName());
        tmp.setBrand(product.getBrand());
        tmp.setQuantity(product.getQuantity());
        tmp.setPrice(product.getPrice());
        tmp.setImage(product.getImage());

        productRepository.save(tmp);

        Product p = productRepository.findByUniCode(product.getUniCode());

        return p;
    }

    public  void removeProduct(String uniCode) throws RuntimeException {
        Product p = productRepository.findByUniCode(uniCode);

        if(p == null) {
            throw new ProductNotFoundException();
        }

        productRepository.delete(p);
    }

    public List<ProductDTO>  getAll() throws RuntimeException {
        List<Product> pList = productRepository.findAll();

        List<ProductDTO> pDTOList = new ArrayList<>();

        for(Product p : pList) {
            ProductDTO pDTO = new ProductDTO(p);
            pDTOList.add(pDTO);
        }

        return pDTOList;
    }

    public Page<Product> getPage(int pgNumber, int pgSize) throws RuntimeException {
        PageRequest pageReq = PageRequest.of(pgNumber, pgSize);

        return productRepository.findAll(pageReq);
    }

    public List<Product> getSearch(String searchTerm) throws RuntimeException {

        List<Product> productsList = productRepository.findAll();

        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : productsList) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase()) || product.getBrand().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredProducts.add(product);
            }
        }

        if (filteredProducts.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return filteredProducts;
    }
}
