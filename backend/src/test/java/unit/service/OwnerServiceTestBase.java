package at.ac.tuwien.sepm.assignment.individual.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



public abstract class OwnerServiceTestBase {

    @Autowired
    OwnerService ownerService;

    //Negative test
    @Test
    @DisplayName("Insert owner without name. Should throw  ValidationException")
    public void insertOwnerWithoutName_ShouldThrowValidationException(){
        assertThrows(ValidationException.class, ()-> ownerService.insertOwner(TestData.getNewOwner()));
    }

    //Positive test
    @Test
    @DisplayName("Insert and update owner with valid values. Should not throw exception")
    public void insertAndUpdateOwner_ShouldNotThrowException() throws ServiceException,ValidationException {
        ownerService.insertOwner(TestData.getNewOwner("TestOwner"));
        Long id = ownerService.getOwner(TestData.getNewOwner("TestOwner")).get(0).getId();
        assertDoesNotThrow(()-> ownerService.updateOwner(TestData.getNewOwnerWithId(id, "NewTestOwner")), "No Exception thrown!");
    }
}
