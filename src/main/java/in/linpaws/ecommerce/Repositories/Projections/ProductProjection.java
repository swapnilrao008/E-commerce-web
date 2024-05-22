package in.linpaws.ecommerce.Repositories.Projections;

import in.linpaws.ecommerce.Models.ProductCategeory;

import java.util.Locale;

public interface ProductProjection {
    Long getId();
    String getDescription();
    String getTitle();
    String getPrice();
    ProductCategeory getCategory();
    String getImage();
}
