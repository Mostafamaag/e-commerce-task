package flagak.com.e_commerce.controller;


import flagak.com.e_commerce.model.Order;
import flagak.com.e_commerce.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@PreAuthorize("hasRole('ROLE_USER')")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public List<Order> getOrders(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        return orderService.getOrders(userId);
    }
}