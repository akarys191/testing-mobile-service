package com.testing.service.impl;

import com.testing.model.MobileBookingJournal;
import com.testing.exception.NotFoundException;
import com.testing.model.Tester;
import com.testing.model.TestingMobile;
import com.testing.repository.MobileBookingJournalRepository;
import com.testing.service.MobileBookingJournalService;
import com.testing.service.MobileBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MobileBookingJournalServiceImpl implements MobileBookingJournalService {

    private final MobileBookingJournalRepository mobileBookingRepository;

    @Override
    @Transactional
    public Optional<MobileBookingJournal> getLatestBookingForMobile(Long testingMobileId) {
        return mobileBookingRepository.findLatestByTestingMobileId(testingMobileId);
    }

    @Override
    @Transactional
    public MobileBookingJournal saveMobileBooking(TestingMobile testingMobile, Tester tester) {
        MobileBookingJournal mobileBooking =
                MobileBookingJournal.builder()
                        .tester(tester)
                        .testingMobile(testingMobile)
                        .bookingDateTime(LocalDateTime.now(ZoneOffset.UTC))
                        .build();
        return mobileBookingRepository.save(mobileBooking);
    }

    @Override
    public MobileBookingJournal releaseMobile(MobileBookingJournal mobileBookingJournal) {
        mobileBookingJournal.setReleaseDateTime(LocalDateTime.now(ZoneOffset.UTC));
        return mobileBookingRepository.save(mobileBookingJournal);
    }
}
