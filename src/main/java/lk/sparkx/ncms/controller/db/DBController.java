package lk.sparkx.ncms.controller.db;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DBMigration")
public class DBController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        DBHelper dbHelper = new DBHelper();
        List<String> statusList = new ArrayList<>();
        statusList.add(dbHelper.createDB() ? "Db created successfully" : "Db already exists");
        statusList.add(dbHelper.createHospitalsTable() ? "Hospital table created successfully" : "Hospital table already exists");
        statusList.add(dbHelper.createDoctorsTable() ? "Doctors table  created successfully" : "Doctors table already exists");
        statusList.add(dbHelper.createPatientsTable() ? "Patients table  created successfully" : "Patients table already exists");
        statusList.add(dbHelper.createHospitalBedsTable() ? "Hospital beds table  created successfully" : "Hospital beds table already exists");
        statusList.add(dbHelper.createPatientQueueTable() ? "Patient queue table  created successfully" : "Patient queue table already exists");
        statusList.add(dbHelper.createUserTable() ? "users table  created successfully" : "users table already exists");

        this.sendResponse(statusList, resp);
    }

    /**
     * Send response using JsonObject. Best to use when the response contains 1-2 parameters
     *
     * @param data
     * @param resp
     * @throws IOException
     */
    private void sendResponse(List<String> data, HttpServletResponse resp) throws IOException
    {
        String responseString = new Gson().toJson(data);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.print(responseString);
        writer.flush();
    }
}
