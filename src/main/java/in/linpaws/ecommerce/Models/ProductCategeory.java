package in.linpaws.ecommerce.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ProductCategeory extends BaseModel{
    //private int id;
    private String title;
    @OneToMany(mappedBy = "categeory")
    List<Product> products;
}
