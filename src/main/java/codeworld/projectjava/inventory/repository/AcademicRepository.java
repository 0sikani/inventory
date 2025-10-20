package codeworld.projectjava.inventory.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.inventory.model.Academic;

@Repository
public class AcademicRepository {
    private final JdbcTemplate jdbcTemp;

    public AcademicRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemp = jdbcTemplate;
    }

    public Academic save(Academic demc){
        if(demc.getId() == null){
            String sql = "INSERT INTO academic (academic_year, programme, certificate_type, other_docs) VALUES (?, ?, ?, ?)";
            jdbcTemp.update(sql, demc.getAcademicYear(), demc.getProgramme(), demc.getCirtificateType(), demc.getOtherDocs());

            Long id = jdbcTemp.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            demc.setId(id);
        }
        else{
            String sql = "UPDATE academic SET academic_year = ?, programme = ?, certificate_type = ?, other_docs = ? WHERE id = ?";
            jdbcTemp.update(sql, demc.getAcademicYear(), demc.getProgramme(), demc.getCirtificateType(), demc.getOtherDocs(), demc.getId());
        }
        return demc;
    }

    public Optional<Academic> findById(Long id){
        String sql = "SELECt * from academic WHERE id = ?";
        try{
            Academic acdmc = jdbcTemp.queryForObject(sql, new AcademicRowMapper(), id);
            return Optional.ofNullable(acdmc);
        }catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Academic> findByEmail(String email){
        String sql = "SELECT * FROM academic WHERE email = ?";
        try {
            Academic acdmc = jdbcTemp.queryForObject(sql, new AcademicRowMapper(), email);
            return Optional.ofNullable(acdmc);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(Long id){
        String sql = "DELETE FROM academic WHERE id = ?";
        jdbcTemp.update(sql, id);
    }

    private static class AcademicRowMapper implements RowMapper<Academic> {
        @Override
        public Academic mapRow(ResultSet rset, int rowNum) throws SQLException {
            Academic aca = new Academic();
            aca.setId(rset.getLong("id"));
            aca.setAcademicYear(rset.getString("academicYear"));
            aca.setCirtificateType(rset.getString("cirtificateType"));
            aca.setProgramme(rset.getString("programme"));
            aca.setOtherDocs(rset.getString("otherDocs"));
            return aca;
        }
    }
}
