package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class HorseDto extends BaseDto {

    private String name;
    private String description;
    private int rating;
    private String birthDate;
    private String race;
    private String photo;
    private long ownerId;

    public HorseDto(){
    }

    public HorseDto(String name, String description, int rating, String birthDate, String race, String photo, long ownerId){
        this.name = name;
        this.birthDate = birthDate;
        this.description = description;
        this.rating = rating;
        this.race = race;
        this.photo = photo;
        this.ownerId = ownerId;
    }

    public HorseDto(Long id, String name, String description, int rating, String birthDate, String race, String photo, long ownerId, LocalDateTime created, LocalDateTime updated){
        super(id, created, updated);
        this.name = name;
        this.birthDate = birthDate;
        this.description = description;
        this.rating = rating;
        this.race = race;
        this.photo = photo;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorseDto)) return false;
        if (!super.equals(o)) return false;
        HorseDto horseDto = (HorseDto) o;
        return Objects.equals(name, horseDto.name) && Objects.equals(rating, horseDto.rating)
            && Objects.equals(birthDate, horseDto.birthDate) && Objects.equals(description, horseDto.description) && Objects.equals(race, horseDto.race) && Objects.equals(photo, horseDto.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, rating, birthDate, description, race, photo, ownerId);
    }

    @Override
    protected String fieldsString() {
        return super.fieldsString() + ", name=" + name +  ", rating=" + rating + ", birthDate=" + birthDate +
            ", description=" + description  + ", race=" + race + ", photo=" + photo + ", ownerId=" + ownerId + '\'';
    }

    @Override
    public String toString() {
        return "Horse{ " + fieldsString() +" }";
    }
}
