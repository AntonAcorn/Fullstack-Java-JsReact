package ca.acorn.springboot.customer;

import ca.acorn.springboot.AbstractTestcontainersUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainersUnitTest {

    private CustomerJDBCDataAccessService underTest;
    private CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().emailAddress() + UUID.randomUUID(),
                20

        );
        underTest.insertCustomer(customer);

        // When
        List<Customer> actual = underTest.selectAllCustomers();

        // Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        String email = FAKER.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20

        );
        underTest.insertCustomer(customer);
        Long customerId = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        Optional<Customer> actualCustomer = underTest.selectCustomerById(customerId);

        // Then
        assertThat(actualCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(customerId);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void WillReturnEmptyWhenSelectCustomerById() {
        // Given
        Long id = -1L;

        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().emailAddress() + UUID.randomUUID(),
                20
        );

        // When
        underTest.insertCustomer(customer);
        Long customerId = customer.getId();

        // Then
        assertThat(customer.getId()).isEqualTo(customerId);
    }

    @Test
    void deleteCustomerById() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().emailAddress() + UUID.randomUUID(),
                20
        );

        // When
        underTest.insertCustomer(customer);
        Long customerId = customer.getId();
        Optional<Customer> actual = underTest.selectCustomerById(customerId);
        underTest.deleteCustomerById(customerId);

        // Then
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateCustomerName() {
        // Given
        String email = FAKER.internet().emailAddress() + UUID.randomUUID();
        int age = 20;
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                age
        );
        underTest.insertCustomer(customer);

        Long customerId = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();

        // When
        Customer newCustomer = new Customer();
        newCustomer.setId(customerId);
        String newName = "newName";
        newCustomer.setName(newName);
        underTest.updateCustomer(newCustomer);

        // Then
        Optional<Customer> updatedCustomer = underTest.selectCustomerById(customerId);
        assertThat(updatedCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(customerId);
            assertThat(c.getName()).isEqualTo(newName); //changed
            assertThat(c.getEmail()).isEqualTo(email);
            assertThat(c.getAge()).isEqualTo(age);
        });
    }

    @Test
    void updateCustomerEmail() {
        // Given
        String email = FAKER.internet().emailAddress() + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                20
        );
        underTest.insertCustomer(customer);

        Long customerId = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();

        String newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When
        Customer newCustomer = new Customer();
        newCustomer.setId(customerId);
        newCustomer.setEmail(newEmail);
        underTest.updateCustomer(newCustomer);

        // Then
        Optional<Customer> updatedCustomer = underTest.selectCustomerById(customerId);
        assertThat(updatedCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(customerId);
            assertThat(c.getName()).isEqualTo(name);
            assertThat(c.getEmail()).isEqualTo(newEmail); // changed
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void updateCustomerAge() {
        // Given
        String email = FAKER.internet().emailAddress() + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                20
        );
        underTest.insertCustomer(customer);

        Long customerId = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();

        int newAge = 100;

        // When
        Customer newCustomer = new Customer();
        newCustomer.setId(customerId);
        newCustomer.setAge(newAge);
        underTest.updateCustomer(newCustomer);

        // Then
        Optional<Customer> updatedCustomer = underTest.selectCustomerById(customerId);
        assertThat(updatedCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(customerId);
            assertThat(c.getName()).isEqualTo(name);
            assertThat(c.getEmail()).isEqualTo(email); // changed
            assertThat(c.getAge()).isEqualTo(newAge);
        });
    }

    @Test
    void updateCustomerAllProperties() {
        // Given
        String email = FAKER.internet().emailAddress() + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                20
        );
        underTest.insertCustomer(customer);

        Long customerId = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();

        String newName = FAKER.name().fullName();
        String newEmail = FAKER.internet().emailAddress() + UUID.randomUUID();
        int newAge = 100;



        // When
        Customer newCustomer = new Customer();
        newCustomer.setId(customerId);
        newCustomer.setName(newName);
        newCustomer.setEmail(newEmail);
        newCustomer.setAge(newAge);
        underTest.updateCustomer(newCustomer);

        // Then
        Optional<Customer> updatedCustomer = underTest.selectCustomerById(customerId);
        assertThat(updatedCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(customerId);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getEmail()).isEqualTo(newEmail); // changed
            assertThat(c.getAge()).isEqualTo(newAge);
        });
    }


    @Test
    void existsCustomerById() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().emailAddress() + UUID.randomUUID(),
                20
        );

        // When
        underTest.insertCustomer(customer);

        Long customerId = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();

        boolean isExist = underTest.existsCustomerById(customerId);

        // Then
        assertThat(isExist).isTrue();
    }

    @Test
    void existsPersonWithIdWillReturnFalseWhenIdIsNotExist() {
        // Given
        Long customerId = -1L;

        // When
        boolean isExist = underTest.existsCustomerById(customerId);

        // Then
        assertThat(isExist).isFalse();
    }

    @Test
    void existsPersonWithEmail() {
        // Given
        String email = FAKER.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);

        // When
        boolean isExist = underTest.existsPersonWithEmail(email);

        // Then
        assertThat(isExist).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnsFallsWhenDoesNotExist() {
        // Given
        String email = FAKER.internet().emailAddress() + UUID.randomUUID();

        // When
        boolean isExist = underTest.existsPersonWithEmail(email);

        // Then
        assertThat(isExist).isFalse();
    }
}