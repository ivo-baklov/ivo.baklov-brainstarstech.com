package com.ivo.javatraing.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivo.javatraing.exc.ProductNotFoundException;
import com.ivo.javatraing.model.Product;
import com.ivo.javatraing.repo.ProductRepository;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

@RestController
public class ProductController {

	@Autowired
	ProductRepository repository;
	
	@Autowired
	RabbitTemplate template;
	
	@Autowired
	RabbitTemplate templateConsume;
	
	//Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>>  all() {

        List<Product> products = repository.findAll();
        
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    
  //Create product
    @PostMapping("/addProduct")
    public ResponseEntity<?> newProduct(@RequestBody Product newProduct) throws URISyntaxException {
    
    	ConnectionFactory factory = new ConnectionFactory();
    	 try {
    		 ConnectionFactory cf = new ConnectionFactory();
    		 Connection conn = cf.newConnection();

    		 Channel ch = conn.createChannel();;
    		 ch.queueDeclare("Hello rooter",false,false,false,null);
    		 String message = "Hello man!";
//    		 ch.basicPublish("", "Hello rooter", false, null,message.getBytes("UTF-8"));
    		 template.convertAndSend("","Hello rooter",newProduct);
    		 System.out.println("Message was sent to rabbit!!"+ LocalDateTime.now());
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
    
  //Create product
    @PostMapping("/consume")
    public void consumeRabbitMessage(){
        
    	ConnectionFactory factory = new ConnectionFactory();
    	 try {
    		 ConnectionFactory cf = new ConnectionFactory();
    		 Connection conn = cf.newConnection();

    		 Channel ch = conn.createChannel();;
    		 ch.queueDeclare("Hello rooter",false,false,false,null);
    		
    		 ch.basicConsume("Hello rooter", true, (consumerTag, message) -> {
					// TODO Auto-generated method stub
    			 String s =new String (message.getBody(),"UTF-8");
    			 System.out.println("my message is"+s+". Receive in time:"+LocalDateTime.now());
				}
    			 
    		 ,consumerTag -> {
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
        
    @DeleteMapping("/delProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        repository.deleteById(Integer.parseInt(id));

        return ResponseEntity.noContent().build();
    }
    
  //Get one product
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> one(@PathVariable String id) {

        Product product = repository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    
  //Update product
    @PutMapping("/updProduct")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) throws URISyntaxException {
    	boolean flag=true;
    	 Optional<Product> product1 = repository.findById(product.getId());
    	 if (!product1.isEmpty()) {
    		 repository.save(product);
    	 		return ResponseEntity.status(HttpStatus.OK).body(product);
    	 }
    	 else {
    		 	
    	 		return ResponseEntity.notFound().build();
    	 }
        

    }
}
