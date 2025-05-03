package ru.web.TurboLoot.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "upgrade_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Upgrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idUpgrade;

    @Column(name = "id_item")
    private Integer idItem;

    @Column(name = "id_item_upgrade")
    private Integer idItemUpgrade;

    @Column(name = "move")
    private Integer move;

    @Column(name = "id_owner")
    private Integer idOwner;
}
