package com.hcl.helathcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.helathcare.entity.UserPolicy;
@Repository
public interface UserPolicyRepository extends JpaRepository<UserPolicy, Long> {

	Optional<UserPolicy> findByPolicyIdAndUserId(Long policyId, Long userId);

	Optional<UserPolicy> findByPolicyId(Long policyId);

}
