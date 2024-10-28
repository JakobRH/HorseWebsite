package at.ac.tuwien.sepm.assignment.individual.unit.rest;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.endpoint.HorseEndpoint;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

public abstract class HorseRestTestBase {

    @Autowired
    HorseEndpoint horseEndpoint;
    @Autowired
    HorseMapper horseMapper;

    //Negative test
    @Test
    @DisplayName("Insert horse without values. Should throw ResponseStatusException")
    public void insertHorseWithoutValues_ShouldThrowResponseStatusException(){
        assertThrows(ResponseStatusException.class, ()-> horseEndpoint.insertHorse(horseMapper.entityToDto(TestData.getNewHorse())));
    }

    @Test
    @DisplayName("Insert horse with ownerId that doesnt exist. Should throw ResponseStatusException")
    public void insertHorseWithWrongOwnerId_ShouldThrowResponseStatusException() {
      assertThrows(ResponseStatusException.class, ()-> horseEndpoint.insertHorse(horseMapper.entityToDto(TestData.getNewHorse("Testhorse",0L))));
    }
}
