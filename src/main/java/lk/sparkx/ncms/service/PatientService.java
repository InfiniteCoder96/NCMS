package lk.sparkx.ncms.service;

import lk.sparkx.ncms.controller.db.DBConnectionPool;
import lk.sparkx.ncms.model.Gender;
import lk.sparkx.ncms.model.Patient;
import lk.sparkx.ncms.model.SeverityLevel;
import lk.sparkx.ncms.repository.PatientRepository;
import lk.sparkx.ncms.utils.Helper;

import java.sql.*;
import java.util.UUID;

public class PatientService implements PatientRepository {

    @Override
    public boolean registerPatient(Patient patient) {
        Connection con = null;
        PreparedStatement stmt = null;
        try
        {
            UUID uuid = UUID.randomUUID();

            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.prepareStatement(Helper.INSERT_PATIENT);
            stmt.setString(1, uuid.toString());
            stmt.setString(2, generatePatientSerialNo());
            stmt.setString(3, patient.getFirstName());
            stmt.setString(4, patient.getLastName());
            stmt.setString(5, patient.getDistrict());
            stmt.setInt(6, patient.getLocationX());
            stmt.setInt(7, patient.getLocationY());
            stmt.setString(8, patient.getGender().getName());
            stmt.setString(9, patient.getContact());
            stmt.setString(10, patient.getEmail());
            stmt.setInt(11, patient.getAge());
            int changedRows = stmt.executeUpdate();
            return (changedRows == 1);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
        return false;
    }

    @Override
    public String generatePatientSerialNo() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String patientSerialNo = null;
        try{
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(Helper.GET_PATIENT_COUNT);

            int patientCount = 0;

            if(rs.next()){
                patientCount = Integer.parseInt(rs.getString("PATIENT_COUNT"));
            }


            String patientSerialNoPrepStr = Helper.PATIENT_SERIAL_NO_PREP;

            if(patientCount == 0){
                patientSerialNo = patientSerialNoPrepStr  + 1;
            }
            else{
                patientSerialNo = patientSerialNoPrepStr  + patientCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientSerialNo;
    }

    @Override
    public Patient getPatient(String patientIdOrSerialNo) {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        Patient patient = null;
        try{
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(Helper.GET_PATIENT_DETAILS);


            if(rs.next()){
                patient = new Patient();
                patient.setId(rs.getString("id"));
                patient.setSerialNo(rs.getString("id"));
                patient.setFirstName(rs.getString(""));
                patient.setLastName(rs.getString(""));
                patient.setDistrict(rs.getString(""));
                patient.setLocationX(rs.getInt(""));
                patient.setLocationY(rs.getInt(""));
                patient.setSeverityLevel(SeverityLevel.valueOf(rs.getString("")));
                patient.setGender(Gender.valueOf(rs.getString("")));
                patient.setContact(rs.getString(""));
                patient.setEmail(rs.getString(""));
                patient.setAge(rs.getInt(""));
                patient.setAdmitDate(rs.getDate(""));
                patient.setAdmittedBy(rs.getString(""));
                patient.setDischargeDate(rs.getDate(""));
                patient.setDischargedBy(rs.getString(""));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patient;
    }
}
