package codeworld.projectjava.inventory.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.inventory.model.Request;
import codeworld.projectjava.inventory.model.Student;

@Repository
public class RequestRepository {
    private final JdbcTemplate jdbcTemp;

    public RequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemp = jdbcTemplate;
    }

    public Request save(Request req) {
        if (req.getId() == null) {
            String sql = "INSERT INTO request(student_id, academic_id, request_date, request_nature, sending_address, receiving_address, request_status) VALUES(?,?,?,?,?,?,?)";
            jdbcTemp.update(sql, req.getStudentId(), req.getAcademicId(), req.getRequestDate(), req.getRequestNature(), req.getSendingAddress(), req.getReceivingAddress(), req.getRequestStatus());

            Long id = jdbcTemp.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            req.setId(id);
        } else {
            String sql = "UPDATE request SET student_id = ?, academic_id = ?, request_date = ?, request_nature = ?, sending_address = ?, receiving_address = ?, request_status = ? WHERE id = ?";
            jdbcTemp.update(sql, req.getStudentId(), req.getAcademicId(), req.getRequestDate(), req.getRequestNature(), req.getSendingAddress(), req.getReceivingAddress(), req.getRequestStatus(), req.getId());
        }
        return req;
    }

    public List<Request> findAll() {
        String sql = "SELECT * FROM request";
        return jdbcTemp.query(sql, new BasicRequestRowMapper());
    }

    public Optional<Request> findById(Long id) {
        String sql = "SELECT * FROM request WHERE id = ?";
        try {
            Request request = jdbcTemp.queryForObject(sql, new BasicRequestRowMapper(), id);
            return Optional.ofNullable(request);
        } catch (EmptyResultDataAccessException emp) {
            return Optional.empty();
        }
    }

    public List<Request> findByStudentId(Long studentId) {
        String sql = "SELECT * FROM request WHERE student_id = ?";
        return jdbcTemp.query(sql, new BasicRequestRowMapper(), studentId);
    }

    // Get requests with student relationships
    public List<Request> findAllWithStudentRelations() {
        String sql = """
            SELECT 
                r.*,
                s.id as student_id, s.stu_name, s.birth_date, s.email_address, s.phone_number
            FROM request r
            LEFT JOIN student s ON r.student_id = s.id
            ORDER BY r.id
            """;

        return jdbcTemp.query(sql, new RequestWithStudentExtractor());
    }

    public Optional<Request> findByIdWithStudent(Long id) {
        String sql = """
            SELECT 
                r.*,
                s.id as student_id, s.stu_name, s.birth_date, s.email_address, s.phone_number
            FROM request r
            LEFT JOIN student s ON r.student_id = s.id
            WHERE r.id = ?
            """;

        try {
            List<Request> requests = jdbcTemp.query(sql, new RequestWithStudentExtractor(), id);
            return requests.isEmpty() ? Optional.empty() : Optional.of(requests.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteRequest(Long id) {
        String sql = "DELETE FROM request WHERE id = ?";
        jdbcTemp.update(sql, id);
    }

    // Row Mappers
    private static class BasicRequestRowMapper implements RowMapper<Request> {
        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
            Request request = new Request();
            request.setId(rs.getLong("id"));
            request.setStudentId(rs.getLong("student_id"));
            request.setAcademicId(rs.getLong("academic_id"));
            request.setRequestDate(rs.getObject("request_date", LocalDateTime.class));
            request.setRequestNature(rs.getString("request_nature"));
            request.setSendingAddress(rs.getString("sending_address"));
            request.setReceivingAddress(rs.getString("receiving_address"));
            request.setRequestStatus(rs.getString("request_status"));
            return request;
        }
    }

    private static class RequestWithStudentExtractor implements ResultSetExtractor<List<Request>> {
        @Override
        public List<Request> extractData(ResultSet rs) throws SQLException {
            List<Request> requests = new ArrayList<>();

            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getLong("id"));
                request.setStudentId(rs.getLong("student_id"));
                request.setAcademicId(rs.getLong("academic_id"));
                request.setRequestDate(rs.getObject("request_date", LocalDateTime.class));
                request.setRequestNature(rs.getString("request_nature"));
                request.setSendingAddress(rs.getString("sending_address"));
                request.setReceivingAddress(rs.getString("receiving_address"));
                request.setRequestStatus(rs.getString("request_status"));

                // Add student information if available
                Long studentId = rs.getLong("student_id");
                if (!rs.wasNull() && studentId > 0) {
                    Student student = new Student();
                    student.setId(studentId);
                    student.setStudentName(rs.getString("stu_name"));
                    student.setBirthDate(rs.getObject("birth_date", LocalDate.class));
                    student.setEmail(rs.getString("email_address"));
                    student.setPhoneNumber(rs.getString("phone_number"));
                    request.setStudent(student);
                }

                requests.add(request);
            }

            return requests;
        }
    }
}