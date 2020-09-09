package lk.sparkx.ncms.service;

import com.google.gson.JsonElement;
import lk.sparkx.ncms.controller.db.DBConnectionPool;
import lk.sparkx.ncms.model.Gender;
import lk.sparkx.ncms.model.Patient;
import lk.sparkx.ncms.model.SeverityLevel;
import lk.sparkx.ncms.repository.PatientRepository;
import lk.sparkx.ncms.utils.Helper;

import java.sql.*;
import java.util.*;

public class PatientService implements PatientRepository {

    HospitalService hospitalService = new HospitalService();

    @Override
    public boolean registerPatient(Patient patient) {
        Connection con = null;
        PreparedStatement stmt = null;
        try
        {
            con = DBConnectionPool.getInstance().getConnection();

            stmt = con.prepareStatement(Helper.INSERT_PATIENT);

            String patientId = generatePatientSerialNo();

            stmt.setString(1, patientId);
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getLastName());
            stmt.setString(4, patient.getDistrict());
            stmt.setInt(5, patient.getLocationX());
            stmt.setInt(6, patient.getLocationY());
            stmt.setString(7, patient.getSeverityLevel().getName());
            stmt.setString(8, patient.getGender().getName());
            stmt.setString(9, patient.getContact());
            stmt.setString(10, patient.getEmail());
            stmt.setInt(11, patient.getAge());

            int changedRows = stmt.executeUpdate();

            int nearestAvailableHospital = this.getNearestAvailableHospital(patient.getLocationX(), patient.getLocationY());

            if(nearestAvailableHospital == 0){
                stmt = con.prepareStatement(Helper.ADD_PATIENT_TO_QUEUE);
                stmt.setString(1, patientId);
                changedRows += stmt.executeUpdate();
            }

            return (changedRows == 1 || changedRows == 2);
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
            int year = Calendar.getInstance().get(Calendar.YEAR);

            if(patientCount == 0){
                patientSerialNo = year + "/" + patientSerialNoPrepStr  + 1;
            }
            else{
                patientSerialNo = year + "/" + patientSerialNoPrepStr  + patientCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientSerialNo;
    }

    @Override
    public Patient getPatient(String patientId) {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stmt = null;
        Patient patient = null;
        try{
            con = DBConnectionPool.getInstance().getConnection();

            stmt = con.prepareStatement(Helper.GET_PATIENT_DETAILS);
            stmt.setString(1, patientId);

            rs = stmt.executeQuery();

            if(rs.next()){
                patient = new Patient();
                patient.setId(rs.getString("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setDistrict(rs.getString("district"));
                patient.setLocationX(rs.getInt("location_x"));
                patient.setLocationY(rs.getInt("location_y"));
                patient.setSeverityLevel(SeverityLevel.valueOf(rs.getString("severity_level")));
                patient.setGender(Gender.valueOf(rs.getString("gender")));
                patient.setContact(rs.getString("contact"));
                patient.setEmail(rs.getString("email"));
                patient.setAge(rs.getInt("age"));
                patient.setAdmitDate(rs.getDate("admit_date"));
                patient.setAdmittedBy(rs.getString("admitted_by"));
                patient.setDischargeDate(rs.getDate("discharge_date"));
                patient.setDischargedBy(rs.getString("discharged_by"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patient;
    }

    public List<Patient> getAllPatients() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        Patient patient = null;
        List<Patient> patientList = new ArrayList<>();
        try{
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(Helper.GET_ALL_ACTIVE_PATIENTS);


            if(rs.next()){
                patient = new Patient();
                patient.setId(rs.getString("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setDistrict(rs.getString("district"));
                patient.setLocationX(rs.getInt("location_x"));
                patient.setLocationY(rs.getInt("location_y"));
                patient.setSeverityLevel(SeverityLevel.valueOf(rs.getString("severity_level")));
                patient.setGender(Gender.valueOf(rs.getString("gender")));
                patient.setContact(rs.getString("contact"));
                patient.setEmail(rs.getString("email"));
                patient.setAge(rs.getInt("age"));
                patient.setAdmitDate(rs.getDate("admit_date"));
                patient.setAdmittedBy(rs.getString("admitted_by"));

                patientList.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientList;
    }

    public int getNearestAvailableHospital(int patientLocX, int patientLocY){

        List<String> availableHospitals = hospitalService.getAvailableHospitals();

        if(availableHospitals.size() != 0){
            Map<Integer, int[]> coordinates = null;

            for (String id: availableHospitals) {
                coordinates = hospitalService.getHospitalCoordinates(id);
            }

            Map<Integer, Double> distances = new HashMap<>();

            assert coordinates != null;
            for (Map.Entry<Integer, int[]> entry: coordinates.entrySet()) {
                distances.put(entry.getKey(), Math.sqrt((entry.getValue()[0]-patientLocX)*(entry.getValue()[0]-patientLocX) + (entry.getValue()[1]-patientLocY)*(entry.getValue()[1]-patientLocY)));
            }

            // Create a list from elements of HashMap
            List<Map.Entry<Integer, Double> > list = new LinkedList<Map.Entry<Integer, Double> >(distances.entrySet());

            // Sort the list
            Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() {
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2)
                {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });

            // put data from sorted list to hashmap
            LinkedHashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
            for (Map.Entry<Integer, Double> aa : list) {
                temp.put(aa.getKey(), aa.getValue());
            }

            Map.Entry<Integer, Double> entry = temp.entrySet().iterator().next();

            return entry.getKey();
        }
        else{
            return 0;
        }

    }
}
