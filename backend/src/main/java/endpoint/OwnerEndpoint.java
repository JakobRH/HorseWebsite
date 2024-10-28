package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.OwnerMapper;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import java.lang.invoke.MethodHandles;
import java.util.List;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(OwnerEndpoint.BASE_URL)
public class OwnerEndpoint {

    static final String BASE_URL = "/owners";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @Autowired
    public OwnerEndpoint(OwnerService ownerService, OwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    @PostMapping(value = "/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto insertOwner(@RequestBody OwnerDto ownerDto){
        LOGGER.info("Insert owner: ", ownerDto);
        try{
            return ownerMapper.entityToDto(ownerService.insertOwner(ownerMapper.dtoToEntity(ownerDto)));
        }catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error during data processing: " + e.getMessage(), e );
        }catch (ValidationException e) {
            LOGGER.error("Error during validation process: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validation process: " + e.getMessage(), e);
        }
    }

    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public OwnerDto updateOwner(@RequestBody OwnerDto ownerDto){
        LOGGER.info("Update owner: ", ownerDto);
        try{
            return ownerMapper.entityToDto(ownerService.updateOwner(ownerMapper.dtoToEntity(ownerDto)));
        }catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error during data processing: " + e.getMessage(), e );
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading owner - "+ e.getMessage(), e);
        }catch (ValidationException e) {
            LOGGER.error("Error during validation process: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validation process: " + e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OwnerDto deleteOwner(@PathVariable("id") Long id){
        LOGGER.info("DELETE owner with id " + id);
        try{
            ownerService.deleteOwner(id);
            return new OwnerDto("id");
        }catch (NotFoundException e) {
            LOGGER.error("Owner not found!" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading owner - " + e.getMessage(), e);
        }catch (ServiceException e){
            LOGGER.error("Error during data processing: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error during data processing: " + e.getMessage(), e );
        }
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public List<OwnerDto> getOwner(OwnerDto ownerDto) {
        LOGGER.info("GET " + ownerDto.toString());
        try {
            return ownerMapper.entityToDtoList(ownerService.getOwner(ownerMapper.dtoToEntity(ownerDto)));
        } catch (NotFoundException e) {
            LOGGER.error("Owner not found!" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading owner - " + e.getMessage(), e);
        } catch (ServiceException e) {
            LOGGER.error("Error during data processing: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during data processing: " + e.getMessage(), e);
        }
    }
}
