package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import java.lang.invoke.MethodHandles;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME = "Horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public HorseJdbcDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Horse> getHorse(Horse horse) throws PersistenceException{

        List<Horse> horses = new ArrayList<Horse>();

        try{
        if(horse.getName() != null){

            String name = horse.getName();
            final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE UPPER(name) LIKE UPPER(?) ";

            LOGGER.trace("Trying to get horse with name like {}", name);
            LOGGER.debug("Trying to execute SQL query: {}", sql);

            List<Horse> horsesTmp = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, "%" + name + "%");
                return stmt;
            }, this::mapRow);



            if(!horsesTmp.isEmpty()) {
                LOGGER.info("Successfully read horses with name like {}", horse.getName());
                if (horses.isEmpty()){
                    horses = new ArrayList<>(horsesTmp);
                }else{
                   horses = horses.stream().filter(horsesTmp::contains).collect(Collectors.toList());
                }
            }
        }
         if(horse.getDescription() != null){

            String description = horse.getDescription();
            final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE UPPER(description) LIKE UPPER(?) ";

            LOGGER.trace("Trying to get horse with description like {}", description);
            LOGGER.debug("Trying to execute SQL query: {}", sql);

            List<Horse> horsesTmp = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, "%" + description+ "%");
                return stmt;
            }, this::mapRow);



             if(!horsesTmp.isEmpty()) {
                 LOGGER.info("Successfully read horses with description like {}", description);
                 if (horses.isEmpty()){
                     horses = new ArrayList<>(horsesTmp);
                 }else{
                     horses = horses.stream().filter(horsesTmp::contains).collect(Collectors.toList());
                 }
             }
        }
         if(horse.getRating() != 0){

            int rating = horse.getRating();
            final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE rating=?";

            LOGGER.trace("Trying to get horse with rating like {}", rating);
            LOGGER.debug("Trying to execute SQL query: {}", sql);

            List<Horse> horsesTmp = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, rating);
                return stmt;
            }, this::mapRow);



             if(!horsesTmp.isEmpty()) {
                 LOGGER.info("Successfully read horses with rating like {}", rating);
                 if (horses.isEmpty()){
                     horses = new ArrayList<>(horsesTmp);
                 }else{
                     horses = horses.stream().filter(horsesTmp::contains).collect(Collectors.toList());
                 }
             }
        }
         if(horse.getRace() != null){

            String race = horse.getRace();
            final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE UPPER(race)= UPPER(?)";

            LOGGER.trace("Trying to get horse with race like {}", race);
            LOGGER.debug("Trying to execute SQL query: {}", sql);

            List<Horse> horsesTmp = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, race);
                return stmt;
            }, this::mapRow);


             if(!horsesTmp.isEmpty()) {
                 LOGGER.info("Successfully read horses with race like {}", race);
                 if (horses.isEmpty()){
                     horses = new ArrayList<>(horsesTmp);
                 }else{
                     horses = horses.stream().filter(horsesTmp::contains).collect(Collectors.toList());
                 }
             }
        }
         if(horse.getBirthDate() != null){

            String date = horse.getBirthDate();
            final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE birth_date<=?";

            LOGGER.trace("Trying to get horse with date like {}", date);
            LOGGER.debug("Trying to execute SQL query: {}", sql);

            List<Horse> horsesTmp = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, date);
                return stmt;
            }, this::mapRow);


             if(!horsesTmp.isEmpty()) {
                 LOGGER.info("Successfully read horses with date like {}", date);
                 if (horses.isEmpty()){
                     horses = new ArrayList<>(horsesTmp);
                 }else{
                     horses = horses.stream().filter(horsesTmp::contains).collect(Collectors.toList());
                 }
             }
        }

        if(horses.isEmpty()) {
            LOGGER.error("No horse found with given values");
            throw new NotFoundException("No horse found with given values");
        }else return horses;

        }catch(DataAccessException e){
            LOGGER.error("Erroc occured during database access" + horse.toString() + "\n" + e.getMessage());
            throw new PersistenceException("Erroc occured during database access" + horse.toString() + "\n" + e.getMessage());
        }
    }

    @Override
    public Horse insertHorse(Horse horse)throws PersistenceException{

        final String sql = "INSERT INTO " + TABLE_NAME +
            " (name, description, rating, birth_date, race, photo, owner_id, created_at, updated_at) VALUES (?,?,?,?,?,?,?,?,?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        LOGGER.trace("Trying to insert horse {}", horse);
        LOGGER.debug("Trying to execute SQL query: {}", sql);

        try{
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName());
                stmt.setString(2, horse.getDescription());
                stmt.setInt(3, horse.getRating());
                stmt.setString(4, horse.getBirthDate());
                stmt.setString(5, horse.getRace());
                stmt.setString(6, horse.getPhoto());
                stmt.setLong(7, horse.getOwnerId());
                stmt.setObject(8, horse.getCreatedAt());
                stmt.setObject(9, horse.getUpdatedAt());
                return stmt;
            }, keyHolder);

            horse.setId(((Number) keyHolder.getKeys().get("id")).longValue());
            LOGGER.info("Successfully inserted horse " + horse.toString());
            return horse;

        } catch (DataAccessException e){
            LOGGER.error("Error occured while trying to insert horse" + horse.toString() + "\n" + e.getMessage());
            throw new PersistenceException("Error occured while trying to insert horse " + horse.toString()+ "\n" + e.getMessage());
        }

    }

    @Override
    public Horse updateHorse(Horse horse) throws PersistenceException {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "UPDATE " + TABLE_NAME +
            " SET name = ?, description = ?, rating = ?, birth_date = ?, race = ?, photo = ?, owner_id = ?, updated_at = ? WHERE id = ?";

        LOGGER.trace("Trying to update horse {}", horse);
        LOGGER.debug("Trying to execute SQL query: {}", sql);

        try{
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName());
                stmt.setString(2, horse.getDescription());
                stmt.setInt(3, horse.getRating());
                stmt.setString(4, horse.getBirthDate());
                stmt.setString(5, horse.getRace());
                stmt.setString(6, horse.getPhoto());
                stmt.setLong(7, horse.getOwnerId());
                stmt.setObject(8, horse.getUpdatedAt());
                stmt.setLong(9, horse.getId());
                return stmt;
            }, keyHolder);
            LOGGER.info("Successfully updated horse " + horse.toString());
            return horse;

        } catch (DataAccessException e){
            LOGGER.error("Error occured while trying to update horse" + horse.toString() + "\n" + e.getMessage());
            throw new PersistenceException("Error occured while trying to update horse " + horse.toString());
        }
    }

    private Horse mapRow(ResultSet resultSet, int i) throws SQLException {
        final Horse horse = new Horse();
        horse.setId(resultSet.getLong("id"));
        horse.setName(resultSet.getString("name"));
        horse.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        horse.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        horse.setBirthDate(resultSet.getString("birth_date"));
        horse.setDescription(resultSet.getString("description"));
        horse.setRating(resultSet.getInt("rating"));
        horse.setRace(resultSet.getString("race"));
        horse.setPhoto(resultSet.getString("photo"));
        horse.setOwnerId(resultSet.getLong("owner_id"));
        return horse;
    }

    @Override
    public void deleteHorse(Long id) throws PersistenceException {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";

        LOGGER.trace("Trying to delete horse with id {}", id);
        LOGGER.debug("Trying to execute SQL query: {}", sql);


        try {

            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1,id);
                return stmt;
            }, keyHolder);
            LOGGER.info("Successfully deleted horse with id " + id);

        } catch (DataAccessException e){
            LOGGER.error("Error occured while trying to delete horse with id" + id + "\n" + e.getMessage());
            throw new PersistenceException("Error occured while trying to delete horse " + id);
        }
    }

    @Override
    public List<Horse> getHorsesOfOwner(Long ownerId) throws PersistenceException {
        try {
            if (ownerId != null) {

                final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE owner_id=?";

                LOGGER.trace("Trying to get horses with ownerDd {}", ownerId);
                LOGGER.debug("Trying to execute SQL query: {}", sql);

                List<Horse> horses = jdbcTemplate.query(connection -> {
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stmt.setLong(1, ownerId);
                    return stmt;
                }, this::mapRow);

                if (horses.isEmpty()) throw new NotFoundException("Could not find horse with ownerId: " + ownerId);

                LOGGER.info("Successfully read horses with ownerId:{}", ownerId);
                return horses;

            }  else {
                LOGGER.error("Cant find horses without given ownerId!");
                throw new NotFoundException("Cant find horses without given ownerId!");
            }

        }catch(DataAccessException e){
            LOGGER.error("Erroc occured during database access trying to get horses with " + ownerId + "\n" + e.getMessage());
            throw new PersistenceException("Erroc occured during database access trying to get horses with " + ownerId + "\n" + e.getMessage());
        }
    }
}
