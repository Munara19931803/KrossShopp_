package peaksoft.krossshopp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.krossshopp.model.Cart;
import peaksoft.krossshopp.model.Order;
import peaksoft.krossshopp.model.Product;
import peaksoft.krossshopp.repository.CartRepository;
import peaksoft.krossshopp.repository.OrderRepository;
import peaksoft.krossshopp.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Long userId, String address) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Корзина пуста!");
        }

        double totalAmount = 0;

        for (Cart cartItem : cartItems) {
            Optional<Product> productOpt = productRepository.findById(cartItem.getProductId());
            if (productOpt.isEmpty()) {
                throw new RuntimeException("Продукт не найден!");
            }

            Product product = productOpt.get();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Недостаточно товара на складе для " + product.getName());
            }

            totalAmount += product.getPrice() * cartItem.getQuantity();
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден!"));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}