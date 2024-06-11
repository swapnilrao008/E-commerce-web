package in.linpaws.ecommerce.Services;

import in.linpaws.ecommerce.Exceptions.ProductNotFoundException;
import in.linpaws.ecommerce.Models.Mobiles;
import in.linpaws.ecommerce.Models.Product;
import in.linpaws.ecommerce.Models.ProductCategeory;
import in.linpaws.ecommerce.Repositories.CategoryRepository;
import in.linpaws.ecommerce.Repositories.MobileRepository;
import in.linpaws.ecommerce.Repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SelfProductService")
public class SelfProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final MobileRepository mobileRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository, MobileRepository mobileRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mobileRepository = mobileRepository;
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

    public Page<Mobiles> getAllMobiles(int pageNum,int pageSize,String sortParam,int Direction)
    {
        if(Direction==1)
        return mobileRepository.findAll(PageRequest.of(pageNum,pageSize, Sort.by(sortParam).descending()));
        else
            return mobileRepository.findAll(PageRequest.of(pageNum,pageSize, Sort.by(sortParam).ascending()));
    }

    public Page<Mobiles> getAllMobiles(int x,int y)
    {

            return mobileRepository.findAll(PageRequest.of(x,y));
        }

    public Mobiles addMobiles(String title, Double price,String os) {
        Mobiles mobiles=new Mobiles();
        mobiles.setTitle(title);
        mobiles.setPrice(price);
        mobiles.setOs(os);

        Mobiles savedMobile=mobileRepository.save(mobiles);
        return savedMobile;
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
