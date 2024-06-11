package in.linpaws.ecommerce.Services;

import in.linpaws.ecommerce.DTOS.ProductResponseDto;
import in.linpaws.ecommerce.Exceptions.ProductNotFoundException;
import in.linpaws.ecommerce.Models.Mobiles;
import in.linpaws.ecommerce.Models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(long productId) throws ProductNotFoundException;

    public List<Product> getAllProducts();
    public Page<Mobiles> getAllMobiles(int pageNum,int pageSize,String sortParam,int Direction);
    public Page<Mobiles> getAllMobiles(int x,int y);

    public Product addProduct(String title,
                              Double price,
                              String description,
                              String category,
                              String image);
    public Mobiles addMobiles(String title, Double price,String os);

    public Product deleteProduct(long productId) throws ProductNotFoundException;

   public Product updateProduct(long productId,String title, Double price, String description, String category, String image) throws ProductNotFoundException;

    public Product replaceProduct(long productId, String title, Double price, String description, String category, String image) throws ProductNotFoundException;
}
