package flagak.com.e_commerce.controller;

import flagak.com.e_commerce.model.Cart;
import flagak.com.e_commerce.model.CartItem;
import flagak.com.e_commerce.model.Order;
import flagak.com.e_commerce.model.Product;
import flagak.com.e_commerce.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('ROLE_USER')")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Cart cart = cartService.getCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Product> addProductToCart(HttpServletRequest request, @PathVariable Long productId) {

        Long userId = (Long) request.getAttribute("id");
        CartItem cartItem = cartService.addProductToCart(userId, productId, 1);
        System.out.println(cartItem.getProduct().getTitle());
        return new ResponseEntity<>(cartItem.getProduct(), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProductFromCart(
            HttpServletRequest request,
            @PathVariable Long productId) {
        Long userId = (Long) request.getAttribute("id");
        System.out.println("product id" + productId);
        cartService.removeProductFromCart(userId, productId);
        return new ResponseEntity<>("Product removed from cart", HttpStatus.OK);
    }



    @PostMapping("/checkout")
    public ResponseEntity<Order> checkoutCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Order order = cartService.checkoutCart(userId);
        return ResponseEntity.ok(order);
    }
}
