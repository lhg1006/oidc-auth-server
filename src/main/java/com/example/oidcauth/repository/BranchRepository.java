package com.example.oidcauth.repository;

import com.example.oidcauth.entity.Branch;
import com.example.oidcauth.entity.enums.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByBranchId(String branchId);

    List<Branch> findByActiveTrue();

    List<Branch> findByOperationType(OperationType operationType);

    List<Branch> findByRegionInfoContaining(String region);

    @Query("SELECT b FROM Branch b WHERE b.availableTickets > :minTickets")
    List<Branch> findBranchesWithMinimumTickets(@Param("minTickets") Integer minTickets);

    @Query("SELECT b FROM Branch b WHERE b.branchName LIKE %:name% OR b.regionInfo LIKE %:name%")
    List<Branch> searchByNameOrRegion(@Param("name") String name);
}