package com.ivo.javatraing.consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivo.javatraing.model.Product;
import com.ivo.javatraing.repo.ProductRepository;

@Component
public class ConsumerProducts {
	@Autowired
	ProductRepository repository;

	@RabbitListener(queues="Hello rooter")
	public void consumeMessageFromQueue(Product prod) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Message Receive form queue is:id:"+prod.getId()+",Name:"+prod.getName()+"->in time:"+LocalDateTime.now());

        repository.save(prod);
	}
}
