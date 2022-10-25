package com.covid19simulator.recruitmenttask.service;

import com.covid19simulator.recruitmenttask.domain.InitialData;
import com.covid19simulator.recruitmenttask.domain.Simulation;
import com.covid19simulator.recruitmenttask.exceptions.InitialDataException;
import com.covid19simulator.recruitmenttask.exceptions.SimulationException;
import com.covid19simulator.recruitmenttask.repository.InitialDataRepository;
import com.covid19simulator.recruitmenttask.repository.SimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimulationService {

    private static final List<Simulation> simulations = new ArrayList<>();
    private static final List<Integer> infectedPopulations = new ArrayList<>();
    private static final List<Integer> notAlivePopulations = new ArrayList<>();
    private static final List<Integer> susceptiblePopulations = new ArrayList<>();

    private static final int POPULATION_LIMIT = 0;
    private static final int ADD_TWO_DAY = 2;

    private SimulationRepository simulationRepository;

    @Autowired
    public SimulationService(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    public void createSimulation(Simulation simulation) throws InitialDataException, SimulationException {
        int daysToRecovery = 0;
        int daysToDeath = 0;
        if (simulation.getInitialData().isAvailable()) {
            createFirstDayOfSimulation(simulation.getInitialData());
            for (int i = 0; i < simulations.get(0).getInitialData().getSimulationDays() - 1; i++) {
                simulation = new Simulation();
                daysToRecovery++;
                daysToDeath++;
                simulation.setInitialData(simulations.get(0).getInitialData());
                simulation.setSimulationDay(i + ADD_TWO_DAY);
                addDead(simulation, daysToDeath, i);
                addRecovery(simulation, daysToRecovery, i);
                addInfected(simulation, i);
                addSusceptible(simulation, i);
                addRow(simulation, i);
            }
        } else {
            throw new InitialDataException();
        }
        clearAllLists();
    }

    private void clearAllLists() {
        simulations.clear();
        infectedPopulations.clear();
        notAlivePopulations.clear();
    }

    private void addSusceptible(Simulation simulation, int i) {
        simulation.setSusceptiblePopulation((susceptiblePopulations.get(i) - infectedPopulations.get(i + 1)
                - notAlivePopulations.get(i + 1)));
        susceptiblePopulations.add((susceptiblePopulations.get(i) - infectedPopulations.get(i + 1)
                - notAlivePopulations.get(i + 1)));
    }

    private void addInfected(Simulation simulation, int i) {
        simulation.setInfectedPopulation(
                (int) (infectedPopulations.get(i) * simulation.getInitialData().getIndicatorR()));
        infectedPopulations.add(
                (int) (infectedPopulations.get(i) * simulation.getInitialData().getIndicatorR()));
    }

    private void createFirstDayOfSimulation(final InitialData initialData) {
        Simulation simulation = new Simulation();
        simulation.setSimulationDay(1);
        simulation.setInfectedPopulation(initialData.getInitialNumberOfInfected());
        simulation.setSusceptiblePopulation(initialData.getPopulation() - initialData.getInitialNumberOfInfected());
        simulation.setMortalityPopulation(0);
        simulation.setRecoveryPopulation(0);
        simulation.setInitialData(initialData);
        infectedPopulations.add(initialData.getInitialNumberOfInfected());
        susceptiblePopulations.add(initialData.getPopulation() - initialData.getInitialNumberOfInfected());
        notAlivePopulations.add(0);
        simulations.add(simulation);
        simulationRepository.save(simulation);
    }

    public List<Simulation> getAllSimulationByInitialDataId(final Long id) {
        return simulationRepository.findAllByInitialData_Id(id);
    }

    private void addDead(Simulation simulation, int daysToDeath, int i) {
        if (daysToDeath > simulation.getInitialData().getDaysToMortality()) {
            simulation.setMortalityPopulation(
                    (int) (infectedPopulations.get(
                            i - simulation.getInitialData().getDaysToMortality()) * simulation.getInitialData().getMortality()));
            notAlivePopulations.add(
                    (int) (infectedPopulations.get(
                            i - simulation.getInitialData().getDaysToMortality()) * simulation.getInitialData().getMortality()));
        } else {
            simulation.setMortalityPopulation(0);
            notAlivePopulations.add(0);
        }
    }

    private void addRecovery(Simulation simulation, int daysToRecovery, int i) {
        if (daysToRecovery >= simulation.getInitialData().getDaysToRecovery()) {
            simulation.setRecoveryPopulation(infectedPopulations.get(
                    i - simulation.getInitialData().getDaysToRecovery() + 1)
                    - notAlivePopulations.get(i - simulation.getInitialData().getDaysToRecovery()
                    + ADD_TWO_DAY + simulation.getInitialData().getDaysToMortality()));
        } else {
            simulation.setRecoveryPopulation(0);
        }
    }

    private void addRow(Simulation simulation, int i) throws SimulationException {
        if (infectedPopulations.get(i + 1) >= simulations.get(0).getInitialData().getPopulation()
                || susceptiblePopulations.get(i + 1) < POPULATION_LIMIT) {
            clearAllLists();
            throw new SimulationException();
        } else {
            simulationRepository.save(simulation);
        }
    }
}
