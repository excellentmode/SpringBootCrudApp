package org.example.springbootcrudapp.repository;

import org.example.springbootcrudapp.entity.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountData, Long> {
    Optional<AccountData> findByUserDataId(Long userId);
}