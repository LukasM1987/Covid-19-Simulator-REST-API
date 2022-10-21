package com.covid19simulator.recruitmenttask.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "INITIAL_DATA")
public class InitialData {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "INITIAL_DATA_ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "SIMULATION_NAME")
    private String simulationName;

    @NotNull
    @Column(name = "POPULATION")
    private int population;

    @NotNull
    @Column(name = "INITIAL_NUMBER_OF_INFECTED")
    private int initialNumberOfInfected;

    @NotNull
    @Column(name = "INDICATOR_R")
    private double indicatorR;

    @NotNull
    @Column(name = "MORTALITY")
    private double mortality;

    @NotNull
    @Column(name = "DAYS_TO_RECOVERY")
    private int daysToRecovery;

    @NotNull
    @Column(name = "DAYS_TO_MORTALITY")
    private int daysToMortality;

    @NotNull
    @Column(name = "SIMULATION_DAYS")
    private int simulationDays;

    @NotNull
    @Column(name = "AVAILABLE")
    private boolean available;

    @OneToMany(targetEntity = Simulation.class, mappedBy = "initialData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Simulation> simulations = new ArrayList<>();

    public InitialData(Long id, String simulationName, int population, int initialNumberOfInfected, double indicatorR, double mortality, int daysToRecovery, int daysToMortality, int simulationDays, boolean available) {
        this.id = id;
        this.simulationName = simulationName;
        this.population = population;
        this.initialNumberOfInfected = initialNumberOfInfected;
        this.indicatorR = indicatorR;
        this.mortality = mortality;
        this.daysToRecovery = daysToRecovery;
        this.daysToMortality = daysToMortality;
        this.simulationDays = simulationDays;
        this.available = available;
    }
}
