package kapsiki.tomcatlab;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class JDBCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Customers</h1>");
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/classicmodels");
			Connection con = ds.getConnection();
			ResultSet resultats = null;
			String requete = "SELECT customerName FROM customers";
			Statement stmt = con.createStatement();
			resultats = stmt.executeQuery(requete);
			int cpt = 1;
			while (resultats.next()) {
				out.println("(" + cpt + ")" + resultats.getString("customerName") + "<br>");
				cpt++;
			}
			resultats.close();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}
}
