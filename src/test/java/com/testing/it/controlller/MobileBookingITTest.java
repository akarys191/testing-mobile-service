package com.testing.it.controlller;

import com.testing.controller.TestingMobileController;
import com.testing.dto.BookingRequestTO;
import com.testing.exception.AlreadyBookedException;
import com.testing.it.ParentItTest;
import com.testing.model.MobileBookingJournal;
import com.testing.repository.MobileBookingJournalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(value = "classpath:sql/setup.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:sql/clean.sql", executionPhase = AFTER_TEST_METHOD)
})
class MobileBookingITTest extends ParentItTest {

    @Autowired
    private TestingMobileController testingMobileController;
    @Autowired
    private MobileBookingJournalRepository bookingJournalRepository;


    @Test
    void testBookingMobile() {
        //given
        final BookingRequestTO bookingRequestTO = new BookingRequestTO(1L, 1L);
        //when
        testingMobileController.bookMobile(bookingRequestTO);

        //test
        Optional<MobileBookingJournal> mobileBookingJournalOpt = bookingJournalRepository.findLatestByTestingMobileId(1L);
        assertThat(mobileBookingJournalOpt).isPresent();
        final MobileBookingJournal mobileBookingJournal = mobileBookingJournalOpt.get();

        //assert
        assertThat(mobileBookingJournal.getTester().getId()).isEqualTo(1);
        assertThat(mobileBookingJournal.getBookingDateTime()).isBeforeOrEqualTo(LocalDateTime.now(ZoneOffset.UTC));
        assertThat(mobileBookingJournal.getReleaseDateTime()).isNull();
    }


    @Test
    void testReturnBadClientExceptionWhenBookingMobile() throws Exception {
        //given
        final BookingRequestTO bookingRequestTO = new BookingRequestTO(2L, 2L);

        //assert
        assertThatThrownBy(() ->  this.testingMobileController.bookMobile(bookingRequestTO))
                .isInstanceOf(AlreadyBookedException.class)
                .hasMessageContaining("Testing mobile has been booked by Second Middle Tester already!");
    }

    @Test
    void testReleaseMobile() throws Exception {
        //given
        final Integer deviceId = 1;
        //when
        testingMobileController.releaseMobile(2L);

        //assert
        final Optional<MobileBookingJournal> mobileBookingJournalOpt = bookingJournalRepository.findLatestReleasedByTestinMobileId(2L);
        assertThat(mobileBookingJournalOpt).isPresent();
        assertThat(mobileBookingJournalOpt.get().getReleaseDateTime()).isNotNull();
    }

}
