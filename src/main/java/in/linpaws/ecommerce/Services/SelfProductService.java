package in.linpaws.ecommerce.Services;

import in.linpaws.ecommerce.Exceptions.ProductNotFoundException;
import in.linpaws.ecommerce.Models.Product;
import in.linpaws.ecommerce.Models.ProductCategeory;
import in.linpaws.ecommerce.Repositories.CategoryRepository;
import in.linpaws.ecommerce.Repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SelfProductService")
public class SelfProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(long productId) throws ProductNotFoundException {
        Product product=productRepository.findByIdIs(productId);
        if(product==null)
        {
            throw  new ProductNotFoundException(
                    "Product with "+productId+" not found"+" try a product with valid id"
            );
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(String title, Double price, String description,
                              String category, String image) {
         Product product=new Product();
         product.setTitle(title);
         product.setPrice(price);
         product.setDescription(description);
        ProductCategeory productCategeory=categoryRepository.findByTitle(category);
        if (productCategeory==null)
        {
            ProductCategeory newProductCategeory=new ProductCategeory();
            newProductCategeory.setTitle(category);
            productCategeory=newProductCategeory;
        }
        product.setCategeory(productCategeory);
         product.setImageUrl(image);

         Product savedProduct=productRepository.save(product);
         return savedProduct;
    }

    @Override
    public Product deleteProduct(long productId) throws ProductNotFoundException {
         Product product=productRepository.findByIdIs(productId);
        if(product==null)
        {
            throw  new ProductNotFoundException(
                    "Product with "+productId+" not found"+" try a product with valid id"
            );
        }
        productRepository.deleteById(productId);
        return product;
    }

    @Override
    public Product updateProduct(long productId, String title, Double price, String description, String category, String image) throws ProductNotFoundException {
        Product productInDb=productRepository.findByIdIs(productId);
        if(productInDb==null)
        {
            throw  new ProductNotFoundException(
                    "Product with "+productId+" not found"+" try a product with valid id"
            );
        }
        if(title!=null)
        {
            productInDb.setTitle(title);
        }
        if(price!=null)
        {
            productInDb.setPrice(price);
        }
        if(description!=null)
        {
            productInDb.setDescription(description);
        }
        if(category!=null)
        {
            ProductCategeory productCategeoryFromDB=categoryRepository.findByTitle(category);
            if (productCategeoryFromDB==null)
            {
                ProductCategeory newProductCategeory=new ProductCategeory();
                newProductCategeory.setTitle(category);
                productCategeoryFromDB=newProductCategeory;
            }
            productInDb.setCategeory(productCategeoryFromDB);

        }
        if(image!=null)
        {
            productInDb.setImageUrl(image);
        }
        return productRepository.save(productInDb);
    }

    @Override
    public Product replaceProduct(long productId, String title, Double price, String description, String category, String image) throws ProductNotFoundException {
        Product product=productRepository.findByIdIs(productId);
        if(product==null)
        {
            throw  new ProductNotFoundException(
                    "Product with "+productId+" not found"+" try a product with valid id"
            );
        }
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        ProductCategeory productCategeory=categoryRepository.findByTitle(category);
        if (productCategeory==null)
        {
            ProductCategeory newProductCategeory=new ProductCategeory();
            newProductCategeory.setTitle(category);
            productCategeory=newProductCategeory;
        }
        product.setCategeory(productCategeory);
        product.setImageUrl(image);
        return productRepository.save(product);
    }
}
