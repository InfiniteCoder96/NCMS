package lk.sparkx.ncms.service;

import lk.sparkx.ncms.controller.db.DBConnectionPool;
import lk.sparkx.ncms.model.Hospital;
import lk.sparkx.ncms.utils.Helper;

import java.sql.*;
import java.util.*;

public class HospitalService {

    public boolean checkHospitalBedsTableEmpty(){
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        try{
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(Helper.CHECK_TABLE_IS_EMPTY);

            List<String> availableHospitalIds = new ArrayList<>();

            int rows = -1;
            
            if(rs.next()){
               rows = rs.getInt("rows");
            }
            
            return (rows == 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally
        {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
    }

    public List<String> getAvailableHospitals(){
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        try{
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(Helper.GET_AVAILABLE_HOSPITALS);

            List<String> availableHospitalIds = new ArrayList<>();

            if (rs.next()){
                availableHospitalIds.add(rs.getString("hospital_id"));
            }

            return availableHospitalIds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally
        {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
    }

    public Map<Integer,int[]> getHospitalCoordinates(String hospitalId){
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stmt = null;

        Map<Integer, int[]> coordinates = new HashMap<Integer, int[]>();

        try{
            con = DBConnectionPool.getInstance().getConnection();

            stmt = con.prepareStatement(Helper.GET_HOSPITAL_LOCATION);
            stmt.setString(1, hospitalId);

            rs = stmt.executeQuery();

            int[] location = new int[2];

            if(rs.next()){
                int locX = rs.getInt("location_x");
                int locY = rs.getInt("location_y");

                location[0] = locX;
                location[1] = locY;

                coordinates.put(Integer.parseInt(hospitalId), location);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }

        return coordinates;
    }

    public Map<Integer,int[]> getAllHospitalCoordinates() {
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        Map<Integer, int[]> coordinates = new HashMap<Integer, int[]>();
        try{
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Helper.GET_ALL_HOSPITAL_LOCATIONS);

            int[] location = new int[2];

            if(rs.next()){
                int hospitalId = rs.getInt("id");
                int locX = rs.getInt("location_x");
                int locY = rs.getInt("location_y");

                location[0] = locX;
                location[1] = locY;

                coordinates.put(hospitalId, location);
            }
            return coordinates;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally
        {
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
    }

    public boolean addNewHospital(Hospital newHospital) {
        Connection con = null;
        PreparedStatement stmt = null;

        try{
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.prepareStatement(Helper.ADD_NEW_HOSPITAL);

            stmt.setString(1, newHospital.getName());
            stmt.setString(2, newHospital.getDistrict());
            stmt.setInt(3, newHospital.getLocationX());
            stmt.setInt(4, newHospital.getLocationY());
            stmt.setDate(5, newHospital.getBuildDate());

            int changedRows = stmt.executeUpdate();

            return (changedRows == 1);

        } catch (SQLException e) {
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
