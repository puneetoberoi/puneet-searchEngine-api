package com.company;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;

public class SecondDBUpdate {

    public SecondDBUpdate() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "puneet");
            System.out.println("wmk");
            int count = 0;
            Statement stmt = con.createStatement();
            stmt.execute("select * from crawler1");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                String ur1 = rs.getString("name");
                try {
                    URL url = new URL(ur1);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    int respondcode = huc.getResponseCode();

                    //System.out.println(respondcode);


                    if (respondcode == 200 || respondcode == 302 || respondcode == 301) {
                        stmt = con.createStatement();
                        String query = "insert into accepted (url) values (?)";
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, ur1);
                        pstmt.executeUpdate();
                        System.out.println("Unique table filled " + ++count + "");
                        huc.disconnect();
                    }

                } catch (UnknownHostException e) {
                    if (e instanceof UnknownHostException) {
                        stmt = con.createStatement();
                        String query = "delete from crawler1 where name = ?";
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, ur1);
                        pstmt.executeUpdate();
                        System.out.println("The query didn't go through so deleted the record");


                    }
                    e.getStackTrace();
                } catch (SQLException e) {
                    if (e instanceof SQLIntegrityConstraintViolationException) {
                        stmt = con.createStatement();
                        String query = "delete from url where url = ?";
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, ur1);
                        pstmt.executeUpdate();
                        System.out.println("The query didn't go through so deleted the record");


                    }
                }
            }
            stmt.execute("delete from url");
            stmt.execute("delete from crawler1");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (final Exception | Error ignored) {
            System.out.println("wmk");
        }
    }

}
