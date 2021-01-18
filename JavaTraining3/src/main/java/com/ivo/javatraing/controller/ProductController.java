package com.ivo.javatraing.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ivo.javatraing.exc.ProductNotFoundException;
import com.ivo.javatraing.model.Order1;
import com.ivo.javatraing.model.Product;
import com.ivo.javatraing.repo.ProductRepository;
import com.ivo.javatraing.repo.OrderRepository;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

@RestController
public class ProductController {

	final String NAME_SORT = "orderBy";
	final String TYPE_DESC = "direction";
	final String NAME_PAGE = "page";
	final String NAME_PSIZE = "pageSize";
	@Autowired
	ProductRepository repository;

	@Autowired
	OrderRepository repositoryOR;

	@Autowired
	RabbitTemplate template;

	@Autowired
	RabbitTemplate templateConsume;

	@Autowired
	private RestTemplate tmpl;

	// Get all products
	@GetMapping("/products")
	public ResponseEntity<List<Product>> all(@RequestParam Map<String, String> allParams) {
		System.out.println("Parameters are " + allParams.entrySet());

		List<Product> products = null;
		int page = 0;
		int size = 3;
		String nameSort = null;
		String asc_desc = null;
		int flagPagination = 0;
		String myQuery = " ";
		for (String key : allParams.keySet()) {

			switch (key) {
			case NAME_PAGE: {
				myQuery += " Limit " + allParams.get(key);
				page = Integer.parseInt(allParams.get(key));
				flagPagination++;
			}
				break;
			case NAME_PSIZE: {
				myQuery += " OFFSET " + allParams.get(key);
				size = Integer.parseInt(allParams.get(key));
				flagPagination++;
			}
				break;
			case NAME_SORT: {
				myQuery += " ORDER BY " + allParams.get(key);
				nameSort = allParams.get(key);
			}
				break;
			case TYPE_DESC: {
				myQuery += " " + allParams.get(key);
			}

				break;
			}

			if (flagPagination == 2) {
				Pageable paging = PageRequest.of(page, size);
				if (nameSort != null) {
					if (asc_desc == null) {
						paging = PageRequest.of(page, size, Sort.by(nameSort).ascending());
					} else {
						paging = PageRequest.of(page, size, Sort.by(nameSort).descending());
					}

				}
				Page<Product> productsPage = repository.findAll(paging);
				products = productsPage.getContent();

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(products);
	}

	// Create product using rabbit
	@PostMapping("/addProduct")
	public ResponseEntity<?> newProduct(@RequestBody Product newProduct) throws URISyntaxException {

		try {
			ConnectionFactory cf = new ConnectionFactory();
			Connection conn = cf.newConnection();

			Channel ch = conn.createChannel();
			;
			ch.queueDeclare("Hello rooter1", false, false, false, null);
			String message = "Hello man!";
			template.convertAndSend("", "Hello rooter1", newProduct);
			System.out.println("Message was sent to rabbit!!" + LocalDateTime.now());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        repository.save(newProduct);
		return ResponseEntity.status(HttpStatus.OK).body(newProduct);

	}

	// Create product with consume handly
	@PostMapping("/consume")
	public void consumeRabbitMessage() {

		try {
			ConnectionFactory cf = new ConnectionFactory();
			Connection conn = cf.newConnection();

			Channel ch = conn.createChannel();
			ch.queueDeclare("Hello rooter1", false, false, true, null);

			ch.basicConsume("Hello rooter1", true, (consumerTag, message) -> {
				// TODO Auto-generated method stub
				String s = new String(message.getBody(), "UTF-8");
				System.out.println("my message is" + s + ". Receive in time:" + LocalDateTime.now());
			}

					, consumerTag -> {
						// TODO Auto-generated method stub

					}

			);
			System.out.println("Message was consumed of rabbit!!");
//    		 repository.save(newProduct);
//    	        return ResponseEntity.status(HttpStatus.OK).body(newProduct);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Create order
	@PostMapping("/send_to_order_service/{price}")
	public Order1 sentToOrderService(@PathVariable String price, @RequestBody Order1 newOrder) {
		String strURL = "http://ORDER-SERVICE/order-provider/pay/" + price;

		return tmpl.postForObject(strURL, newOrder, Order1.class);
	}

	@DeleteMapping("/delProduct/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id) {
		repository.deleteById(Integer.parseInt(id));

		return ResponseEntity.noContent().build();
	}

	// Get one product
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> one(@PathVariable String id) {

		Product product = repository.findById(Integer.parseInt(id)).orElseThrow(() -> new ProductNotFoundException(id));
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	// Update product
	@PutMapping("/updProduct")
	public ResponseEntity<?> updateProduct(@RequestBody Product product) throws URISyntaxException {
		boolean flag = true;
		Optional<Product> product1 = repository.findById(product.getId());
		if (!product1.isEmpty()) {
			repository.save(product);
			return ResponseEntity.status(HttpStatus.OK).body(product);
		} else {

			return ResponseEntity.notFound().build();
		}

	}
}
