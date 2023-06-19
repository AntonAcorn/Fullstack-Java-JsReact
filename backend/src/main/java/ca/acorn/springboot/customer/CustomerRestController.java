package ca.acorn.springboot.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Long customerId) {
       return customerService.getCustomer(customerId);
    }

    @PostMapping
    public  void registerCustomer (
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        customerService.addCustomer(customerRegistrationRequest);
    }

    @PutMapping("/{customerId}")
    public void updateCustomer (@PathVariable Long customerId,
                                @RequestBody CustomerUpdateRequest customerUpdateRequest){
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
    }
}
