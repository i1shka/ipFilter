package com.example.ipFilter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IpDBLoader {
    public static void loadToDB(List<String> resultIpList) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=123")) {
            PreparedStatement insertStmt =
                    conn.prepareStatement("INSERT INTO public.filtered_ip(ip_address) VALUES (?)");
            for (String ip : resultIpList) {
                insertStmt.setString(1, ip);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
