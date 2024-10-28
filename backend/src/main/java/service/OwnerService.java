package at.ac.tuwien.sepm.assignment.individual.service;


import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;

import java.util.List;


public interface OwnerService {



    /**
     * @param owner to insert.
     * @return the owner with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the owner could not be found in the system.
     * @thorws ValidationException will be thrown if something is wrong with given values.
     */
    Owner insertOwner(Owner owner) throws ServiceException, ValidationException;

    /**
     * @param owner to update.
     * @return the owner with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the owner could not be found in the system.
     *  @thorws ValidationException will be thrown if something is wrong with given values.
     */
    Owner updateOwner(Owner owner) throws ServiceException, ValidationException;

    /**
     * @param id of the owner to delete.
     * @throws ServiceException will be thrown if something is wrong during data processing.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    void deleteOwner(Long id) throws ServiceException;

    /**
     * @param owner to get.
     * @return the owners with matching attributes.
     * @throws ServiceException will be thrown if something is wrong in the validation process or during data processing.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    List<Owner> getOwner(Owner owner) throws ServiceException;


}
