package in.linpaws.ecommerce.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private int id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
