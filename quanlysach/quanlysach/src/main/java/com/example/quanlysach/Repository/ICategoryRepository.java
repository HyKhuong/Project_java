package com.example.quanlysach.Repository;

import com.example.quanlysach.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository  extends JpaRepository<Category,Long> {
    /*server.error.path=/error*/
}
