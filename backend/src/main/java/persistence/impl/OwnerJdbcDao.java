package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerJdbcDao implements OwnerDao {

    private static final String TABLE_NAME = "Owner";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OwnerJdbcDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public Owner insertOwner(Owner owner) throws PersistenceException {


            final String sql = "INSERT INTO " + TABLE_NAME +
                " (name, created_at, updated_at) VALUES (?,?,?) ";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            try{
                LOGGER.trace("Trying to insert owner in persistence layer: {}", owner);
                LOGGER.debug("Trying to execute SQL update: {}", sql);
                jdbcTemplate.update(connection -> {
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, owner.getName());
                    stmt.setObject(2, owner.getCreatedAt());
                    stmt.setObject(3, owner.getUpdatedAt());
                    return stmt;
                }, keyHolder);
                owner.setId(((Number) keyHolder.getKeys().get("id")).longValue());
                LOGGER.info("Successfully inserted " + owner.toString());
                return owner;

            } catch (DataAccessException e){
                LOGGER.error("Error occured while trying to insert owner" + owner.toString() + "\n" + e.getMessage());
                throw new PersistenceException("Error occured while trying to insert owner " + owner.toString() + "\n" + e.getMessage());
            }
    }

    @Override
    public Owner updateOwner(Owner owner) throws PersistenceException {

        final String sql = "UPDATE " + TABLE_NAME +
            " SET name = ? WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();


        try {
            if(owner.getId() != null && owner.getName() != null){
            LOGGER.trace("Trying to insert owner in persistence layer: {}", owner);
            LOGGER.debug("Trying to execute SQL update: {}", sql);
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, owner.getName());
                stmt.setLong(2, owner.getId());
                return stmt;
            }, keyHolder);
            owner.setId(((Number) keyHolder.getKeys().get("id")).longValue());
            LOGGER.info("Successfully updated " + owner.toString());
            return owner;
        }else{
            throw new PersistenceException("Cannot update owner without necessary values");
            }

        } catch (DataAccessException e){
            LOGGER.error("Error occured while trying to update owner " + owner.toString() + "\n" + e.getMessage());
            throw new PersistenceException("Error occured while trying to update owner " + owner.toString() + "\n" + e.getMessage());
        }
    }

    @Override
    public void deleteOwner(Long id) throws PersistenceException {
        KeyHolder keyHolder = new GeneratedKeyHolder();


        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";

        try {
            LOGGER.trace("Trying to delete owner in persistence layer with id: {} ", id);
            LOGGER.debug("Trying to execute SQL update: {}", sql);

            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1,id);
                return stmt;
            }, keyHolder);
            LOGGER.info("Successfully deleted owner with id " + id);

        } catch (DataAccessException e){
            LOGGER.error("Error occured while trying to update owner with id" + id + "\n" + e.getMessage());
            throw new PersistenceException("Error occured while trying to delete owner " + id + "\n" + e.getMessage());
        }
    }

    @Override
    public List<Owner> getOwner(Owner owner) throws PersistenceException {
        try {
            if (owner.getName() != null) {
                final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE ?";

                LOGGER.trace("Trying to get owner in persistence layer with name: {}", owner.getName());
                LOGGER.debug("Trying to execute SQL query: {}", sql);

                List<Owner> owners = jdbcTemplate.query(connection -> {
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, "%"+owner.getName()+"%");
                    return stmt;
                }, this::mapRow);

                if (owners.isEmpty()) throw new NotFoundException("Could not find owner with name: " + owner.getName());

                LOGGER.info("Successfully read owner with name: {}", owner.getName());
                return owners;

            }else if (owner.getId() != null) {
                Long id = owner.getId();
                final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

                LOGGER.trace("Get owner with id {}", id);
                LOGGER.debug("Trying to execute SQL query: {}", sql);

                List<Owner> owners = jdbcTemplate.query(connection -> {
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stmt.setLong(1, id);
                    return stmt;
                }, this::mapRow);

                if (owners.isEmpty()) throw new NotFoundException("Could not find owner with id: " + id);

                LOGGER.info("Successfully read owner with id: {}", id);
                return owners;

            } else {
                LOGGER.error("Cant find owner without any values!");
                throw new NotFoundException("Cant find owner without any values!");
            }

        }catch(DataAccessException e){
            LOGGER.error("Erroc occured during database access" + owner.toString() + "\n" + e.getMessage());
            throw new PersistenceException("Erroc occured during database access" + owner.toString() + "\n" + e.getMessage());
        }
    }

    private Owner mapRow(ResultSet resultSet, int i) throws SQLException {
        final Owner owner = new Owner();
        owner.setId(resultSet.getLong("id"));
        owner.setName(resultSet.getString("name"));
        owner.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        owner.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        return owner;
    }

}
