package com.covid19simulator.recruitmenttask.conotroller;

import com.covid19simulator.recruitmenttask.domain.InitialData;
import com.covid19simulator.recruitmenttask.dto.InitialDataDto;
import com.covid19simulator.recruitmenttask.exceptions.InitialDataException;
import com.covid19simulator.recruitmenttask.mapper.InitialDataMapper;
import com.covid19simulator.recruitmenttask.service.InitialDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/initialData")
@RequiredArgsConstructor
public class InitialDataController {

    private final InitialDataMapper initialDataMapper;
    private final InitialDataService initialDataService;

    @RequestMapping(method = RequestMethod.POST, value = "addInitialData", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addInitialData(@RequestBody final InitialDataDto initialDataDto) {
        InitialData initialData = initialDataMapper.mapToInitialData(initialDataDto);
        initialDataService.addInitialData(initialData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getInitialData")
    public InitialDataDto getInitialData(@RequestParam final Long id) throws InitialDataException {
        return initialDataMapper.mapToInitialDataDto(initialDataService.getInitialData(id));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteInitialData")
    public void deleteInitialData(@RequestParam final Long id) throws InitialDataException {
        initialDataService.deleteInitialData(id);
    }
}
