package in.linpaws.ecommerce.Controllers;

import in.linpaws.ecommerce.DTOS.*;
import in.linpaws.ecommerce.Exceptions.ProductNotFoundException;
import in.linpaws.ecommerce.Models.Mobiles;
import in.linpaws.ecommerce.Models.Product;
import in.linpaws.ecommerce.Services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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

    public ProductController(@Qualifier("FakeStoreService") ProductService productService, ModelMapper modelMapper) {
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
    @GetMapping("/mobiles")
    public ResponseEntity<List<MobileResponseDto>> getAllMobiles(@RequestParam("PageNum") int pageNum,@RequestParam("PageSize") int pageSize,@RequestParam("Sortby") String sortParm,@RequestParam("direction") int direction)
    {
        Page<Mobiles> mobiles=productService.getAllMobiles(pageNum,pageSize,sortParm,direction);
        List<MobileResponseDto> mobileResponseDtoList=new ArrayList<>();
        for(Mobiles mobiles1:mobiles)
        {
            mobileResponseDtoList.add(convertToMobileResponseDto(mobiles1));

        }
        return new ResponseEntity<>(mobileResponseDtoList,HttpStatus.OK);

    }

    @GetMapping("/mobile")
    public ResponseEntity<List<MobileResponseDto>> getAllMobiles(@RequestParam("PageNum") int x,@RequestParam("PageSize") int y)
    {
        Page<Mobiles> mobiles=productService.getAllMobiles(x,y);
        List<MobileResponseDto> mobileResponseDtoList=new ArrayList<>();
        for(Mobiles mobiles1:mobiles)
        {
            mobileResponseDtoList.add(convertToMobileResponseDto(mobiles1));

        }
        return new ResponseEntity<>(mobileResponseDtoList,HttpStatus.OK);

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



    @PostMapping("/mobiles")
    public ResponseEntity<MobileResponseDto> createMobile(@RequestBody MobileRequestDto mobileRequestDto)
    {
        Mobiles mobiles=productService.addMobiles(
                mobileRequestDto.getTitle(),
                mobileRequestDto.getPrice(),
                mobileRequestDto.getOs()
        );
        MobileResponseDto mobileResponseDto=convertToMobileResponseDto(mobiles);
        return new ResponseEntity<>(mobileResponseDto,HttpStatus.CREATED);
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

    private MobileResponseDto convertToMobileResponseDto(Mobiles mobiles)
    {

        MobileResponseDto mobileResponseDto=modelMapper.map(mobiles,MobileResponseDto.class);

        return mobileResponseDto;
    }



}
