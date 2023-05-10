package ca.acorn.springboot.customer;

import ca.acorn.springboot.exceptions.DuplicateResourceException;
import ca.acorn.springboot.exceptions.RequestValidationException;
import ca.acorn.springboot.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        // When
        underTest.getAllCustomers();

        // Then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void getCustomer() {
        // Given
        Long id = 25L;
        Customer customer = new Customer(
                id,
                "Alex",
                "Alex@test.com",
                20
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // When
        Customer actual = underTest.getCustomer(id);

        // Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional() {
        // Given
        Long id = 25L;
        Customer customer = new Customer(
                id,
                "Alex",
                "Alex@test.com",
                20
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("customer with id [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        // Given
        String email = "Alex@test.com";
        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",
                "Alex@test.com",
                20
        );

        // When
        underTest.addCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowExceptionWhenEmailExistsWhileAddingCustomer() {
        // Given
        String email = "Alex@test.com";
        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",
                "Alex@test.com",
                20
        );

        // When
        assertThatThrownBy(() ->
                underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Customer with email [%s] is already exists"
                        .formatted(request.email()));
        // Then
        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomer() {
        // Given
        Long id = 1L;
        when(customerDao.existsCustomerById(id)).thenReturn(true);

        // When
        underTest.deleteCustomer(id);

        // Then
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void willThrowExceptionWhenCustomerExistsWhileDeletingCustomer() {
        // Given
        Long id = 10L;
        when(customerDao.existsCustomerById(id)).thenReturn(false);

        // When
        assertThatThrownBy(() ->
                underTest.deleteCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class);

        // Then
        verify(customerDao,never()).deleteCustomerById(id);
    }

    @Test
    void canUpdateAllCustomerProperties() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(id,
                "Alex",
                "Alex@test.com",
                20);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("NewAlex",
                "newAlex@test.com",
                25
        );

        // When
        underTest.updateCustomer(id,updateRequest);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // Then
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canUpdateCustomerName() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(id,
                "Alex",
                "Alex@test.com",
                20);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("NewAlex",
                null,
                null
        );

        // When
        underTest.updateCustomer(id,updateRequest);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // Then
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateCustomerEmail() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(id,
                "Alex",
                "Alex@test.com",
                20);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null,
                "newAlex@test.com",
                null
        );

        // When
        underTest.updateCustomer(id,updateRequest);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // Then
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateCustomerAge() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(id,
                "Alex",
                "Alex@test.com",
                20);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null,
                null,
                25
        );

        // When
        underTest.updateCustomer(id,updateRequest);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // Then
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void willThrowExceptionWhenCustomerUpdateHasNoChanges() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(id,
                "Alex",
                "Alex@test.com",
                20);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );

        // When
        assertThatThrownBy(() ->
                underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessageContaining("no data changes found");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }
}