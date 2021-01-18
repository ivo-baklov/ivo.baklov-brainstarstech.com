package com.ivo.training.eureka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ivo.training.eureka.model.*;

@Repository
public interface OrderRepository extends JpaRepository<Order1,Integer>{

}
