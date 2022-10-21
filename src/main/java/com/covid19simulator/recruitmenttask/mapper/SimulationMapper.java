package com.covid19simulator.recruitmenttask.mapper;

import com.covid19simulator.recruitmenttask.domain.Simulation;
import com.covid19simulator.recruitmenttask.dto.SimulationsDto;
import com.covid19simulator.recruitmenttask.exceptions.InitialDataException;
import com.covid19simulator.recruitmenttask.service.InitialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimulationMapper {

    @Autowired
    private InitialDataService initialDataService;

    public Simulation mapToSimulation(final SimulationsDto simulationsDto) throws InitialDataException {
        Simulation simulation = new Simulation(simulationsDto.getId(),
                simulationsDto.getSimulationDay(),
                simulationsDto.getInfectedPopulation(),
                simulationsDto.getSusceptiblePopulation(),
                simulationsDto.getMortalityPopulation(),
                simulationsDto.getRecoveryPopulation());
        simulation.setInitialData(initialDataService.getInitialData(simulationsDto.getInitialDataId()));
        return simulation;
    }

    public SimulationsDto matoToSimulationsDto(final Simulation simulation) {
        SimulationsDto simulationsDto = new SimulationsDto(simulation.getId(),
                simulation.getSimulationDay(),
                simulation.getInfectedPopulation(),
                simulation.getSusceptiblePopulation(),
                simulation.getMortalityPopulation(),
                simulation.getRecoveryPopulation(),
                simulation.getInitialData().getId());
        return simulationsDto;
    }

    public List<SimulationsDto> mapToSimulationsDtoList(final List<Simulation> simulations) {
        return simulations.stream().map(this::matoToSimulationsDto).collect(Collectors.toList());
        //return simulations.stream().map(this::matoToSimulationsDto).filter(i -> i.getInitialDataId().equals(id)).collect(Collectors.toList());
    }
}
