//package ca.acorn.springboot.graphql;
//
//import ca.acorn.springboot.customer.Customer;
//import ca.acorn.springboot.customer.CustomerRegistrationRequest;
//import ca.acorn.springboot.customer.CustomerService;
//import graphql.kickstart.tools.GraphQLQueryResolver;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//public class CustomerMutations implements GraphQLQueryResolver {
//    private final CustomerService customerService;
//    public Customer createCustomer(CustomerRegistrationRequest customerRequest){
//        Customer customer = new Customer();
//        customer.setName(customerRequest.name());
//        customer.setEmail(customerRequest.email());
//        customer.setAge(customerRequest.age());
//        customerService.addCustomer(customerRequest);
//        return customer;
//    }
//}
