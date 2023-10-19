package com.testing.repository;

import com.testing.model.TestingMobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface TestingMobileRepository extends JpaRepository<TestingMobile, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<TestingMobile> findById(Long testingMobileId);
}
