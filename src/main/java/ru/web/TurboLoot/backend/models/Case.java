package ru.web.TurboLoot.backend.models;

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

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    @Column(name = "items")
    private Integer[] weapons;

    @Column(name = "count_items")
    private Integer countItems;

    @Column(name = "price")
    private Integer price;

    @Column(name = "rarity")
    private String rarity;

}
