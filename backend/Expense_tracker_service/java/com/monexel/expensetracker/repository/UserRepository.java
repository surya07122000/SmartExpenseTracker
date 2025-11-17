package com.monexel.expensetracker.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monexel.expensetracker.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
	Optional<User> findByName(String userName);
	boolean existsByName(String userName);
	Object findUsersByEmail(String username);

	


}
