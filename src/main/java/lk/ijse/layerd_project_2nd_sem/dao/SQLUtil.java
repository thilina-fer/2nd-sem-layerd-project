package lk.ijse.layerd_project_2nd_sem.dao;

import lk.ijse.layerd_project_2nd_sem.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {
    public static ResultSet executeQuery(String sql, Object... ob) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        for (int i = 0; i < ob.length; i++) {
            pstm.setObject(i + 1, ob[i]);
        }
        return pstm.executeQuery();
    }
    public static boolean executeUpdate(String sql, Object... ob) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        for (int i = 0; i < ob.length; i++) {
            pstm.setObject(i + 1, ob[i]);
        }
        return pstm.executeUpdate()>0;
    }
}
