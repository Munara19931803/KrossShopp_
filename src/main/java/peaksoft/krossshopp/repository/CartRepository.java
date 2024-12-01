package peaksoft.krossshopp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.krossshopp.model.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);

    List<Cart> findByUserId(Long userId);
}
