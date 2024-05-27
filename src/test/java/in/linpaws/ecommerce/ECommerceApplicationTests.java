package in.linpaws.ecommerce;

import in.linpaws.ecommerce.Models.Product;
import in.linpaws.ecommerce.Models.ProductCategeory;
import in.linpaws.ecommerce.Repositories.CategoryRepository;
import in.linpaws.ecommerce.Repositories.ProductRepository;
import in.linpaws.ecommerce.Repositories.Projections.ProductProjection;
import in.linpaws.ecommerce.Services.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ECommerceApplicationTests {

    @Autowired
    private ProductRepository productRepository;
@Autowired
    private CategoryRepository categoryRepository;
    @Qualifier("SelfProductService")
    @Autowired
    private ProductService productService;

//    public ECommerceApplicationTests(ProductRepository productRepository,
//                                     CategoryRepository categoryRepository,
//                                     ProductService productService) {
//        this.productRepository = productRepository;
//        this.categoryRepository = categoryRepository;
//        this.productService = productService;
//    }

    @Test
    void contextLoads() {
    }

    @Test
    void testJpaDeclaredJoin()
    {
        List<Product> products=productRepository.findByCategeory_TitleContaining("mobiles-ANDROID");
        for(Product product:products)
        {
            System.out.println(product.getTitle());
        }

    }

    @Test
    void testJpaDeclaredJoin1()
    {
        List<Product> products=productRepository.findAllByCategeory_Id(3L);
        for(Product product:products)
        {
            System.out.println(product.getTitle());
        }

    }

    @Test
    void testHQL()
    {
        List<Product> products=productRepository.getProductWithCategoryName("mobiles-ANDROID");
        for(Product product:products)
        {
            System.out.println(product.getTitle());
        }

    }

    @Test
    void testProjection()
    {
        List<ProductProjection> products=productRepository.projectionMethod("mobiles-ANDROID");
        for(ProductProjection product:products)
        {
            System.out.println(product.getTitle()+"  "+product.getPrice());

        }

    }

//    @Test
//    void testNativeQuery(){
//        Product product=productRepository.nativeQuery(3L);
//        System.out.println(product.getTitle()+" "+product.getPrice());
//
//    }
//
    //

    @Test
    @Transactional
    void testFetchType(){
        Optional<ProductCategeory> productCat=categoryRepository.findById(3L);
        if(productCat.isPresent()) {
            System.out.println(productCat.get().getTitle());
            List<Product> products=productCat.get().getProducts();
            for(Product product:products)
            {
                System.out.println(product.getTitle());
            }
        }

    }

    @Test
    @Transactional
    //n+1 calls when fetch type is lazy by changing fetch mode to subselect in product category model will make it 2 db calls instead of n+1 db calls.
    void testFetchMode()
    {
        List<ProductCategeory> categeories=categoryRepository.findByTitleStartingWith("mobile");
        for (ProductCategeory categeory:categeories)
        {
            System.out.println(categeory.getTitle());
            List<Product> products=categeory.getProducts();
            for (Product product:products)
            {
                System.out.println(product.getTitle());
            }
        }
    }


}
