package com.company;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;

public class SecondDBUpdate {

    public SecondDBUpdate() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "");
            System.out.println("From Second Update");
            int count = 0;
            Statement stmt = con.createStatement();
            stmt.execute("select * from crawler");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                String ur1 = rs.getString("name");

                try {
                    URL url = new URL(ur1);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    int respondcode = huc.getResponseCode();
                    if (respondcode == 200 || respondcode == 302 || respondcode == 301) {
                        stmt = con.createStatement();
                        String query = "replace into accepted (url) values (?)";
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, ur1);
                        pstmt.executeUpdate();
                        System.out.println("Unique table filled " + ++count + " "+ respondcode + " " + ur1);
                        huc.disconnect();
                    }

                } catch (UnknownHostException e) {
                    e.getStackTrace();
                } catch (SQLException e) {
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
