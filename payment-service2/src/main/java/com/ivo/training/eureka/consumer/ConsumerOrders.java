package com.ivo.training.eureka.consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivo.training.eureka.model.Order1;
import com.ivo.training.eureka.repo.OrderRepository;

@Component
@RabbitListener(queues = "Order")
public class ConsumerOrders {
	@Autowired
	OrderRepository repository;

	@RabbitHandler
	public void consumeMessageFromQueue(Order1 or) {
//		try {
////			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("Message Receive form queue is:id:" + or.getId() + ",Quantity:" + or.getQuantity()
				+ "->in time:" + LocalDateTime.now());

		repository.save(or);
	}
}
