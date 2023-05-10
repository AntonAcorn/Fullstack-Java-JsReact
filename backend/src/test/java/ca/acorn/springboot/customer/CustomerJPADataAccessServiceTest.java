package ca.acorn.springboot.customer;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

@RequiredArgsConstructor
class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // When
        underTest.selectAllCustomers();

        // Then
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        // Given
        Long id = 1L;

        // When
        underTest.selectCustomerById(id);

        // Then
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        // Given
        Customer customerToInsert = new Customer(
                "Temp",
                "Temp@temp.com",
                25
        );

        // When
        underTest.insertCustomer(customerToInsert);

        // Then
        verify(customerRepository).save(customerToInsert);
    }

    @Test
    void existsPersonWithEmail() {
        // Given
        String email = "Temp@temp.com";

        // When
        underTest.existsPersonWithEmail(email);

        // Then
        verify(customerRepository).existsByEmail(email);

    }

    @Test
    void deleteCustomerById() {
        // Given
        Long id = 1L;

        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerRepository).deleteById(id);
    }

    @Test
    void existsCustomerById() {
        // Given
        Long id = 1L;

        // When
        underTest.existsCustomerById(id);

        // Then
        verify(customerRepository).existsById(id);
    }

    @Test
    void updateCustomer() {
        // Given
        Customer customerToInsert = new Customer(
                6L,
                "Temp",
                "Temp@temp.com",
                25
        );

        // When
        underTest.updateCustomer(customerToInsert);

        // Then
        verify(customerRepository).save(customerToInsert);
    }
}