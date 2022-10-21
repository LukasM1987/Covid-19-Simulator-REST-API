package com.covid19simulator.recruitmenttask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialDataDto {

    @JsonIgnoreProperties("id")
    private Long id;

    @JsonIgnoreProperties("simulationName")
    private String simulationName;

    @JsonIgnoreProperties("population")
    private int population;

    @JsonIgnoreProperties("initialNumberOfInfected")
    private int initialNumberOfInfected;

    @JsonIgnoreProperties("indicatorR")
    private double indicatorR;

    @JsonIgnoreProperties("mortality")
    private double mortality;

    @JsonIgnoreProperties("daysToRecovery")
    private int daysToRecovery;

    @JsonIgnoreProperties("daysToMortality")
    private int daysToMortality;

    @JsonIgnoreProperties("simulationDays")
    private int simulationDays;

    @JsonIgnoreProperties("available")
    private boolean available;
}
