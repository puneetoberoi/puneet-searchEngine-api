package com.company;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;

public class SecondDBUpdate {

    public SecondDBUpdate() {
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from crawler");) {
            System.out.println("From Second Update");
            int count = 0;
            while (rs.next()) {
                String ur1 = rs.getString("url");

                try {
                    URL url = new URL(ur1);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    int respondcode = huc.getResponseCode();
                    if (respondcode == 200 || respondcode == 302 || respondcode == 301) {
                        String query = "replace into accepted (url) values (?)";
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, ur1);
                        pstmt.executeUpdate();
                        //System.out.println("Unique table filled " + ++count + " "+ respondcode + " " + ur1);
                        huc.disconnect();
                    }

                } catch (UnknownHostException e) {
                    e.getStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
