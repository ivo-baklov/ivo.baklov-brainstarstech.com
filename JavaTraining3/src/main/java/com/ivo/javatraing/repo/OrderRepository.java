package com.ivo.javatraing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivo.javatraing.model.Order1;

@Repository
public interface OrderRepository extends JpaRepository<Order1,Integer>{

}
