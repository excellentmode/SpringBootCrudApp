package org.example.springbootcrudapp.repository;

import org.example.springbootcrudapp.entity.UserData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
    @EntityGraph(attributePaths = {"emails", "phones", "accountData"})
    Optional<UserData> findByEmailsEmail(String email);

    @EntityGraph(attributePaths = {"emails", "phones", "accountData"})
    Page<UserData> findAll(Specification<UserData> spec, Pageable pageable);
}