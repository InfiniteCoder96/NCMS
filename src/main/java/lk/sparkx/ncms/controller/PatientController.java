package lk.sparkx.ncms.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lk.sparkx.ncms.model.Gender;
import lk.sparkx.ncms.model.Patient;
import lk.sparkx.ncms.model.SeverityLevel;
import lk.sparkx.ncms.payload.ApiResponse;
import lk.sparkx.ncms.service.PatientService;
import lk.sparkx.ncms.utils.JsonConverter;
import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "Patient")
public class PatientController extends HttpServlet {

    private final PatientService patientService = new PatientService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String Command = request.getParameter("command");

            switch(Command){
                case "REGISTER_PATIENT":
                   registerPatient(request,response);
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
                case "GET_PATIENT":
                    getPatient(request,response);
                    break;
                case "GET_ALL_ACTIVE_PATIENTS":
                    getAllActivePatients(request,response);
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllActivePatients(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientList = new Gson().toJson(patientService.getAllPatients());
        sendResponse(patientList, response);
    }

    private void getPatient(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String patientIdOrSerialNo = request.getParameter("patientId");
        String responseString = new Gson().toJson(patientService.getPatient(patientIdOrSerialNo));
        sendResponse(responseString, response);

    }

    private void registerPatient(HttpServletRequest request, HttpServletResponse response) {
        try {
            String patientFName = request.getParameter("patientFName");
            String patientLName = request.getParameter("patientLName");
            String district = request.getParameter("district");
            String locationX = request.getParameter("locationX");
            String locationY = request.getParameter("locationY");
            String severityLevel = request.getParameter("severitylevel");
            String gender = request.getParameter("gender");
            String contact = request.getParameter("contact");
            String email = request.getParameter("email");
            String age = request.getParameter("age");
            //String admitDateStr = request.getParameter("admitDate");
            //String admittedBy = request.getParameter("admittedBy");
            //String dischargeDateStr = request.getParameter("dischargeDate");
            //String dischargeBy = request.getParameter("dischargeBy");

            /*java.util.Date _admitDate = new SimpleDateFormat("yyyy-MM-dd").parse(admitDateStr);
            java.sql.Date admitDate = new java.sql.Date(_admitDate.getTime());

            java.util.Date _dischargeDate = new SimpleDateFormat("yyyy-MM-dd").parse(dischargeDateStr);
            java.sql.Date dischargeDate = new java.sql.Date(_dischargeDate.getTime());*/

            Patient patient = new Patient();
            patient.setFirstName(patientFName);
            patient.setLastName(patientLName);
            patient.setDistrict(district);
            patient.setLocationX(Integer.parseInt(locationX));
            patient.setLocationY(Integer.parseInt(locationY));
            patient.setSeverityLevel(SeverityLevel.valueOf(severityLevel));
            patient.setGender(Gender.valueOf(gender));
            patient.setContact(contact);
            patient.setEmail(email);
            patient.setAge(Integer.parseInt(age));

            boolean status = patientService.registerPatient(patient);

            String resp;

            if(status){
                resp = "Patient registered successfully";
            }
            else{
                resp = "Something went wrong";
            }

            sendResponse(resp, response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(String data, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        JsonObject json = new JsonObject();
        json.addProperty("Data", data);
        writer.print(json.toString());
        writer.flush();
    }

}
