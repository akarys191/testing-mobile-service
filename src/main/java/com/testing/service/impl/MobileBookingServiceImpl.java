package com.testing.service.impl;

import com.testing.dto.ResponseTO;
import com.testing.exception.AlreadyBookedException;
import com.testing.model.MobileBookingJournal;
import com.testing.model.TestingMobile;
import com.testing.model.Tester;
import com.testing.dto.BookingRequestTO;
import com.testing.exception.NotFoundException;
import com.testing.repository.TestingMobileRepository;
import com.testing.service.MobileBookingService;
import com.testing.service.MobileBookingJournalService;
import com.testing.service.TesterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.persistence.PessimisticLockException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MobileBookingServiceImpl implements MobileBookingService {

    private final TestingMobileRepository testingMobileRepository;
    private final TesterService testerService;
    private final MobileBookingJournalService mobileBookingJournalService;

    @Override
    @Transactional
    public ResponseTO<String> bookMobile(BookingRequestTO requestTO) {
        try {
            final TestingMobile testingMobile = getTestingMobile(requestTO.getTesterId());
            Optional<MobileBookingJournal> latestBookingForMobile =
                    mobileBookingJournalService.getLatestBookingForMobile(testingMobile.getId());

            if(latestBookingForMobile.isPresent()){
                throw new AlreadyBookedException(String.format("Testing mobile has been booked by %s already!",
                                                               latestBookingForMobile.get().getTester().getFullName()));
            }

            Tester tester = testerService.getTesterById(requestTO.getTesterId());
            this.mobileBookingJournalService.saveMobileBooking(testingMobile, tester);
            String formattedBookingMessage =
                    String.format("Mobile %s has been booked by %s successfully", testingMobile.getName(), tester.getUserName());
            log.debug(formattedBookingMessage);
            return ResponseTO.success(formattedBookingMessage);
        } catch (OptimisticLockException | PessimisticLockException ex) {
            MobileBookingJournal latestBookingForMobile = getLatestBookingForMobile(requestTO.getMobileId());
            throw new AlreadyBookedException(String.format("Testing mobile %s has been booked by %s already!",
                                                           requestTO.getMobileId(),
                                                           latestBookingForMobile.getTester().getFullName()));
        }
    }

    private MobileBookingJournal getLatestBookingForMobile(Long testingMobileId) {
        return mobileBookingJournalService.getLatestBookingForMobile(testingMobileId)
                .orElseThrow(() -> new NotFoundException("Active mobile booking has not been found yet for mobile "+ testingMobileId));
    }


    @Override
    @Transactional
    public ResponseTO<String> returnMobile(Long testingMobileId) {
        MobileBookingJournal latestBookingForMobile = getLatestBookingForMobile(testingMobileId);
        this.mobileBookingJournalService.releaseMobile(latestBookingForMobile);
        return ResponseTO.success(String.format("The mobile %s has been released successfully from %s tester", testingMobileId, latestBookingForMobile.getTester().getFullName()));
    }

    private TestingMobile getTestingMobile(Long testingMobileId) {
        return testingMobileRepository.findById(testingMobileId).orElseThrow(() -> new NotFoundException("Testing Mobile Not Found"));
    }
}
