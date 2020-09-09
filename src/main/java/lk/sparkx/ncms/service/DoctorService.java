package lk.sparkx.ncms.service;

import lk.sparkx.ncms.controller.db.DBConnectionPool;
import lk.sparkx.ncms.model.Doctor;
import lk.sparkx.ncms.utils.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorService {

    HospitalService hospitalService = new HospitalService();

    public boolean addNewDoctor(Doctor newDoctor) {
        Connection con = null;
        PreparedStatement stmt = null;
        try
        {
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.prepareStatement(Helper.ADD_NEW_DOCTOR);
            stmt.setString(1, newDoctor.getName());
            stmt.setString(2, newDoctor.getHospitalId());
            stmt.setInt(3, newDoctor.getIsDirector());

            int changedRows = stmt.executeUpdate();
            return (changedRows == 1);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
    }
}
