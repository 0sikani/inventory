package codeworld.projectjava.inventory.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.inventory.model.Academic;

@Repository
public class AcademicRepository {
    private final JdbcTemplate jdbcTemp;

    public AcademicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemp = jdbcTemplate;
    }

    public Academic save(Academic academic) {
        if (academic.getId() == null) {
            String sql = "INSERT INTO academic (academic_year, programme, certificate_type, other_docs) VALUES (?, ?, ?, ?)";
            jdbcTemp.update(sql, academic.getAcademicYear(), academic.getProgramme(), academic.getCertificateType(), academic.getOtherDocs());

            Long id = jdbcTemp.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            academic.setId(id);
        } else {
            String sql = "UPDATE academic SET academic_year = ?, programme = ?, certificate_type = ?, other_docs = ? WHERE id = ?";
            jdbcTemp.update(sql, academic.getAcademicYear(), academic.getProgramme(), academic.getCertificateType(), academic.getOtherDocs(), academic.getId());
        }
        return academic;
    }

    public List<Academic> findAll() {
        String sql = "SELECT * FROM academic";
        return jdbcTemp.query(sql, new AcademicRowMapper());
    }

    public Optional<Academic> findById(Long id) {
        String sql = "SELECT * FROM academic WHERE id = ?";
        try {
            Academic academic = jdbcTemp.queryForObject(sql, new AcademicRowMapper(), id);
            return Optional.ofNullable(academic);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Academic> findByStudentId(Long studentId) {
        String sql = "SELECT a.* FROM academic a INNER JOIN student_academic sa ON a.id = sa.academic_id WHERE sa.student_id = ?";
        return jdbcTemp.query(sql, new AcademicRowMapper(), studentId);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM academic WHERE id = ?";
        jdbcTemp.update(sql, id);
    }

    private static class AcademicRowMapper implements RowMapper<Academic> {
        @Override
        public Academic mapRow(ResultSet rset, int rowNum) throws SQLException {
            Academic academic = new Academic();
            academic.setId(rset.getLong("id"));
            academic.setAcademicYear(rset.getString("academic_year"));
            academic.setCertificateType(rset.getString("certificate_type"));
            academic.setProgramme(rset.getString("programme"));
            academic.setOtherDocs(rset.getString("other_docs"));
            return academic;
        }
    }
}