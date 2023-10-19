package com.testing.controller;

import com.testing.dto.BookingRequestTO;
import com.testing.dto.ResponseTO;
import com.testing.service.MobileBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/testing/mobile")
public class TestingMobileController {

    private final MobileBookingService testMobileService;

    @PostMapping
    public ResponseTO<String> bookMobile(@RequestBody final BookingRequestTO request) {
        return testMobileService.bookMobile(request);
    }

    @PostMapping("/{testingMobileId}/release")
    public ResponseTO<String> releaseMobile(@PathVariable final Long testingMobileId) {
        return testMobileService.releaseMobile(testingMobileId);
    }
}
