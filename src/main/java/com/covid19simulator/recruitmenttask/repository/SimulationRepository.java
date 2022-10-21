package com.covid19simulator.recruitmenttask.repository;

import com.covid19simulator.recruitmenttask.domain.Simulation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SimulationRepository extends CrudRepository<Simulation, Long> {

    List<Simulation> findAllByInitialData_Id(final Long id);
}
