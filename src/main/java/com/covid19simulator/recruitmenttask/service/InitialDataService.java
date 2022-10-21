package com.covid19simulator.recruitmenttask.service;

import com.covid19simulator.recruitmenttask.domain.InitialData;
import com.covid19simulator.recruitmenttask.exceptions.InitialDataException;
import com.covid19simulator.recruitmenttask.repository.InitialDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitialDataService {

    private final InitialDataRepository initialDataRepository;


    public void addInitialData(final InitialData initialData) {
        initialDataRepository.save(initialData);
    }

    public InitialData getInitialData(final Long id) throws InitialDataException {
        return initialDataRepository.findById(id).orElseThrow(InitialDataException::new);
    }

    public void deleteInitialData(final Long id) throws InitialDataException {
        if (initialDataRepository.findById(id).isPresent()) {
            initialDataRepository.deleteById(id);
        } else {
            throw new InitialDataException();
        }
    }
}
