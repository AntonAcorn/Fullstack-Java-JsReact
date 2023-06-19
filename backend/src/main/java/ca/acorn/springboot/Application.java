package ca.acorn.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


//    @Bean
//    CommandLineRunner runner(CustomerRepository customerRepository) {
//        return args -> {
//            Faker faker = new Faker();
//            Random random = new Random();
//            Customer customer = new Customer(
//                    faker.name().fullName(),
//                    faker.internet().emailAddress(),
//                    random.nextInt(16,99));
//
//
//            Customer kateryna = new Customer(
//                    faker.name().fullName(),
//                    faker.internet().emailAddress(),
//                    random.nextInt(8,99));
//            List<Customer> customers = List.of(customer, kateryna);
//            customerRepository.saveAll(customers);
//        }
//        ;

    }

