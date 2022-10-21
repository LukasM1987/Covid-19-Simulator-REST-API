package com.covid19simulator.recruitmenttask.service;

import com.covid19simulator.recruitmenttask.domain.InitialData;
import com.covid19simulator.recruitmenttask.domain.Simulation;
import com.covid19simulator.recruitmenttask.exceptions.InitialDataException;
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
    private static final List<Double> lista_liczby_zarażonych_z_każdego_dnia = new ArrayList<>();
    private static final List<Double> lista_liczby_zmarłych_z_każdego_dnia = new ArrayList<>();
    private static final List<Double> lista_Liczby_ozdrowiałych_z_każdego_dnia = new ArrayList<>();

    private InitialDataRepository initialDataRepository;
    private SimulationRepository simulationRepository;

    @Autowired
    public SimulationService(InitialDataRepository initialDataRepository, SimulationRepository simulationRepository) {
        this.initialDataRepository = initialDataRepository;
        this.simulationRepository = simulationRepository;
    }

    private void createFirstDayOfSimulation(final InitialData initialData) {
        Simulation simulation = new Simulation();
        simulation.setSimulationDay(1);  //POZNIEJ USUNAC TA LINIJKE!!
        simulation.setInfectedPopulation(initialData.getInitialNumberOfInfected());
        simulation.setSusceptiblePopulation(initialData.getPopulation() - initialData.getInitialNumberOfInfected());
        simulation.setMortalityPopulation(0);
        simulation.setRecoveryPopulation(0);
        simulation.setInitialData(initialData);
        lista_liczby_zarażonych_z_każdego_dnia.add((double) initialData.getInitialNumberOfInfected());
        lista_liczby_zmarłych_z_każdego_dnia.add(0.0);
        lista_Liczby_ozdrowiałych_z_każdego_dnia.add(0.0);
        simulations.add(simulation);
        simulationRepository.save(simulation);
    }

    public void createSimulation(Simulation simulation) throws InitialDataException {
        int dni_do_wyzdrowienia = 0;
        int dni_do_śmierci = 0;
        if (simulation.getInitialData().isAvailable()) {
            createFirstDayOfSimulation(simulation.getInitialData());

            for (int i = 0; i < simulations.get(0).getInitialData().getSimulationDays() - 1; i++) {
                int populacja = (int) (simulations.get(0).getInitialData().getPopulation()
                                        - lista_liczby_zmarłych_z_każdego_dnia.get(i) - lista_Liczby_ozdrowiałych_z_każdego_dnia.get(i));
                simulation = new Simulation();
                dni_do_wyzdrowienia++;
                dni_do_śmierci++;
                simulation.setInitialData(simulations.get(0).getInitialData());
                simulation.setSimulationDay(i + 2); //POZNIEJ USUNAC TA LINIJKE!!

                if (dni_do_śmierci > simulation.getInitialData().getDaysToMortality()) {

                    /*
                    simulation.setMortalityPopulation(lista_liczby_zmarłych_z_każdego_dnia.get(i)
                            + simulation.getInitialData().getMortality());
                    lista_liczby_zmarłych_z_każdego_dnia.add(lista_liczby_zmarłych_z_każdego_dnia.get(i)
                            + simulation.getInitialData().getMortality());
                    */

                    simulation.setMortalityPopulation(0);
                    lista_liczby_zmarłych_z_każdego_dnia.add(0.0);
                } else {
                    simulation.setMortalityPopulation(0);
                    lista_liczby_zmarłych_z_każdego_dnia.add(0.0);
                }

                //----------------------------------------------------------------------------------------------------//

                if (dni_do_wyzdrowienia >= simulation.getInitialData().getDaysToRecovery()) {





                    simulation.setInfectedPopulation(
                            (int) (lista_liczby_zarażonych_z_każdego_dnia.get(i) * simulation.getInitialData().getIndicatorR()));
                    lista_liczby_zarażonych_z_każdego_dnia.add(
                            lista_liczby_zarażonych_z_każdego_dnia.get(i) * simulation.getInitialData().getIndicatorR());
                    simulation.setSusceptiblePopulation(0);
                    simulation.setRecoveryPopulation(0);

                    lista_Liczby_ozdrowiałych_z_każdego_dnia.add(
                            lista_liczby_zarażonych_z_każdego_dnia.get(i - simulation.getInitialData().getDaysToRecovery() + 1));

                } else {

                    simulation.setInfectedPopulation(
                            (int) (lista_liczby_zarażonych_z_każdego_dnia.get(i) * simulation.getInitialData().getIndicatorR()));
                    lista_liczby_zarażonych_z_każdego_dnia.add(
                            lista_liczby_zarażonych_z_każdego_dnia.get(i) * simulation.getInitialData().getIndicatorR());
                    simulation.setSusceptiblePopulation(
                            (int) (populacja - lista_liczby_zarażonych_z_każdego_dnia.get(i + 1)));
                    simulation.setRecoveryPopulation(0);
                    lista_Liczby_ozdrowiałych_z_każdego_dnia.add(0.0);
                }
                simulationRepository.save(simulation);
            }
        } else {
            throw new InitialDataException();
        }
        simulations.clear();
        lista_Liczby_ozdrowiałych_z_każdego_dnia.clear();
        lista_liczby_zarażonych_z_każdego_dnia.clear();
        lista_liczby_zmarłych_z_każdego_dnia.clear();
    }

    public List<Simulation> getAllSimulationByInitialDataId(final Long id) {
        return simulationRepository.findAllByInitialData_Id(id);
    }
}
