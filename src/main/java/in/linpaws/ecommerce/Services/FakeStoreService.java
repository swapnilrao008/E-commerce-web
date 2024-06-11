package in.linpaws.ecommerce.Services;

import in.linpaws.ecommerce.DTOS.FakeStoreDto;
import in.linpaws.ecommerce.DTOS.ProductResponseDto;
import in.linpaws.ecommerce.Exceptions.ProductNotFoundException;
import in.linpaws.ecommerce.Models.Mobiles;
import in.linpaws.ecommerce.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreService")
public class FakeStoreService implements ProductService{
    private RestTemplate restTemplate;
    private RedisTemplate redisTemplate;

    public FakeStoreService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Product getSingleProduct(long productId) throws ProductNotFoundException {

        Product productInCache=(Product) redisTemplate.opsForHash()
                .get("PRODUCTS","PRODUCTS_"+productId);
        if(productInCache!=null)
        {
            System.out.println("Cache hit for product "+productId);
            return productInCache;
        }
        System.out.println("Cache hit for product "+productId);
        FakeStoreDto fakeStoreDto=restTemplate.getForObject("http://fakestoreapi.com/products/"+productId, FakeStoreDto.class);
        if(fakeStoreDto==null)
        {
            throw new ProductNotFoundException(
                    "Product with "+productId+" not found"+" try a product with id less than 21"
            );
        }

        Product product= fakeStoreDto.toProduct();
        redisTemplate.opsForHash().put("PRODUCTS","PRODUCTS_"+productId,product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreDto[] fakeStoreDtos=restTemplate.getForObject("http://fakestoreapi.com/products/",FakeStoreDto[].class);

        List<Product> products=new ArrayList<>();
        for (FakeStoreDto fakeStoreDto: fakeStoreDtos)
        {
            products.add(fakeStoreDto.toProduct());
        }
        return products;
    }

    @Override
    public Page<Mobiles> getAllMobiles(int pageNum,int pageSize,String sortParam,int Direction) {
        return null;
    }

    @Override
    public Page<Mobiles> getAllMobiles(int x, int y) {
        return null;
    }

    @Override
    public Product addProduct(
            String title,
    Double price,
    String description,
    String category,
    String image
    ) {
        FakeStoreDto fakeStoreDto=new FakeStoreDto();
        fakeStoreDto.setTitle(title);
        fakeStoreDto.setPrice(price);
        fakeStoreDto.setDescription(description);
        fakeStoreDto.setCategory(category);
        fakeStoreDto.setImage(image);
        return fakeStoreDto.toProduct();


    }

    @Override
    public Mobiles addMobiles(String title, Double price, String os) {
        return null;
    }

    @Override
    public Product deleteProduct(long productId) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto=restTemplate.exchange(
                "http://fakestoreapi.com/products/"+productId, HttpMethod.DELETE,null, FakeStoreDto.class).getBody();
        if(fakeStoreDto==null)
            throw new ProductNotFoundException(
                    "Product with id "+productId+" not found try to delete " +
                            "a product with id less than 21"
            );
        return fakeStoreDto.toProduct();
    }

    //patch method is not supported in rest template
    @Override
    public Product updateProduct(long productId,String title, Double price, String description, String category, String image) throws ProductNotFoundException {

        FakeStoreDto fakeStoreDto=new FakeStoreDto();
        fakeStoreDto.setTitle(title);
        fakeStoreDto.setPrice(price);
        fakeStoreDto.setDescription(description);
        fakeStoreDto.setCategory(category);
        fakeStoreDto.setImage(image);

        fakeStoreDto.setId(productId);
        return fakeStoreDto.toProduct();
    }

    @Override
    public Product replaceProduct(long productId, String title, Double price, String description, String category, String image) throws ProductNotFoundException {
        FakeStoreDto requestDto=new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setPrice(price);
        requestDto.setDescription(description);
        requestDto.setCategory(category);
        requestDto.setImage(image);

        HttpEntity<FakeStoreDto> requestEntity=new HttpEntity<>(requestDto);
        FakeStoreDto response=restTemplate.exchange("http://fakestoreapi.com/products/"+productId,
                HttpMethod.PUT,requestEntity, FakeStoreDto.class).getBody();
        if (response==null) {
            throw new ProductNotFoundException("Product with " + productId + " ot found");
        }
        return response.toProduct();

    }
}
