package com.hcl.helathcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.helathcare.entity.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
	
	@Query(value="select p.policy_id,p.policy_name,p.policy_amount,u.policy_start_date,u.policy_end_date from policy p,user_policy u where p.policy_id=u.policy_id and u.user_id=?",nativeQuery=true)
	public List<Object[]> getPolicesByUserId(Long userId);

}
