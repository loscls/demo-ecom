package demoecom.ecommerce.entities;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer ID;

    @Column(name="name")
    private String name;

    @Column(name="brand")
    private String brand;

    @Column(name="uni_code", nullable = false, unique = true)
    private String uniCode;

    @Column(name="quality")
    private int quantity;

    @Column(name="price")
    private double price;

    @Column(name="image")
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductInPurchase> user;

    @Version
    private Integer version;
        
}
