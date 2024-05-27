package in.linpaws.ecommerce.Repositories;

import in.linpaws.ecommerce.Models.ProductCategeory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<ProductCategeory,Long> {
    ProductCategeory save(ProductCategeory category);
    ProductCategeory findByTitle(String name);

    List<ProductCategeory> findByTitleStartingWith(String starting);
}
