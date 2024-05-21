package in.linpaws.ecommerce.DTOS;

import in.linpaws.ecommerce.Models.Product;
import in.linpaws.ecommerce.Models.ProductCategeory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreDto {
    private long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;

public Product toProduct(){
    Product product=new Product();
    product.setId(id);
    product.setTitle(title);
    product.setPrice(price);
    product.setDescription(description);

    ProductCategeory productCategeory=new ProductCategeory();
    productCategeory.setTitle(category);
    product.setCategeory(productCategeory);
    product.setImageUrl(image);

    return product;

}

}
