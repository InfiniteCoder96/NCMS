package lk.sparkx.ncms.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManagementController extends HttpServlet {

    StatisticsController statisticsController = new StatisticsController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String Command = request.getParameter("command");

            switch(Command){
                case "CHECK_PATIENT_QUEUE_STATUS":
                    checkPatientQueueStatus(request,response);
                    break;
                case "GET_LOCATION_FOR_NEW_HOSPITAL":
                    getLocationForNewHospital(request,response);
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String Command = request.getParameter("command");

            switch(Command){
                case "BUILD_NEW_HOSPITAL":
                    buildNewHospital(request,response);
                    break;
                case "GET_PATIENT_STATISTICS":
                    statisticsController.getPatientStatistics(request,response);
                    break;
                case "GET_HOSPITAL_STATISTICS":
                    statisticsController.getHospitalAndBedsStatistics(request,response);
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void buildNewHospital(HttpServletRequest request, HttpServletResponse response) {
    }

    private void getLocationForNewHospital(HttpServletRequest request, HttpServletResponse response) {
    }

    private void checkPatientQueueStatus(HttpServletRequest request, HttpServletResponse response) {
    }
}
