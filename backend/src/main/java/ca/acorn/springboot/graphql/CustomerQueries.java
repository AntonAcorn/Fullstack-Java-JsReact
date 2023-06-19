package ca.acorn.springboot.graphql;

import ca.acorn.springboot.customer.Customer;
import ca.acorn.springboot.customer.CustomerRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;

import java.util.List;

//@Component
@RequiredArgsConstructor
public class CustomerQueries implements GraphQLQueryResolver {
    private final CustomerRepository customerRepository;
    public List<Customer> getCustomerById(Long id){
        return this.customerRepository.findAll();
    }
}
