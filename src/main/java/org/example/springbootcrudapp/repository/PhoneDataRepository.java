package org.example.springbootcrudapp.repository;

import org.example.springbootcrudapp.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    public Optional<PhoneData> findByPhone(String phone);

    boolean existsByPhone(String phone);

    long countByUserDataId(Long userId);
}