package peaksoft.krossshopp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.krossshopp.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "AND LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%')) " +
           "AND p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> searchProducts(String name, String category, Double minPrice, Double maxPrice);

}
