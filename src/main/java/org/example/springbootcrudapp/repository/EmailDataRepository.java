package org.example.springbootcrudapp.repository;

import org.example.springbootcrudapp.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
    Optional<EmailData> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByUserDataId(Long userId);
}