package com.testing.service;

import com.testing.dto.BookingRequestTO;
import com.testing.dto.ResponseTO;
import com.testing.exception.AlreadyBookedException;
import com.testing.exception.NotFoundException;
import com.testing.model.MobileBookingJournal;
import com.testing.model.Tester;
import com.testing.model.TestingMobile;
import com.testing.repository.TestingMobileRepository;
import com.testing.service.impl.MobileBookingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class MobileBookingServiceTest {

    @Mock
    private TesterService testerService;
    @Mock
    private TestingMobileRepository testingMobileRepository;
    @Mock
    private MobileBookingJournalService mobileBookingJournalService;

    @InjectMocks
    private MobileBookingServiceImpl mobileBookingService;

    @Test
    void testMobileHasAlreadyBeenBooked() {
        //given
        final BookingRequestTO requestTO = BookingRequestTO.builder().mobileId(1L).testerId(1L).build();
        final TestingMobile testingMobile = TestingMobile.builder().id(1L).name("Testing Mobile 1").build();
        final Tester tester = Tester.builder().id(1L).userName("userName1").fullName("Tester 1").build();
        final MobileBookingJournal mobileBookingJournal = MobileBookingJournal.builder().id(1L).testingMobile(testingMobile).tester(tester).build();

        //when
        Mockito.when(testingMobileRepository.findById(requestTO.getMobileId()))
                .thenReturn(Optional.of(testingMobile));
        Mockito.when(mobileBookingJournalService.getLatestBookingForMobile(requestTO.getMobileId()))
                .thenReturn(Optional.of(mobileBookingJournal));

        //then
        assertThatThrownBy(() ->  this.mobileBookingService.bookMobile(requestTO))
                .isInstanceOf(AlreadyBookedException.class)
                .hasMessageContaining("Testing mobile has been booked by Tester 1 already!");
    }

    @Test
    void testMobileHasBeenBookedSuccessfully() {
        //given
        final BookingRequestTO requestTO = BookingRequestTO.builder().mobileId(1L).testerId(1L).build();
        final TestingMobile testingMobile = TestingMobile.builder().id(1L).name("Testing Mobile 1").build();
        final Tester tester = Tester.builder().id(1L).userName("userName1").fullName("Tester 1").build();
        final MobileBookingJournal mobileBookingJournal = MobileBookingJournal.builder().id(1L).testingMobile(testingMobile).tester(tester).build();

        //when
        Mockito.when(testingMobileRepository.findById(requestTO.getMobileId()))
                .thenReturn(Optional.of(testingMobile));
        Mockito.when(testerService.getTesterById(requestTO.getTesterId()))
                .thenReturn(tester);
        Mockito.when(mobileBookingJournalService.saveMobileBooking(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(mobileBookingJournal);
        //then
        ResponseTO<String> stringResponseTO = this.mobileBookingService.bookMobile(requestTO);
        assertThat(stringResponseTO.isSuccess()).isTrue();
        assertThat(stringResponseTO.getData()).isEqualTo("Mobile Testing Mobile 1 has been booked by userName1 successfully");
    }

    @Test
    void testMobileNotFound() {
        //given
        final BookingRequestTO requestDTO = BookingRequestTO.builder().mobileId(1L).testerId(1L).build();
        //when
        Mockito.when(testingMobileRepository.findById(requestDTO.getMobileId()))
                .thenReturn(Optional.empty());
        //then
        Assertions.assertThrows(NotFoundException.class, () -> mobileBookingService.bookMobile(requestDTO));
    }



    @Test
    void testWhenMobileHasBeeSuccessfullyRelease() {
        //given
        final TestingMobile testingMobile = TestingMobile.builder().id(1L).name("Testing Mobile 1").version(0).build();
        final Tester tester = Tester.builder().id(1L).userName("userName1").fullName("Tester 1").build();
        final MobileBookingJournal mobileBookingJournal = MobileBookingJournal.builder().id(1L).testingMobile(testingMobile).tester(tester).build();

        //when
        Mockito.when(mobileBookingJournalService.getLatestBookingForMobile(testingMobile.getId()))
                .thenReturn(Optional.of(mobileBookingJournal));
        //then
        final ResponseTO<String> responseTO = mobileBookingService.returnMobile(testingMobile.getId());
        assertThat(responseTO.getData()).isEqualTo("The mobile 1 has been released successfully from Tester 1 tester");
    }

}
