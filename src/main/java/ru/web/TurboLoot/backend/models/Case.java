package ru.web.TurboLoot.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "case_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_case")
    private String nameCase;

    @JsonProperty("items")
    @Column(name = "items")
    private List<Integer> weapons;

    @Column(name = "count_items")
    private Integer countItems;

    @Column(name = "price")
    private Integer price;

    @Column(name = "rarity")
    private String rarity;

}
