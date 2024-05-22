package in.linpaws.ecommerce.Repositories;

import in.linpaws.ecommerce.Models.Product;
import in.linpaws.ecommerce.Repositories.Projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product save(Product product);
    List<Product> findAll();
    Product findByIdIs(Long id);
    List<Product> findAllByCategeory_Title(String title);
    List<Product> findAllByCategeory_Id(Long id);
    List<Product> findByCategeory_TitleContaining(String title);

    @Query("select p from Product p where p.categeory.title= :categoryName")
    List<Product> getProductWithCategoryName(String categoryName);

    @Query("select p.title as title,p.price as price from Product p where p.categeory.title= :categoryName")
    List<ProductProjection> projectionMethod(String categoryName);

    @Query(value = " select * from Product p where p.id= :id",nativeQuery = true)
    Product nativeQuery(Long id);

}
