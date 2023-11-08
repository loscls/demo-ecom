package demoecom.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import demoecom.ecommerce.entities.Product;
import demoecom.ecommerce.entities.Role;
import demoecom.ecommerce.entities.User;
import demoecom.ecommerce.repositories.ProductRepository;
import demoecom.ecommerce.repositories.UserRepository;
import demoecom.ecommerce.services.UserService;

@SpringBootApplication
public class EcommerceApplication {

	@Autowired
    PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			User u = new User(null, "Lo", "Scls", "loscls@mail.com", passwordEncoder.encode("lopass"), Role.ADMIN, 100, null);
			userRepository.save(u);

			Product p1 = new Product(null, "Acqua", "Calabria", "0001", 200, 1.50, "https://www.grandettaglio.it/ProductsResources/1762/01000_0_ori.jpeg?1696662150", null,  0);
			productRepository.save(p1);

			Product p2 = new Product(null, "Pane", "Panetteria", "0002", 10, 0.25, "https://healthy.thewom.it/wp-content/uploads/2023/05/pane.jpg", null, 0);
			productRepository.save(p2);

			Product p3 = new Product(null, "Latte", "Parmalat", "0003", 200, 1, "https://m.media-amazon.com/images/I/51xrp+URzzL.jpg", null, 0);
			productRepository.save(p3);

			Product p4 = new Product(null, "Banana", "Chiquita", "0004", 200, 3.42, "https://shorturl.at/qsvPW", null, 0);
			productRepository.save(p4);

			Product p5 = new Product(null, "Prosciutto", "San Daniele", "0005", 200, 0.75, "https://shorturl.at/hvFIQ", null, 0);
			productRepository.save(p5);

			Product p6 = new Product(null, "Brasilena", "Calabria", "0006", 200, 1, "https://shorturl.at/azLP8", null, 0);
			productRepository.save(p6);

			Product p7 = new Product(null, "Birra", "Peroni", "0007", 3, 1.20, "https://shorturl.at/gjsKZ", null,  0);
			productRepository.save(p7);

			Product p8 = new Product(null, "Patatine", "San Carlo", "0008", 9, 1.99, "https://shorturl.at/ftAC5", null, 0);
			productRepository.save(p8);
		};

	}

}
