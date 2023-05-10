package ca.acorn.springboot.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
