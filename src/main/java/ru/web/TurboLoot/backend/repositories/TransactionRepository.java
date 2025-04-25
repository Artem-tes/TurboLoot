package ru.web.TurboLoot.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.web.TurboLoot.backend.models.UserTransaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<UserTransaction,Integer> {

    @Query(value = "select * from transactions_table where id = :id", nativeQuery = true)
    UserTransaction getTransactionById(@Param("id") Integer id);

    @Query(value = "select * from transactions_table where owner = :owner", nativeQuery = true)
    List<UserTransaction> getListTransactionsByOwner(@Param("owner") String owner);
}
