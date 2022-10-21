package com.covid19simulator.recruitmenttask.mapper;

import com.covid19simulator.recruitmenttask.domain.InitialData;
import com.covid19simulator.recruitmenttask.dto.InitialDataDto;
import org.springframework.stereotype.Service;

@Service
public class InitialDataMapper {

    public InitialData mapToInitialData(final InitialDataDto initialDataDto) {
        return new InitialData(initialDataDto.getId(),
                initialDataDto.getSimulationName(),
                initialDataDto.getPopulation(),
                initialDataDto.getInitialNumberOfInfected(),
                initialDataDto.getIndicatorR(),
                initialDataDto.getMortality(),
                initialDataDto.getDaysToRecovery(),
                initialDataDto.getDaysToMortality(),
                initialDataDto.getSimulationDays(),
                initialDataDto.isAvailable());
    }

    public InitialDataDto mapToInitialDataDto(final InitialData initialData) {
        return new InitialDataDto(initialData.getId(),
                initialData.getSimulationName(),
                initialData.getPopulation(),
                initialData.getInitialNumberOfInfected(),
                initialData.getIndicatorR(),
                initialData.getMortality(),
                initialData.getDaysToRecovery(),
                initialData.getDaysToMortality(),
                initialData.getSimulationDays(),
                initialData.isAvailable());
    }
}
