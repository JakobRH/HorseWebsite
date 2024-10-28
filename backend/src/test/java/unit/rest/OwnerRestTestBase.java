package at.ac.tuwien.sepm.assignment.individual.unit.rest;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.endpoint.OwnerEndpoint;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.OwnerMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

public abstract class OwnerRestTestBase {

    @Autowired
    OwnerEndpoint ownerEndpoint;
    @Autowired
    OwnerMapper ownerMapper;

    //Negative test
    @Test
    @DisplayName("Get owner with not existing name. Should throw ResponseStatusException")
    public void getOwnerWithWrongName_ShouldThrowResponseStatusException(){
        assertThrows(ResponseStatusException.class, ()-> ownerEndpoint.getOwner(ownerMapper.entityToDto(TestData.getNewOwner("NotValidName"))));
    }

    //Positive test
    @Test
    @DisplayName("Insert valid owner, Should not throw Exception")
    public void insertValidOwner_ShouldNotThrowException(){
        assertDoesNotThrow(()->ownerEndpoint.insertOwner(ownerMapper.entityToDto(TestData.getNewOwner("ValidOwner"))));
    }
}
