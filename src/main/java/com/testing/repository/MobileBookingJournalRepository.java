package com.testing.repository;

import com.testing.model.MobileBookingJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MobileBookingJournalRepository extends JpaRepository<MobileBookingJournal, Long> {
    @Query("select mbj from MobileBookingJournal mbj where mbj.testingMobile.id=:testingMobileId "
            + "and mbj.bookingDateTime is not null and mbj.releaseDateTime is null")
    Optional<MobileBookingJournal> findLatestByTestingMobileId(Long testingMobileId);

    @Query("select mbj from MobileBookingJournal mbj where mbj.testingMobile.id=:testingMobileId "
            + "and mbj.bookingDateTime is not null and mbj.releaseDateTime is not null")
    Optional<MobileBookingJournal> findLatestReleasedByTestinMobileId(Long testingMobileId);
}