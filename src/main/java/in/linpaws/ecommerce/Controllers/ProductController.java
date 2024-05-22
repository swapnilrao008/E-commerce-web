package in.linpaws.ecommerce.Controllers;

import in.linpaws.ecommerce.DTOS.ErrorDto;
import in.linpaws.ecommerce.DTOS.ProductRequestDto;
import in.linpaws.ecommerce.DTOS.ProductResponseDto;
import in.linpaws.ecommerce.Exceptions.ProductNotFoundException;
import in.linpaws.ecommerce.Models.Product;
import in.linpaws.ecommerce.Services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;

    public ProductController(@Qualifier("SelfProductService") ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductDetail(@PathVariable("id") int ProductId) throws ProductNotFoundException {
        Product product=productService.getSingleProduct(ProductId);
        //return modelMapper.map(product,ProductResponseDto.class);
       return new ResponseEntity<>(convertToProductResponseDto(product),HttpStatus.FOUND);
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProductDetails()
    {
        List<Product> products=productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos=new ArrayList<>();
        for(Product product:products)
        {
            productResponseDtos.add(convertToProductResponseDto(product));
        }
        return new ResponseEntity<>(productResponseDtos,HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto)
    {
        Product product=productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription(),
                productRequestDto.getCategory(),
                productRequestDto.getImage()
        );
        ProductResponseDto productResponseDto=convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.CREATED);
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("id") int productId)
    throws ProductNotFoundException{

        Product product=productService.deleteProduct(productId);
        ProductResponseDto productResponseDto=convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
    }

    //patch method is not supported in rest template

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") int productId,
                                                            @RequestBody ProductRequestDto productRequestDto) throws ProductNotFoundException {
        Product product=productService.updateProduct(productId,
                productRequestDto.getTitle(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription(),
                productRequestDto.getCategory(),
                productRequestDto.getImage()
        );
        ProductResponseDto productResponseDto=convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> replaceProduct(@PathVariable("id") int productId,
                                                            @RequestBody ProductRequestDto productRequestDto) throws ProductNotFoundException {
        Product product = productService.replaceProduct(productId,
                productRequestDto.getTitle(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription(),
                productRequestDto.getCategory(),
                productRequestDto.getImage()
        );
ProductResponseDto productResponseDto=convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);

    }




    private ProductResponseDto convertToProductResponseDto(Product product)
    {
        String categeoryTitle=product.getCategeory().getTitle();
        ProductResponseDto productResponseDto=modelMapper.map(product,ProductResponseDto.class);
        productResponseDto.setCategory(categeoryTitle);

        return productResponseDto;
    }



}
