package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

}