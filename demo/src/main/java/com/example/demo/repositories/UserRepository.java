package com.example.demo.repositories;

import com.example.demo.entity.User;
import jdk.dynalink.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

// CrudRepository - Інтерфейс для спільних операцій CRUD з репозиторієм певного типу.
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("Select u FROM User u WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Query("Select u FROM User u WHERE u.id = :id")
    User findUserById(@Param("id") Integer id);

    @Query(
            value = "SELECT user_id, username, password, enabled, email FROM (SELECT @n:=@n+1 as rownum, u.* FROM users u) as t, (SELECT @n := 0)n;",
            nativeQuery = true)
    List<User> findUserByNumberToNumber(@Param("from") int from, @Param("to") int to, @Param("amount") int amount);

    List<User> findUsersByUsername(String username);

    boolean existsUserByUsername(String username);
}
