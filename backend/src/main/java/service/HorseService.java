package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;

import java.util.List;

public interface HorseService {

    /**
     * @param horse to insert.
     * @return the horse with id.
     * @throws ServiceException will be thrown if something is wrong in the validation process or during data processing.
     * @throws ValidationException will be thrown if something is wrong with given values.
     */

    Horse insertHorse(Horse horse) throws ServiceException, ValidationException;

    /**
     * @param horse to update.
     * @return the horse with id.
     * @throws ServiceException will be thrown if something is wrong in the validation process or during data processing.
     *  @throws ValidationException will be thrown if something is wrong with given values.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    Horse updateHorse(Horse horse) throws ServiceException, ValidationException;;


    /**
     * @param horse to get.
     * @return the horses with matching attributes.
     * @throws ServiceException will be thrown if something is wrong in the validation process or during data processing.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    List<Horse> getHorse(Horse horse) throws ServiceException;

    /**
     * @param ownerId of the horses to get.
     * @return the horses with matching ownerId.
     * @throws ServiceException will be thrown if something is wrong in the validation process or during data processing.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    List<Horse> getHorsesOfOwner(Long ownerId) throws ServiceException;


    /**
     * @param id of the horse to delete.
     * @throws ServiceException will be thrown if something is wrong during data processing.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    void deleteHorse(Long id) throws ServiceException;
}
