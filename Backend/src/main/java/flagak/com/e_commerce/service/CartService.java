package flagak.com.e_commerce.service;

import flagak.com.e_commerce.exception.NotFoundException;
import flagak.com.e_commerce.model.*;
import flagak.com.e_commerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository, OrderService orderService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    // add a product to the user's cart
    public CartItem addProductToCart(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new NotFoundException("Product not found"));

        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NotFoundException("User not found"));

        // get cart, create one if not exist
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> createCart(user));

        // check if the product is already in the cart
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        // if product exists? update quantity, else add new CartItem
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            return cartItemRepository.save(cartItem);
        }
    }

    // remove a product from the user cart
    public void removeProductFromCart(Long userId, Long productId) {

        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new NotFoundException("Cart not found"));

        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new NotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new NotFoundException("Product not found in cart"));

        cartItemRepository.delete(cartItem);
    }

    // create a new cart for the user
    private Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }


    public Cart getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new NotFoundException("Cart not found"));

        return cart;
    }

    public Order checkoutCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));


        Cart cart = user.getCart();
        if (cart == null) {
            throw new NotFoundException("No cart found for this user");
        }

        if (cart.getCartItems().isEmpty()) {
            System.out.println("No cart items found for this user");
            throw new NotFoundException("No product found in the cart. Cannot proceed with checkout.");
        }


        return orderService.makeOrder(cart, user);
    }
}
