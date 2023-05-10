package ca.acorn.springboot.customer;

import java.util.List;
import java.util.Optional;
public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional <Customer> selectCustomerById(Long id);
    void insertCustomer(Customer customer);
    void deleteCustomerById(Long id);
    void updateCustomer(Customer customerToUpdate);
    boolean existsCustomerById(Long id);
    boolean existsPersonWithEmail(String email);
}