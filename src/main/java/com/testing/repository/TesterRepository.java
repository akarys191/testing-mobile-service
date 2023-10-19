package com.testing.repository;

import com.testing.model.Tester;
import org.springframework.data.repository.CrudRepository;

public interface TesterRepository extends CrudRepository<Tester, Long> {
}
