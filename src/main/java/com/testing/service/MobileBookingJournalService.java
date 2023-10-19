package com.testing.service;

import com.testing.model.MobileBookingJournal;
import com.testing.model.Tester;
import com.testing.model.TestingMobile;

import java.util.Optional;

public interface MobileBookingJournalService {
    Optional<MobileBookingJournal> getLatestBookingForMobile(Long testingMobileId);
    MobileBookingJournal saveMobileBooking(TestingMobile testingMobile, Tester tester);
    MobileBookingJournal releaseMobile(MobileBookingJournal mobileBookingJournal);
}
