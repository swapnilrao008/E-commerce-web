package in.linpaws.ecommerce.Services;

import in.linpaws.ecommerce.DTOS.ProductResponseDto;
import in.linpaws.ecommerce.Exceptions.ProductNotFoundException;
import in.linpaws.ecommerce.Models.Product;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(long productId) throws ProductNotFoundException;

    public List<Product> getAllProducts();

    public Product addProduct(String title,
                              Double price,
                              String description,
                              String category,
                              String image);

    public Product deleteProduct(long productId) throws ProductNotFoundException;

   public Product updateProduct(long productId,String title, Double price, String description, String category, String image) throws ProductNotFoundException;

    public Product replaceProduct(long productId, String title, Double price, String description, String category, String image) throws ProductNotFoundException;
}
