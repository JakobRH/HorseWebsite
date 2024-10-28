package at.ac.tuwien.sepm.assignment.individual.persistence;


import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;


import java.util.List;

public interface OwnerDao {

    /**
     * @param owner to insert.
     * @return the owner with the specified id.
     * @throws PersistenceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the owner could not be found in the system.
     */
    Owner insertOwner(Owner owner) throws PersistenceException;

    /**
     * @param owner to update.
     * @return the owner with the specified id.
     * @throws PersistenceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the owner could not be found in the system.
     */
    Owner updateOwner(Owner owner) throws PersistenceException;

    /**
     * @param id of the owner to delete.
     * @throws PersistenceException will be thrown if something is wrong during data processing.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    void deleteOwner(Long id) throws PersistenceException;

    /**
     * @param owner to get.
     * @return the owners with matching attributes.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException   will be thrown if the owner could not be found in the database.
     */

    List<Owner> getOwner(Owner owner) throws PersistenceException;


}
