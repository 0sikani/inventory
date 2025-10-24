package codeworld.projectjava.inventory.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.inventory.model.Request;

@Repository
public class RequestRepository {
    private final JdbcTemplate jdbcTemp;

    public RequestRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemp = jdbcTemplate;
    }

    public Request save(Request req){
        if(req.getId() == null){
            String sql = "INSERT INTO request(studentId, academicId, requestDate, requestNature, sendingAddress, receivingAddress, requestStatus) VALUES(?,?,?,?,?,?,?)";
            jdbcTemp.update(sql, req.getStudentId(), req.getAcademicId(), req.getRequestDate(), req.getRequestNature(), req.getSendingAddress(), req.getReceivingAddress(), req.getRequestNature());

            Long id = jdbcTemp.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            req.setId(id);
        }else{
            String sql = "UPDATE request SET studentId = ?, academicId = ?, requestDate = ?, requestNature = ?, sendingAddress = ?, receivingAddress = ?, requestStatus = ? WHERE id = ?";
            jdbcTemp.update(sql, req.getStudentId(), req.getAcademicId(), req.getRequestDate(), req.getRequestNature(), req.getSendingAddress(), req.getReceivingAddress(), req.getRequestNature(), req.getId());
        }
        return req;
    }

    public List<Request> findAll(){
        String sql = "SELECT * FROM request";
        return jdbcTemp.query(sql, new RequestRowMapper());
    }

    public Optional<Request> findById(Long id){
        String sql = "SELECT * FROM request WHERE id = ?";
        try{
            Request reqq = jdbcTemp.queryForObject(sql, new RequestRowMapper(), id);
            return Optional.ofNullable(reqq);
        } catch (EmptyResultDataAccessException emp) {
            return Optional.empty();
        } 
    }

    public Optional<Request> findByEmail(String email){
        String sql = "SELECT * FROM request WHERE email = ?";
        try {
            Request reqq = jdbcTemp.queryForObject(sql, new RequestRowMapper(), email);
            return Optional.ofNullable(reqq);
        } catch (EmptyResultDataAccessException emp) {
            return Optional.empty();
        }
    }

    public void deleteRequest(Long id){
        String sql = "DELETE FROM request WHERE id = ?";
        jdbcTemp.update(sql, id);
    }

    private static class RequestRowMapper implements RowMapper<Request> {
        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException{
            Request request = new Request();
            request.setId(rs.getLong("id"));
            request.setStudentId(rs.getLong("studentId"));
            request.setAcademicId(rs.getLong("academicId"));
            request.setRequestDate(rs.getObject("requestDate", LocalDateTime.class));
            request.setRequestNature(rs.getString("requestNature"));
            request.setSendingAddress(rs.getString("sendingAddress"));
            request.setReceivingAddress(rs.getString("receivingAddress"));
            request.setRequestStatus(rs.getString("requestStatus"));
            return request;
        }
    }
    
}
