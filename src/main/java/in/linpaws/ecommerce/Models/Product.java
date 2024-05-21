package in.linpaws.ecommerce.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
    //private int id;
    private String title;
    private Double price;
    private String description;
    @ManyToOne(cascade ={CascadeType.PERSIST})
    private ProductCategeory categeory;
    private String imageUrl;
}
