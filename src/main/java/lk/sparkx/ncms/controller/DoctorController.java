package lk.sparkx.ncms.controller;


import lk.sparkx.ncms.model.Doctor;
import lk.sparkx.ncms.service.DoctorService;
import lk.sparkx.ncms.utils.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Doctor")
public class DoctorController extends HttpServlet {

    private DoctorService doctorService = new DoctorService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String Command = request.getParameter("command");

            switch(Command){
                case "REGISTER_DOCTOR":
                    addNewDoctor(request,response);
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
                    getDoctor(request,response);
                    break;
                case "GET_ALL_DOCTORS":
                    getAllDoctors(request,response);
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getDoctor(HttpServletRequest request, HttpServletResponse response) {
    }

    private void getAllDoctors(HttpServletRequest request, HttpServletResponse response) {
    }

    private void addNewDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String resp;
        try{
            String doctorName = request.getParameter("doctor_name");
            String hospitalId = request.getParameter("hospital_id");
            String isDoctor = request.getParameter("is_director");

            Doctor newDoctor = new Doctor();

            newDoctor.setName(doctorName);
            newDoctor.setHospitalId(hospitalId);
            newDoctor.setIsDirector(Integer.parseInt(isDoctor));

            boolean status = doctorService.addNewDoctor(newDoctor);

            if(status){
                resp = "Doctor added successfully";
            }
            else{
                resp = "Something went wrong";
            }
        }
        catch(Exception e){
            resp = "Something went wrong";
            e.printStackTrace();
        }
        Utility.sendResponse(resp, response);
    }
}
