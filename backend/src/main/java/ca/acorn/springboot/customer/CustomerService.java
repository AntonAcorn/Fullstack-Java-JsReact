package ca.acorn.springboot.customer;

import ca.acorn.springboot.exceptions.DuplicateResourceException;
import ca.acorn.springboot.exceptions.RequestValidationException;
import ca.acorn.springboot.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Long id) {
        return customerDao.selectCustomerById(id).orElseThrow(() ->  new ResourceNotFoundException(
                "customer with id [%s] not found".formatted(id)
        ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        // Check if email exists
        if(customerDao.existsPersonWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException
                    ("Customer with email [%s] is already exists"
                            .formatted(customerRegistrationRequest.email()));
        }

        // Add
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age());
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomer(Long id){
        if(!(customerDao.existsCustomerById(id))){
            throw new ResourceNotFoundException
                    ("Customer with id [%d] does not exists".formatted(id));
        }
        customerDao.deleteCustomerById(id);
    }

    public void updateCustomer(Long customerId,
                               CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = getCustomer(customerId);

        boolean changes = false;

        if(customerUpdateRequest.name() != null &&
            !customerUpdateRequest.name().equals(customer.getName())){
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if(customerUpdateRequest.age() != null &&
        !customerUpdateRequest.age().equals(customer.getAge())){
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if(customerUpdateRequest.email() != null &&
        !customerUpdateRequest.email().equals(customer.getEmail())){
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }
}
