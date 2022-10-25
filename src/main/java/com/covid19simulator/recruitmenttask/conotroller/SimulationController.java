package com.covid19simulator.recruitmenttask.conotroller;

import com.covid19simulator.recruitmenttask.domain.Simulation;
import com.covid19simulator.recruitmenttask.dto.SimulationsDto;
import com.covid19simulator.recruitmenttask.exceptions.InitialDataException;
import com.covid19simulator.recruitmenttask.exceptions.SimulationException;
import com.covid19simulator.recruitmenttask.mapper.SimulationMapper;
import com.covid19simulator.recruitmenttask.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/simulations")
@RequiredArgsConstructor
public class SimulationController {

    private final SimulationService simulationService;
    private final SimulationMapper simulationMapper;

    @RequestMapping(method = RequestMethod.POST, value = "createSimulation")
    public void createSimulation(@RequestBody final SimulationsDto simulationsDto) throws InitialDataException, SimulationException {
        Simulation simulation = simulationMapper.mapToSimulation(simulationsDto);
        simulationService.createSimulation(simulation);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getSimulationByInitialDataId")
    public List<SimulationsDto> getSimulationByInitialDataId(@RequestParam final Long id) {
        List<Simulation> simulations = simulationService.getAllSimulationByInitialDataId(id);
        return simulationMapper.mapToSimulationsDtoList(simulations);
    }
}
