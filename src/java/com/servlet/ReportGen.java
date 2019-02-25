package com.servlet;

import DbConnection.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author roman
 */
@WebServlet(name = "ReportGen", urlPatterns = {"/ReportGen"})
public class ReportGen extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        DbConnect dbConnect = new DbConnect();
        Connection con;
        //String sql = "SELECT id, name, district FROM world.city where id <5";
        String sql = "SELECT * FROM req_log";

        try {
            con = dbConnect.getConnection();
            //out.println(con + "\n");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            //*** (Start from here (Json Object)...
            JSONArray jsonArr = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();

            if (rs.equals(null)) {
                JSONObject obj = new JSONObject();
                obj.put("Error", "Data not retrieve from database");
                jsonArr.put(obj);
                //out.print(jsonArr.toString());
            } else {
                while (rs.next()) {
                    int numColumns = rsmd.getColumnCount();
                    JSONObject obj = new JSONObject();
                    for (int i = 1; i <= numColumns; i++) {
                        String column_name = rsmd.getColumnName(i);
                        obj.put(column_name, rs.getObject(column_name));
                    }
                    jsonArr.put(obj);
                }
                //out.print(jsonArr.toString());
            }

            //*** ...Stop (Json Object))
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonArr.toString());
            response.getWriter().flush();

//            while (rs.next()) {
//                out.println(rs.getInt(1) + "  " + rs.getString(2)); //+ "  " + rs.getString(3)
//                out.print("\n");
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out.println(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
