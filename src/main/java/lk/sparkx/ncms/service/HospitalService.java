package lk.sparkx.ncms.service;

import lk.sparkx.ncms.controller.db.DBConnectionPool;
import lk.sparkx.ncms.utils.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HospitalService {

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
        }

        return null;
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

        return coordinates;
    }


}
