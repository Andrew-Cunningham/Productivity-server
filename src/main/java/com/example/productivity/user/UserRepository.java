package com.example.productivity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
//    //@Query(value = "SELECT * FROM Users t WHERE t.email = email", nativeQuery = true)
//    User findByEmail(String email);
//
//    // @Query(value = "SElECT * from Users")
//
//
//    @Query("SELECT t.email FROM Users t where t.email = email")
//    String getEmail( User searchUser);
//
//    @Query("SELECT t.password FROM Users t where t.password = password")
//    String getPassword(User searchUser);
}