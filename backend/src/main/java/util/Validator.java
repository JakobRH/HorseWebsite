package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Base64;

@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OwnerDao ownerDao;

    @Autowired
    public Validator(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }
    public void validateNewHorse(Horse horse)throws ValidationException{

        LOGGER.debug("Validate horse: {}", horse);

        int rating = horse.getRating();
        String race = horse.getRace() == null ? null : horse.getRace().toUpperCase();

        String[] base64Format = horse.getPhoto()== null ? null : horse.getPhoto().split(",");

        String extension = "wrongFormat";

        if(base64Format !=null) {
            switch (base64Format[0]) {//check image's extension
                case "data:image/jpeg;base64":
                    extension = "jpeg";
                    break;
                case "data:image/png;base64":
                    extension = "png";
                    break;
                default://should write cases for more images types
                    extension = "wrongFormat";
                    break;
            }
        }

        if(rating == 0 || rating >= 6 || rating < 1){
            LOGGER.error("The horse needs a rating and it has to be between 1 and 5!");
            throw new ValidationException("The horse needs a rating and it has to be between 1 and 5!");
        }
        if(horse.getName() == null || horse.getName() ==""){
            LOGGER.error("The horse needs a name!");
            throw new ValidationException("The horse needs a name!");
        }
        if(horse.getBirthDate() == null || horse.getBirthDate() == ""){
            LOGGER.error("The horse needs a date of birth!");
            throw new ValidationException("The horse needs a date of birth!");
        }
        if(race == null || (!race.equals("ARABIAN") && !race.equals("MORGAN") && !race.equals("PAINT") && !race.equals("APPALOOSA"))){
            LOGGER.error("This race is not valid!");
            throw new ValidationException("This race is not valid!");
        }
        if(base64Format == null || extension.equals("wrongFormat")){
            LOGGER.error("This Image is not valid!");
            throw new ValidationException("This Image is not valid!");
        }
        if(horse.getOwnerId() == 0){
            LOGGER.error("Horse needs an owner");
            throw new ValidationException("Horse needs an owner");
        }

        try{
            ownerDao.getOwner(new Owner(horse.getOwnerId(), null));
        }catch (NotFoundException e){
            throw new ValidationException("There is no owner with given id");
        }catch (PersistenceException e){
            throw new ValidationException("Something went wrong during validation");
        }

        LOGGER.info("Successful validation of horse: {}", horse);
    }


    public void validateNewOwner(Owner owner) throws ValidationException {
        LOGGER.debug("Validate owner: {}", owner);
        if(owner.getName() == null){
            LOGGER.error("Owner needs a name");
            throw new ValidationException("Owner needs a name!");
        }
        LOGGER.info("Successful validation of owner: {}", owner);
    }

}
