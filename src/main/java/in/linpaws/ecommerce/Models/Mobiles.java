package in.linpaws.ecommerce.Models;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Mobiles extends BaseModel{
    private String title;
    private Double price;
    private String os;
}
