package at.ac.tuwien.sepm.assignment.individual.base;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.time.LocalDateTime;
import java.util.Date;

public interface TestData {

    /**
     * URI Data
     */
    String BASE_URL = "http://localhost:";
    String HORSE_URL = "/horses";
    String OWNER_URL = "/owners";

    /**
     * Owner Data
     */
    static Owner getNewOwner() {
        return new Owner();
    }

    static Owner getNewOwner(String name) {
        return new Owner(name);
    }
    static Owner getNewOwner(String name, LocalDateTime crated, LocalDateTime updated) {
        return new Owner(name, crated, updated);
    }
    static Owner getNewOwnerWithId(Long id, String name) {
        return new Owner(id, name );
    }

    /**
     * Horse Data
     */

    String validPhotoBase64 =  "data:image/jpeg;base64,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";

    static Horse getNewHorse(){return new Horse();}

    static Horse getNewHorse(String name){return new Horse(name);}

    static Horse getNewHorse(String name, Long ownerId){return new Horse(name,"description", 1, "2020-10-10", "MORGAN", validPhotoBase64,ownerId, LocalDateTime.now(), LocalDateTime.now() );}


}
