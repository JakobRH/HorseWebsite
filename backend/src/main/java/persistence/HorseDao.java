package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import java.util.List;

public interface HorseDao {

    /**
     * @param horse to get.
     * @return the horses with matching attributes.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException   will be thrown if the owner could not be found in the database.
     */

    List<Horse> getHorse(Horse horse) throws PersistenceException;

    /**
     * @param horse to insert.
     * @return the horse with id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */

    Horse insertHorse(Horse horse) throws PersistenceException;


    /**
     * @param horse to update.
     * @return the horse id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    Horse updateHorse(Horse horse) throws PersistenceException;

    /**
     * @param id of the horse to delete.
     * @throws PersistenceException will be thrown if something is wrong uring data processing.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    void deleteHorse(Long id) throws PersistenceException;

    /**
     * @param ownerId of the horses to get.
     * @return the horses with matching ownerId.
     * @throws PersistenceException will be thrown if something is wrong in the validation process or during data processing.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    List<Horse> getHorsesOfOwner(Long ownerId) throws PersistenceException;


}
