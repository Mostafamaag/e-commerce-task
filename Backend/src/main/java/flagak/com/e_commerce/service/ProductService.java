package flagak.com.e_commerce.service;

import flagak.com.e_commerce.dto.ProductDto;
import flagak.com.e_commerce.dto.UpdateProductDto;
import flagak.com.e_commerce.exception.NotFoundException;
import flagak.com.e_commerce.exception.UnauthorizedAccessException;
import flagak.com.e_commerce.model.Product;
import flagak.com.e_commerce.model.Vendor;
import flagak.com.e_commerce.repository.ProductRepository;
import flagak.com.e_commerce.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;

    public ProductService(ProductRepository productRepository, VendorRepository vendorRepository) {
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products;
    }

    // add a new product for a vendor
    public Product addProduct(Long vendorId, ProductDto productDto) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new NotFoundException("Vendor not found"));

        Product newProduct = new Product();
        newProduct.setTitle(productDto.getTitle());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setStock(productDto.getQuantity());
        newProduct.setVendor(vendor);
        System.out.println(newProduct);
        return productRepository.save(newProduct);
    }

    // get a product by its ID
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    // get all products for a specific vendor
    public List<Product> getProductsByVendor(Long vendorId) {
        System.out.println("vendorid :"+vendorId);
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(
                () -> new NotFoundException("Vendor not found"));

        List<Product> products = productRepository.findByVendor(vendor);
        if (products.isEmpty()) {
            throw new NotFoundException("No products found for this vendor");
        }
        return products;
    }

    // update an existing product
    public Product updateProduct(Long productId, Long vendorId, UpdateProductDto updatedProductDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (!existingProduct.getVendor().getId().equals(vendorId)) {
            throw new UnauthorizedAccessException("You are not authorized to update this product");
        }

        if(updatedProductDto.getTitle() != null) existingProduct.setTitle(updatedProductDto.getTitle());
        if(updatedProductDto.getDescription() != null) existingProduct.setDescription(updatedProductDto.getDescription());
        if(updatedProductDto.getPrice() != null) existingProduct.setPrice(updatedProductDto.getPrice());
        if(updatedProductDto.getQuantity() != null) existingProduct.setStock(updatedProductDto.getQuantity());
        productRepository.save(existingProduct);
        return existingProduct;
    }

    // delete a product
    public void deleteProduct(Long productId, Long vendorId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (!existingProduct.getVendor().getId().equals(vendorId)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this product");
        }

        productRepository.delete(existingProduct);
    }

    public void deductStock(Product product, Integer amount) {
        product.setStock(product.getStock() - amount);
        productRepository.save(product);
    }
}
