package com.covid19simulator.recruitmenttask.repository;

import com.covid19simulator.recruitmenttask.domain.InitialData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InitialDataRepository extends CrudRepository<InitialData, Long> {
}
