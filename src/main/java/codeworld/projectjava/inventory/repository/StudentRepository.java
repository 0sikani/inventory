package codeworld.projectjava.inventory.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.inventory.model.Student;



@Repository
public class StudentRepository {
    private final JdbcTemplate jdbcTemp;

    public StudentRepository(JdbcTemplate jdbcTemp){
        this.jdbcTemp = jdbcTemp;
    }

    public Student save(Student student){
        if(student.getId() == null){
            String sql = "INSERT INTO student (academicId, studentName, birthDate, email, phoneNumber, profilePicturePath) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemp.update(sql, student.getAcademicId(), student.getStudentName(), student.getBirthDate(), student.getEmail(), student.getPhoneNumber(), student.getProfilePicturePath());

            Long id = jdbcTemp.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            student.setId(id);
        }
        return student;
    }    
    
    public Optional<Student> findById(Long id){
        String sql = "SELECT * FROM student WHERE id = ?";
        try {
              Student student = jdbcTemp.queryForObject(sql, new StudentRowMapper(), id);
              return Optional.ofNullable(student);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Student> findByEmail(String email){
        String sql = "SELECT * FROM student WHERE email = ?";
        try {
            Student student = jdbcTemp.queryForObject(sql, new StudentRowMapper(), email);
            return Optional.ofNullable(student);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
      }
    
      public List<Student> findAll(){
        String sql = "SELECT * FROM student";
        return jdbcTemp.query(sql, new StudentRowMapper());
      }

      public void delete(Long id){
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemp.update(sql, id);
      }


    private static class StudentRowMapper implements RowMapper<Student>{
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setAcademicId(rs.getLong("academicId"));
            student.setStudentName(rs.getString("studentName"));
            student.setBirthDate(rs.getObject("birthDate", LocalDate.class));
            student.setEmail(rs.getString("email"));
            student.setPhoneNumber(rs.getString("phoneNumber"));
            student.setProfilePicturePath(rs.getString("profilePicturePath"));
            return student;
        }
    }
        
}
