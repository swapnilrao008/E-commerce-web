package in.linpaws.ecommerce.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String title;
    private Double price;
    private String description;
    private ProductCategeory categeory;
    private String imageUrl;
}
