package ru.web.TurboLoot.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String username;
    private Integer balance;
    private List<Integer> inventory;
    private Integer countCases;
    private Integer countInventory;
    private List<Integer> transactional;
}
