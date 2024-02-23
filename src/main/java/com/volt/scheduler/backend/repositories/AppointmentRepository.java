package com.volt.scheduler.backend.repositories;

import com.volt.scheduler.backend.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class AppointmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AppointmentRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Appointment> appointmentRowMapper = (rs, rowNum) -> {

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getLong("id"));
        appointment.setOperatorId(rs.getLong("operator_id"));
        appointment.setStartTime(rs.getTimestamp("start_time"));
        appointment.setEndTime(rs.getTimestamp("end_time"));
        return appointment;
    };

    // Find an appointment by appointmentId
    public List<Appointment> findById(Long appointmentId) {

        String query = "select * " +
                "from " +
                "Appointments " +
                "where appointment_id = ?";

        return jdbcTemplate.query(
                query,
                ps -> ps.setLong(1, appointmentId),
                appointmentRowMapper
        );
    }

    // Save appointment
    public void save(Appointment appointment) {

        String query = "insert into " +
                "Appointments " +
                "(operator_id, start_time, end_time, description) " +
                "values (?, ?, ?)";

        jdbcTemplate.update(
                query,
                appointment.getOperatorId(), appointment.getStartTime(), appointment.getEndTime()
        );
    }

    // Delete appointment by appointment id
    public void deleteById(Long appointmentId) {

        String query = "delete " +
                "from " +
                "Appointments " +
                "where " +
                "appointment_id = ?";

        jdbcTemplate.update(query, appointmentId);
    }

    // Find all appointments of an operator by their id, startTime, and endTime
    public List<Appointment> findAppointmentsByOperatorAndTime(Long operatorId, Timestamp startTime, Timestamp endTime) {

        String query = "select * " +
                "from " +
                "Appointments " +
                "where " +
                "operator_id = ? and " +
                "((start_time < ? and end_time > ?) " +
                "or (start_time < ? and end_time > ?))";

        return jdbcTemplate.query(query, new Object[]{operatorId, startTime, endTime, endTime, startTime}, appointmentRowMapper);
    }

    // Find all available operators by their startTime and endTime of operation
    public List<Long> findAvailableOperator(Timestamp startTime, Timestamp endTime) {

        String sql = "select o.operator_id " +
                "from " +
                "Operators o " +
                "left join Appointments a on o.operator_id = a.operator_id " +
                "and ((a.start_time <= ? and a.end_time >= ?) " +
                "or (a.start_time >= ? and a.end_time <= ?)) " +
                "where " +
                "a.appointment_id is null " +
                "group by o.operator_id " +
                "limit 1";

        return jdbcTemplate.query(
                sql,
                new Object[]{startTime, endTime, startTime, endTime},
                (rs, rowNum) -> rs.getLong("operator_id")
        );
    }

    // Find all Appointments by Operator ID
    public List<Appointment> findAllByOperatorId(Long operatorId) {

        String query = "select * " +
                "from " +
                "Appointments " +
                "where " +
                "operator_id = ? " +
                "order by start_time asc";

        return jdbcTemplate.query(query, new Object[]{operatorId}, appointmentRowMapper);
    }

}
