package at.ac.tuwien.sepm.assignment.individual.unit.persistence;
import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class HorseDaoTestBase {

    @Autowired
    HorseDao horseDao;
    @Autowired
    OwnerDao ownerDao;


    //Negative Test
    @Test
    @DisplayName("Finding horse by non-existing name should throw NotFoundException")
    public void findingOwnerByHorse_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> horseDao.getHorse(TestData.getNewHorse("NotAValidName")));
    }

    //Positive Test
    @Test
    @DisplayName("Find horse by existing name should not throw an Exception")
    public void findingHorseByName_shouldNotThrowException() throws PersistenceException {
        ownerDao.insertOwner(TestData.getNewOwner("ValidName", LocalDateTime.now(), LocalDateTime.now()));
        Long ownerId = ownerDao.getOwner(TestData.getNewOwner("ValidName")).get(0).getId();
        horseDao.insertHorse(TestData.getNewHorse("TestHorse", ownerId));

        assertDoesNotThrow(()-> horseDao.getHorse(TestData.getNewHorse("TestHorse")), "No Exception thrown");
    }


}
