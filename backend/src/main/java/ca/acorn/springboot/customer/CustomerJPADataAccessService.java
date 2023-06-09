package ca.acorn.springboot.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
@AllArgsConstructor
public class CustomerJPADataAccessService implements CustomerDao {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public void updateCustomer(Customer customerToUpdate) {
        customerRepository.save(customerToUpdate);
    }
}
