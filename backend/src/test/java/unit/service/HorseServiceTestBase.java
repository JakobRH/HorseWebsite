package at.ac.tuwien.sepm.assignment.individual.unit.service;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;

import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class HorseServiceTestBase {

    @Autowired
    HorseService horseService;
    @Autowired
    OwnerService ownerService;

    //Negative test
    @Test
    @DisplayName("Insert horse without values. Should throw Validationsexception")
    public void insertHorse_withoutValues_ShouldThrowValidationexception() {
        assertThrows(ValidationException.class, () ->horseService.insertHorse(TestData.getNewHorse()));
    }

    //Positive test
    @Test
    @DisplayName("Insert horse with valid values. Should throw no Exception")
    public void insertHorse_withValidValues_ShouldThrowNoException() throws ServiceException,ValidationException {
        ownerService.insertOwner(TestData.getNewOwner("TestOwner"));
        Long ownerId = ownerService.getOwner(TestData.getNewOwner("TestOwner")).get(0).getId();
        assertDoesNotThrow(()-> horseService.insertHorse(TestData.getNewHorse("TestHorse", ownerId)), "Did not throw Exception!");
    }

}

