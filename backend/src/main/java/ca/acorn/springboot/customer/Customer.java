package ca.acorn.springboot.customer;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Customer {
     @Id
     @SequenceGenerator(
             name = "customer_id_seq",
             sequenceName = "customer_id_seq",
             allocationSize = 1
     )
     @GeneratedValue(
             strategy = GenerationType.SEQUENCE,
             generator = "customer_id_seq"
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