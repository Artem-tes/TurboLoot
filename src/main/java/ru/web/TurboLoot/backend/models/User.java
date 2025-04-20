package ru.web.TurboLoot.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "inventory")
    private List<Integer> inventory;

    @Column(name = "count_cases")
    private Integer countCases;

    @Column(name = "count_inventory")
    private Integer countInventory;

    @Column(name = "transactional_id")
    private List<Integer> transactional;



}
