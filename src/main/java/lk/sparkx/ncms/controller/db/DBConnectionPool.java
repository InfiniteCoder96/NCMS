package lk.sparkx.ncms.controller.db;

import com.google.gson.JsonObject;
import lk.sparkx.ncms.payload.ApiResponse;
import lk.sparkx.ncms.utils.Helper;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by: thisum
 * Date      : 2020-08-27
 * Time      : 23:56
 **/

public class DBConnectionPool
{
    private static DBConnectionPool instance;
    private BasicDataSource basicDataSource;

    private DBConnectionPool()
    {
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl(Helper.DB_URL + Helper.DATABASE);
        basicDataSource.setUsername(Helper.USERNAME);
        basicDataSource.setPassword(Helper.PASSWORD);
        basicDataSource.setMinIdle(Helper.MIN_IDLE);
        basicDataSource.setMaxIdle(Helper.MAX_IDLE);
        basicDataSource.setMaxTotal(Helper.MAX_TOTAL);
    }

    public static DBConnectionPool getInstance()
    {
        if(instance == null)
        {
            instance = new DBConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException
    {
        return this.basicDataSource.getConnection();
    }

    public void close(AutoCloseable closeable)
    {
        try
        {
            if(closeable != null)
            {
                closeable.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
