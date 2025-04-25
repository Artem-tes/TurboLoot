package ru.web.TurboLoot.backend.models.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
    private Integer typeTransaction;
    private Integer amount;
    private String date;
    private String owner;
}
