package com.example.oidcauth.repository;

import com.example.oidcauth.entity.Branch;
import com.example.oidcauth.entity.Student;
import com.example.oidcauth.entity.enums.AccountType;
import com.example.oidcauth.entity.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByLoginId(String loginId);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.branch WHERE s.loginId = :loginId")
    Optional<Student> findByLoginIdWithBranch(@Param("loginId") String loginId);

    List<Student> findByBranch(Branch branch);

    List<Student> findByBranchAndActiveTrue(Branch branch);

    List<Student> findByAccountType(AccountType accountType);

    List<Student> findBySubject(Subject subject);

    List<Student> findByGradeContaining(String grade);

    @Query("SELECT s FROM Student s WHERE s.branch = :branch AND s.accountType = :accountType")
    List<Student> findByBranchAndAccountType(@Param("branch") Branch branch, @Param("accountType") AccountType accountType);

    @Query("SELECT s FROM Student s WHERE s.studentName LIKE %:name% OR s.parentName LIKE %:name%")
    List<Student> searchByStudentOrParentName(@Param("name") String name);

    @Query("SELECT s FROM Student s WHERE s.points >= :minPoints")
    List<Student> findStudentsWithMinimumPoints(@Param("minPoints") Integer minPoints);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.branch = :branch AND s.active = true")
    Long countActiveStudentsByBranch(@Param("branch") Branch branch);
}