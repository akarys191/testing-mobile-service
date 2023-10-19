package com.testing.service.impl;

import com.testing.model.Tester;
import com.testing.exception.NotFoundException;
import com.testing.repository.TesterRepository;
import com.testing.service.TesterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TesterServiceImpl implements TesterService {

    private final TesterRepository testerRepository;

    @Override
    @Transactional
    public Tester getTesterById(Long testerId) {
        return testerRepository.findById(testerId)
                .orElseThrow(() -> new NotFoundException("Tester Mobile Not Found!"));
    }
}
