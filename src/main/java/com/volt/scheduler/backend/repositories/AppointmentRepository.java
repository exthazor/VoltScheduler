package com.volt.scheduler.backend.repositories;

import com.volt.scheduler.backend.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AppointmentRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Appointment> appointmentRowMapper = (rs, rowNum) -> {

        Appointment appointment = new Appointment();
        appointment.setId(rs.getLong("id"));
        appointment.setOperatorId(rs.getLong("operator_id"));
        appointment.setStartTime(rs.getTimestamp("start_time"));
        appointment.setEndTime(rs.getTimestamp("end_time"));
        return appointment;
    };

    public List<Appointment> findAll() {

        String query = "select * " +
                "from " +
                "appointments";

        return jdbcTemplate.query(query, appointmentRowMapper);
    }

    public List<Appointment> findById(Long id) {

        String query = "select * " +
                "from " +
                "appointments " +
                "where id = ?";

        return jdbcTemplate.query(
                query,
                ps -> ps.setLong(1, id),
                appointmentRowMapper
        );
    }


    public int save(Appointment appointment) {

        String query = "insert into " +
                "appointments " +
                "(operator_id, start_time, end_time, description) " +
                "values (?, ?, ?)";

        return jdbcTemplate.update(
                query,
                appointment.getOperatorId(), appointment.getStartTime(), appointment.getEndTime()
        );
    }

    public int update(Appointment appointment) {

        String query = "update appointments " +
                "set operator_id = ?, start_time = ?, end_time = ?, " +
                "last_accessed_timestamp = current_timestamp " +
                "where id = ?";

        return jdbcTemplate.update(
                query,
                appointment.getOperatorId(), appointment.getStartTime(), appointment.getEndTime(), appointment.getId()
        );
    }

    public int deleteById(Long id) {

        return jdbcTemplate.update("DELETE FROM appointments WHERE id = ?", id);
    }

    public List<Appointment> findAppointmentsByOperatorAndTime(Long operatorId, Timestamp startTime, Timestamp endTime) {

        String query = "select * " +
                "from " +
                "appointments " +
                "where " +
                "operator_id = ? and " +
                "((start_time < ? and end_time > ?) " +
                "or (start_time < ? and end_time > ?))";

        return jdbcTemplate.query(query, new Object[]{operatorId, startTime, endTime, endTime, startTime}, appointmentRowMapper);
    }

    public List<Long> findAvailableOperator(Timestamp startTime, Timestamp endTime) {

        String sql = "select o.operator_id " +
                "from " +
                "operators o " +
                "left join appointments a on o.operator_id = a.operator_id " +
                "and (a.start_time < ? and a.end_time > ?) " +
                "or (a.start_time < ? and a.end_time > ?) " +
                "where " +
                "a.appointment_id is null " +
                "group by o.operator_id " +
                "limit 1";

        return jdbcTemplate.query(sql, new Object[]{startTime, endTime}, (rs, rowNum) -> rs.getLong("operator_id"));
    }
}
