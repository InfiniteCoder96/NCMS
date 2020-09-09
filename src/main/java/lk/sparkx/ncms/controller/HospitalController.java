package lk.sparkx.ncms.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Hospital")
public class HospitalController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String Command = request.getParameter("command");

            switch(Command){
                case "REGISTER_HOSPITAL":
                    addNewHospital(request,response);
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String Command = request.getParameter("command");

            switch(Command){
                case "GET_DOCTOR":
                    getHospital(request,response);
                    break;
                case "GET_ALL_DOCTORS":
                    getAllHospitals(request,response);
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllHospitals(HttpServletRequest request, HttpServletResponse response) {
    }

    private void getHospital(HttpServletRequest request, HttpServletResponse response) {
    }


    private void addNewHospital(HttpServletRequest request, HttpServletResponse response) {
    }

}
