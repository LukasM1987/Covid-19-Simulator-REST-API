package com.covid19simulator.recruitmenttask.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SIMULATIONS")
public class Simulation {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "SIMULATION_ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "SIMULATION_DAY")
    private int simulationDay;

    @NotNull
    @Column(name = "INFECTED_POPULATION")
    private int infectedPopulation;

    @NotNull
    @Column(name = "SUSCEPTIBLE_POPULATION")
    private int susceptiblePopulation;

    @NotNull
    @Column(name = "MORTALITY_POPULATION")
    private int mortalityPopulation;

    @NotNull
    @Column(name = "RECOVERY_POPULATION")
    private int recoveryPopulation;

    @ManyToOne
    @JoinColumn(name = "INITIAL_DATA_ID")
    private InitialData initialData;

    public Simulation(Long id, int simulationDay, int infectedPopulation, int susceptiblePopulation, int mortalityPopulation, int recoveryPopulation) {
        this.id = id;
        this.simulationDay = simulationDay;
        this.infectedPopulation = infectedPopulation;
        this.susceptiblePopulation = susceptiblePopulation;
        this.mortalityPopulation = mortalityPopulation;
        this.recoveryPopulation = recoveryPopulation;
    }
}
