package com.testing.service;

import com.testing.dto.ResponseTO;
import com.testing.dto.BookingRequestTO;

public interface MobileBookingService {

    ResponseTO<String> bookMobile(BookingRequestTO requestDTO);
    ResponseTO<String> releaseMobile(Long testingMobileId);
}
