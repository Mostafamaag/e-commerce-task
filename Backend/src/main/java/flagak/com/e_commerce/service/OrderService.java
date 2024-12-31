package flagak.com.e_commerce.service;

import flagak.com.e_commerce.exception.NotFoundException;
import flagak.com.e_commerce.model.*;
import flagak.com.e_commerce.repository.CartItemRepository;
import flagak.com.e_commerce.repository.CartRepository;
import flagak.com.e_commerce.repository.OrderRepository;
import flagak.com.e_commerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final AccountPayableService accountPayableService;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, ProductService productService, CartItemRepository cartItemRepository, AccountPayableService accountPayableService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.accountPayableService = accountPayableService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order makeOrder(Cart cart, User user) {

        // create new Order
        Order order = new Order();
        order.setUser(user);
        double totalAmount = 0;

        // handle deductions
        List<Pair<Product, Integer>> productsToDeducte = new ArrayList<>();


        // convert cart items to order items
        for (CartItem cartItem : cart.getCartItems()) {

            Product product = cartItem.getProduct();
            // check stock
            if (product.getStock() == null || product.getStock() < cartItem.getQuantity()) {
                throw new NotFoundException("Insufficient stock for product: " + product.getTitle());
            }

            productsToDeducte.add(Pair.of(product, cartItem.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            totalAmount += product.getPrice() * cartItem.getQuantity();
            order.getItems().add(orderItem);

        }

        order.setTotalAmount(totalAmount);

        // save order
        orderRepository.save(order);

        // handle products deductions if order created
        for (Pair<Product, Integer> deduction : productsToDeducte) {
            Product product = deduction.getFirst();
            int quantityToDeduct = deduction.getSecond();
            productService.deductStock(product, quantityToDeduct);
        }

        // create account payable for order
        accountPayableService.createAccountPayable(order);

        // free cart
        cartItemRepository.deleteAllByCart(cart);

        return order;
    }

    public List<Order> getOrders(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found"));

        return orderRepository.findByUser(user);
    }

}
