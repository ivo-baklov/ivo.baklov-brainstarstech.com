package com.ivo.training.eureka.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivo.training.eureka.model.Order1;
import com.ivo.training.eureka.repo.OrderRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@RestController
@RequestMapping("/order-provider")
public class OrderController {
	@Autowired
	OrderRepository repository;

	@Autowired
	RabbitTemplate template;

	@PostMapping("/pay/{price}")
	public Order1 pay(@PathVariable int price, @RequestBody Order1 or) {

		try {
			ConnectionFactory cf = new ConnectionFactory();
			Connection conn = cf.newConnection();
			Channel ch = conn.createChannel();
			ch.queueDeclare("Order", false, false, false, null);
			template.convertAndSend("", "Order", or);
			System.out.println("Message was sent to rabbit!!" + LocalDateTime.now());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		repository.save(or);
		return or;

	}
}
