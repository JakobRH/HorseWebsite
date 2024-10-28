package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.List;
@RestController
@RequestMapping(HorseEndpoint.BASE_URL)
public class HorseEndpoint {

    static final String BASE_URL = "/horses";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseService horseService;
    private final HorseMapper horseMapper;

    @Autowired
    public HorseEndpoint(HorseService horseService, HorseMapper horseMapper){
        this.horseMapper = horseMapper;
        this.horseService = horseService;
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<HorseDto> getHorse(HorseDto horseDto) {
        LOGGER.info("GET " + horseDto.toString());
        try {
            return horseMapper.entityToDtoList(horseService.getHorse(horseMapper.dtoToEntity(horseDto)));
        } catch (NotFoundException e) {
            LOGGER.error("Horse not found!" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse - "+ e.getMessage(), e);
        }catch (ServiceException e){
            LOGGER.error("Error during data processing: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error during data processing: " + e.getMessage(), e );
        }
    }

    @PostMapping(value = "/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto insertHorse(@RequestBody HorseDto horseDto){
        LOGGER.info("POST " + horseDto.toString());
        try{
            return horseMapper.entityToDto(horseService.insertHorse(horseMapper.dtoToEntity(horseDto)));
        }
        catch (ValidationException e) {
            LOGGER.error("Error during validation process: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validation process: " + e.getMessage(), e);
        }
        catch (ServiceException e){
            LOGGER.error("Error during data processing: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during data processing: " + e.getMessage(), e);
        }
    }

    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto updateHorse(@RequestBody HorseDto horseDto){
        LOGGER.info("PUT " + horseDto.toString());
        try{
            return horseMapper.entityToDto(horseService.updateHorse(horseMapper.dtoToEntity(horseDto)));
        }
        catch (NotFoundException e){
            LOGGER.error("Error during updating horse - "+ e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (ValidationException e) {
            LOGGER.error("Error during validation process: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validation process: " + e.getMessage(), e);
        }
        catch (ServiceException e){
            LOGGER.error("Error during data processing: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during data processing: " + e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto deleteHorse(@PathVariable("id") Long id){
        LOGGER.info("DELETE horse with id " + id);
        try{
            horseService.deleteHorse(id);
            return new HorseDto();
        }catch (NotFoundException e) {
            LOGGER.error("Horse not found!" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse - " + e.getMessage(), e);
        }catch (ServiceException e){
            LOGGER.error("Error during data processing: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error during data processing: " + e.getMessage(), e );
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<HorseDto> getHorsesOfOwner(@PathVariable("id") Long ownerId) {
        LOGGER.info("GET horses with ownerID:" + ownerId);
        try {
            return horseMapper.entityToDtoList(horseService.getHorsesOfOwner(ownerId));
        } catch (NotFoundException e) {
            LOGGER.error("Horse not found!" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse - " + e.getMessage(), e);
        } catch (ServiceException e) {
            LOGGER.error("Error during data processing: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during data processing: " + e.getMessage(), e);
        }
    }
}

