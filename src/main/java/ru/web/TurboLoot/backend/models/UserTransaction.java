package ru.web.TurboLoot.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private Integer typeTransaction;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "date")
    private String date;

    @Column(name = "owner")
    private String owner;

}
