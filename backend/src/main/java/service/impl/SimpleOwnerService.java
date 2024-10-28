package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleOwnerService implements OwnerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OwnerDao ownerDao;
    private final Validator validator;

    @Autowired
    public SimpleOwnerService(OwnerDao ownerDao, Validator validator) {
        this.ownerDao = ownerDao;
        this.validator = validator;
    }

    @Override
    public Owner insertOwner(Owner owner) throws ServiceException, ValidationException {

        LOGGER.trace("Validate: ", owner);
        validator.validateNewOwner(owner);

        try {

            LOGGER.trace("insertOwnerByValues({}) in service layer", owner);
            return ownerDao.insertOwner(new Owner(owner.getName(), LocalDateTime.now(), LocalDateTime.now()));

        }catch (PersistenceException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Owner updateOwner(Owner owner) throws ServiceException, ValidationException {

        LOGGER.trace("Validate: ", owner);
        validator.validateNewOwner(owner);

        try {
            LOGGER.trace("updateOwnerByValues({}) in service layer", owner);
            return ownerDao.updateOwner(new Owner(owner.getId(),owner.getName(), LocalDateTime.now(), LocalDateTime.now()));

        }catch (PersistenceException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteOwner(Long id) throws ServiceException {
        try{
            LOGGER.trace("deleteOwnerWithid({}) in service layer", id);
            ownerDao.deleteOwner(id);

        }catch (PersistenceException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Owner> getOwner(Owner owner) throws ServiceException {
        try {
            LOGGER.trace("getHorseByValues({}) in service layer", owner);
            return ownerDao.getOwner(owner);

        }catch (PersistenceException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
