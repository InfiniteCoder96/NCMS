package lk.sparkx.ncms.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Statistics")
public class StatisticsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String Command = request.getParameter("command");

            switch(Command){
                case "GET_PATIENT_STATISTICS":
                    getPatientStatistics(request,response);
                    break;
                case "GET_HOSPITAL_BEDS_STATISTICS":
                    getHospitalAndBedsStatistics(request,response);
                    break;
                default:
                    getPatientStatistics(request,response);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getHospitalAndBedsStatistics(HttpServletRequest request, HttpServletResponse response) {
    }

    public void getPatientStatistics(HttpServletRequest request, HttpServletResponse response) {
    }
}
