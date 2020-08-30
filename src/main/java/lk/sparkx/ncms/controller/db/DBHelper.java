package lk.sparkx.ncms.controller.db;

import lk.sparkx.ncms.utils.Helper;
import lombok.NoArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@NoArgsConstructor
public class DBHelper {

    private BasicDataSource basicDataSource;

    /**
     * Creates the database if it doesn't exist
     *
     * @return status of type boolean
     */
    public boolean createDB() {
        boolean status = false;
        Connection con = null;
        Statement stmt = null;

        try {
            basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            basicDataSource.setUrl(Helper.DB_URL);
            basicDataSource.setUsername(Helper.USERNAME);
            basicDataSource.setPassword(Helper.PASSWORD);

            con = basicDataSource.getConnection();
            stmt = con.createStatement();

            String sql = Helper.CREATE_DATABASE;
            status = stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("Error : While Creating Database");
            e.printStackTrace();
        } finally {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
        return status;
    }

    /**
     * Creates the hospitals table if it doesn't exist in the database
     *
     * @return status of type boolean
     */
    public boolean createHospitalsTable(){
        boolean status = false;
        Statement stmt = null;
        Connection con = null;
        try {
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();

            String sql = Helper.CREATE_HOSPITALS_TABLE;
            status = stmt.execute(sql);
        } catch (SQLException sqe) {
            System.out.println("Error : While Creating Table (Table Already Exists)");
            sqe.printStackTrace();
        } finally {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
        return status;
    }

    /**
     * Creates the doctors table if it doesn't exist in the database
     *
     * @return status of type boolean
     */
    public boolean createDoctorsTable(){
        boolean status = false;
        Statement stmt = null;
        Connection con = null;
        try {
            con = DBConnectionPool.getInstance().getConnection();

            stmt = con.createStatement();
            String sql = Helper.CREATE_DOCTORS_TABLE;
            status = stmt.execute(sql);
        } catch (SQLException sqe) {
            System.out.println("Error : While Creating Table (Table Already Exists)");
            sqe.printStackTrace();
        } finally {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
        return status;
    }

    /**
     * Creates the patients table if it doesn't exist in the database
     *
     * @return status of type boolean
     */
    public boolean createPatientsTable(){
        boolean status = false;
        Statement stmt = null;
        Connection con = null;
        try {
            con = DBConnectionPool.getInstance().getConnection();
            String sql = Helper.CREATE_PATIENTS_TABLE;
            stmt = con.createStatement();
            status = stmt.execute(sql);
        } catch (SQLException sqe) {
            System.out.println("Error : While Creating Table (Table Already Exists)");
            sqe.printStackTrace();
        } finally {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
        return status;
    }

    /**
     * Creates the hospital beds table if it doesn't exist in the database
     *
     * @return status of type boolean
     */
    public boolean createHospitalBedsTable(){
        boolean status = false;
        Statement stmt = null;
        Connection con = null;
        try {
            con = DBConnectionPool.getInstance().getConnection();
            String sql = Helper.CREATE_HOSPITAL_BEDS_TABLE;
            stmt = con.createStatement();
            status = stmt.execute(sql);
        } catch (SQLException sqe) {
            System.out.println("Error : While Creating Table (Table Already Exists)");
            sqe.printStackTrace();
        } finally {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
        return status;
    }

    /**
     * Creates the patient queue table if it doesn't exist in the database
     *
     * @return status of type boolean
     */
    public boolean createPatientQueueTable(){
        boolean status = false;
        Statement stmt = null;
        Connection con = null;
        try {
            con = DBConnectionPool.getInstance().getConnection();
            String sql = Helper.CREATE_PATIENT_QUEUE_TABLE;
            stmt = con.createStatement();
            status = stmt.execute(sql);
        } catch (SQLException sqe) {
            System.out.println("Error : While Creating Table (Table Already Exists)");
            sqe.printStackTrace();
        } finally {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
        return status;
    }

    /**
     * Creates the user table if it doesn't exist in the database
     *
     * @return status of type boolean
     */
    public boolean createUserTable(){
        boolean status = false;
        Statement stmt = null;
        Connection con = null;
        try {
            con = DBConnectionPool.getInstance().getConnection();
            String sql = Helper.CREATE_USERS_TABLE;
            stmt = con.createStatement();
            status = stmt.execute(sql);
        } catch (SQLException sqe) {
            System.out.println("Error : While Creating Table (Table Already Exists)");
            sqe.printStackTrace();
        } finally {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
        return status;
    }
}
