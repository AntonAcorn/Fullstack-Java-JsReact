package ca.acorn.springboot.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Customer {
     @Id
     @GeneratedValue(
             strategy = GenerationType.IDENTITY
     )
     private Long id;
     @Column(
             nullable = false
     )
     private String name;
     @Column(
             unique = true
     )
     private String email;
     @Column(
             nullable = false
     )
     private Integer age;

     public Customer(String name, String email, Integer age) {
          this.name = name;
          this.email = email;
          this.age = age;
     }
}