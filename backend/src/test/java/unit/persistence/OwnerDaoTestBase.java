package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public abstract class OwnerDaoTestBase {

    @Autowired
    OwnerDao ownerDao;

    //Negative Test
    @Test
    @DisplayName("Finding owner by non-existing name should throw NotFoundException")
    public void findingOwnerByName_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> ownerDao.getOwner(TestData.getNewOwner("NotAValidName")));
    }

    //Positive Test
    @Test
    @DisplayName("Find owner by existing name should not throw an Exception")
    public void findingOwnerByName_shouldNotThrowException() throws PersistenceException {
        Owner owner = TestData.getNewOwner("ValidName", LocalDateTime.now(), LocalDateTime.now());
        ownerDao.insertOwner(owner);
        assertDoesNotThrow(()-> ownerDao.getOwner(owner), "No Exception thrown");
    }
}
