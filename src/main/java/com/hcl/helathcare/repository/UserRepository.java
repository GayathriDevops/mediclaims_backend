package com.hcl.helathcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.helathcare.entity.Claim;
import com.hcl.helathcare.entity.User;



@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

	Optional<List<Claim>> findAllByUserId(long userId);

}