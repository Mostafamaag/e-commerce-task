package flagak.com.e_commerce.controller;


import flagak.com.e_commerce.dto.ProductDto;
import flagak.com.e_commerce.dto.UpdateProductDto;
import flagak.com.e_commerce.model.Product;
import flagak.com.e_commerce.repository.VendorRepository;
import flagak.com.e_commerce.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;

@RestController
@RequestMapping("/products")
@PreAuthorize("hasRole('ROLE_VENDOR')")
public class ProductController {
    final ProductService productService;
    final VendorRepository vendorRepository;
    public ProductController(ProductService productService, VendorRepository vendorRepository) {
        this.productService = productService;
        this.vendorRepository = vendorRepository;
    }

//    @GetMapping
//    public List<Product> getAllProducts() {
//        return productService.getAllProducts();
//    }


    @GetMapping()
    public List<Product> getProductsByVendor(HttpServletRequest request) {
        Long vendorId = (Long) request.getAttribute("id");
        return productService.getProductsByVendor(vendorId);
    }


    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {
        Long vendorId = (Long) request.getAttribute("id");
        System.out.println("vendorId :" + vendorId);
        Product product = productService.addProduct(vendorId, productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductDto updatedProductDto, HttpServletRequest request) {
        Long vendorId = (Long) request.getAttribute("id");
        Product updatedProduct = productService.updateProduct(productId, vendorId, updatedProductDto);
        System.out.println("updatedProduct :" + updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId, HttpServletRequest request) {
        Long vendorId = (Long) request.getAttribute("id");
        productService.deleteProduct(productId, vendorId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
