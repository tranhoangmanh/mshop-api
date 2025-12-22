package dev.manhtran.mshop_api.product.repository;

import dev.manhtran.mshop_api.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    List<Product> findByCategory(String category);
    List<Product> findByActiveTrueAndCategory(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
}
