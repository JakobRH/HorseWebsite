package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleHorseService implements HorseService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseDao horseDao;
    private final Validator validator;

    @Autowired
    public SimpleHorseService(HorseDao horseDao, Validator validator) {
        this.horseDao = horseDao;
        this.validator = validator;
    }

    @Override
    public Horse insertHorse(Horse horse) throws ServiceException, ValidationException{

        LOGGER.trace("Validate: ", horse);
        validator.validateNewHorse(horse);

        try {

            LOGGER.trace("insertHorseByValues({})", horse);
            return horseDao.insertHorse(new Horse(horse.getName(), horse.getDescription(), horse.getRating(), horse.getBirthDate(), horse.getRace(), horse.getPhoto(),
                horse.getOwnerId(), LocalDateTime.now(), LocalDateTime.now()));

        }catch (PersistenceException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Horse updateHorse(Horse horse) throws ServiceException, ValidationException {
        LOGGER.trace("Validate: ", horse);
        validator.validateNewHorse(horse);

        try {
            LOGGER.trace("updateHorseByValues({})", horse);
            return horseDao.updateHorse(new Horse(horse.getId(), horse.getName(), horse.getDescription(), horse.getRating(), horse.getBirthDate(), horse.getRace(), horse.getPhoto(),
                horse.getOwnerId(), LocalDateTime.now(), LocalDateTime.now()));

        }catch (PersistenceException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Horse> getHorse(Horse horse) throws ServiceException {

        try {
            LOGGER.trace("getHorseByValues({})", horse);
            List<Horse> list = new LinkedList<Horse>();
            list = horseDao.getHorse(horse);

            for(Horse h : list){
                LOGGER.info( h.toString());
            }
            return list;

        }catch (PersistenceException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteHorse(Long id) throws ServiceException {
        try{
            LOGGER.trace("deleteHorseWithid({})", id);
            horseDao.deleteHorse(id);

        }catch (PersistenceException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public List<Horse> getHorsesOfOwner(Long ownerId) throws ServiceException {
        try {
            LOGGER.trace("getHorseByOwnerId({})", ownerId);
            List<Horse> list = new LinkedList<Horse>();
            list = horseDao.getHorsesOfOwner(ownerId);

            for(Horse h : list){
               LOGGER.info( h.toString());
            }
            return list;

        }catch (PersistenceException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
