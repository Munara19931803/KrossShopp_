package peaksoft.krossshopp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.krossshopp.model.Cart;
import peaksoft.krossshopp.model.Product;
import peaksoft.krossshopp.repository.CartRepository;
import peaksoft.krossshopp.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Продукт не найден!");
        }

        Product product = productOpt.get();

        if (product.getStock() < quantity) {
            throw new RuntimeException("Недостаточно товара на складе!");
        }

        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        if (existingCartItem.isPresent()) {
            Cart cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity); // Увеличиваем количество
            return cartRepository.save(cartItem);
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден в корзине!"));
        cartRepository.delete(cart);
    }

    public void clearCart(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartItems);
    }
}

