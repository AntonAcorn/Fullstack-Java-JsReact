package ca.acorn.springboot.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer anton = new Customer(1L,
                "Anton",
                "Anton@test@gmail.com",
                21);
        customers.add(anton);

        Customer kateryna = new Customer(2L,
                "Kateryna",
                "Kateryna@test@gmail.com",
                20);
        customers.add(kateryna);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long customerId) {
        return customers.stream().filter(customer -> customer.getId().equals(customerId)).findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customers.stream()
                .anyMatch(customer -> customer.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customers.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer customerToUpdate) {
        customers.add(customerToUpdate);
    }
}
