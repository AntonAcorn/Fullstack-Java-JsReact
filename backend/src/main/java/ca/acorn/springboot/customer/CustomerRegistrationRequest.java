package ca.acorn.springboot.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
