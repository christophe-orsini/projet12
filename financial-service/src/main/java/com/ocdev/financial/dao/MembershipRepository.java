package com.ocdev.financial.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ocdev.financial.entities.Membership;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long>
{
	@Query(value = "SELECT * FROM membership m WHERE member_id = ?1 ORDER BY validity_date DESC LIMIT 1", nativeQuery = true)
	public Optional<Membership> findLastMembershipByMemberId(long memberId);
}
