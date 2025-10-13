import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MedicalReminderDB", "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                // Login success
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("home.html");
            } else {
                // Login failed
                response.sendRedirect("login.html?error=Invalid Credentials");
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}