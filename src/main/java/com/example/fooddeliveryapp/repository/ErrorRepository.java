package com.example.fooddeliveryapp.repository;

import com.example.fooddeliveryapp.model.Error;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorRepository extends JpaRepository<Error, Long> {
}
