package in.linpaws.ecommerce.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class ProductCategeory extends BaseModel{
    //private int id;
    private String title;
    //private String desc;
    //private int pid;
    @OneToMany(mappedBy = "categeory")
    @Fetch(value = FetchMode.SUBSELECT)
    List<Product> products;
}
