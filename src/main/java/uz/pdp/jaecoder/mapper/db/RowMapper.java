package uz.pdp.jaecoder.mapper.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    T mapTo(ResultSet resultSet) throws SQLException;
}
