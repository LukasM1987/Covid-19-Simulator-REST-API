package com.covid19simulator.recruitmenttask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationsDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("simulationDay")
    private int simulationDay;

    @JsonProperty("infectedPopulation")
    private int infectedPopulation;

    @JsonProperty("susceptiblePopulation")
    private int susceptiblePopulation;

    @JsonProperty("mortalityPopulation")
    private int mortalityPopulation;

    @JsonProperty("recoveryPopulation")
    private int recoveryPopulation;

    @JsonProperty("initialDataId")
    private Long initialDataId;
}
