package codeworld.projectjava.inventory.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.inventory.model.Student;
import codeworld.projectjava.inventory.model.Academic;
import codeworld.projectjava.inventory.model.Request;

@Repository
public class StudentRepository {
    private final JdbcTemplate jdbcTemp;

    public StudentRepository(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    // Basic CRUD operations
    public Student save(Student student) {
        if (student.getId() == null) {
            String sql = "INSERT INTO student (stu_name, birth_date, email_address, phone_number) VALUES (?, ?, ?, ?)";
            jdbcTemp.update(sql, student.getStudentName(), student.getBirthDate(), student.getEmail(), student.getPhoneNumber());

            Long id = jdbcTemp.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            student.setId(id);
        }
        return student;
    }

    public Optional<Student> findById(Long id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try {
            Student student = jdbcTemp.queryForObject(sql, new BasicStudentRowMapper(), id);
            return Optional.ofNullable(student);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Student> findByEmail(String email) {
        String sql = "SELECT * FROM student WHERE email_address = ?";
        try {
            Student student = jdbcTemp.queryForObject(sql, new BasicStudentRowMapper(), email);
            return Optional.ofNullable(student);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Student> findAll() {
        String sql = "SELECT * FROM student";
        return jdbcTemp.query(sql, new BasicStudentRowMapper());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM student WHERE id = ?";
        jdbcTemp.update(sql, id);
    }

    // Relationship methods
    public List<Student> findAllWithRelations() {
        String sql = """
            SELECT 
                s.id as student_id, s.stu_name, s.birth_date, s.email_address, s.phone_number,
                a.id as academic_id, a.academic_year, a.programme, a.certificate_type, a.other_docs,
                r.id as request_id, r.student_id as request_student_id, r.academic_id as request_academic_id,
                r.request_nature, r.sending_address, r.receiving_address, 
                r.request_status, r.request_date
            FROM student s
            LEFT JOIN student_academic sa ON s.id = sa.student_id
            LEFT JOIN academic a ON sa.academic_id = a.id
            LEFT JOIN request r ON s.id = r.student_id
            ORDER BY s.id, a.id, r.id
            """;

        return jdbcTemp.query(sql, new StudentRelationsExtractor());
    }

    public Optional<Student> findByIdWithRelations(Long id) {
        String sql = """
            SELECT 
                s.id as student_id, s.stu_name, s.birth_date, s.email_address, s.phone_number,
                a.id as academic_id, a.academic_year, a.programme, a.certificate_type, a.other_docs,
                r.id as request_id, r.student_id as request_student_id, r.academic_id as request_academic_id,
                r.request_nature, r.sending_address, r.receiving_address, 
                r.request_status, r.request_date
            FROM student s
            LEFT JOIN student_academic sa ON s.id = sa.student_id
            LEFT JOIN academic a ON sa.academic_id = a.id
            LEFT JOIN request r ON s.id = r.student_id
            WHERE s.id = ?
            ORDER BY a.id, r.id
            """;

        try {
            List<Student> students = jdbcTemp.query(sql, new StudentRelationsExtractor(), id);
            return students.isEmpty() ? Optional.empty() : Optional.of(students.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // Row Mappers
    private static class BasicStudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setStudentName(rs.getString("stu_name"));
            student.setBirthDate(rs.getObject("birth_date", LocalDate.class));
            student.setEmail(rs.getString("email_address"));
            student.setPhoneNumber(rs.getString("phone_number"));
            return student;
        }
    }

    private static class StudentRelationsExtractor implements ResultSetExtractor<List<Student>> {
        @Override
        public List<Student> extractData(ResultSet rs) throws SQLException {
            Map<Long, Student> studentMap = new LinkedHashMap<>();

            while (rs.next()) {
                Long studentId = rs.getLong("student_id");

                // Get or create student
                Student student = studentMap.get(studentId);
                if (student == null) {
                    student = new Student();
                    student.setId(studentId);
                    student.setStudentName(rs.getString("stu_name"));
                    student.setBirthDate(rs.getObject("birth_date", LocalDate.class));
                    student.setEmail(rs.getString("email_address"));
                    student.setPhoneNumber(rs.getString("phone_number"));
                    student.setAcademics(new ArrayList<>());
                    student.setRequests(new ArrayList<>());
                    studentMap.put(studentId, student);
                }

                // Process academic record if exists
                Long academicId = rs.getLong("academic_id");
                if (!rs.wasNull() && academicId > 0) {
                    Academic academic = new Academic();
                    academic.setId(academicId);
                    academic.setAcademicYear(rs.getString("academic_year"));
                    academic.setProgramme(rs.getString("programme"));
                    academic.setCertificateType(rs.getString("certificate_type"));
                    academic.setOtherDocs(rs.getString("other_docs"));

                    if (student.getAcademics().stream().noneMatch(a -> a.getId().equals(academicId))) {
                        student.addAcademic(academic);
                    }
                }

                // Process request if exists
                Long requestId = rs.getLong("request_id");
                if (!rs.wasNull() && requestId > 0) {
                    Request request = new Request();
                    request.setId(requestId);
                    request.setStudentId(rs.getLong("request_student_id"));
                    request.setAcademicId(rs.getLong("request_academic_id"));
                    request.setRequestNature(rs.getString("request_nature"));
                    request.setSendingAddress(rs.getString("sending_address"));
                    request.setReceivingAddress(rs.getString("receiving_address"));
                    request.setRequestStatus(rs.getString("request_status"));
                    request.setRequestDate(rs.getObject("request_date", LocalDateTime.class));

                    if (student.getRequests().stream().noneMatch(r -> r.getId().equals(requestId))) {
                        student.addRequest(request);
                    }
                }
            }

            return new ArrayList<>(studentMap.values());
        }
    }
}