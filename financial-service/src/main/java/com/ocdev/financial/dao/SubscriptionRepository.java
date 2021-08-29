package com.ocdev.financial.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ocdev.financial.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>
{
	@Query(value = "SELECT * FROM subscription WHERE member_id = ?1 ORDER BY validity_date DESC LIMIT 1", nativeQuery = true)
	public Optional<Subscription> findLastSubscriptionByMemberId(String memberId);
}
