package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import model.UserBean;
import model.UserModel;

@WebServlet("/LoginControl")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static UserModel model = new UserModel();
	
    public LoginControl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserBean utente;
		
		HttpSession session = request.getSession();
		String us = request.getParameter("username");
		String psw = request.getParameter("password");
		
		try {
			utente = model.recuperaDatiDaChiave(us);
					
			if(us.equals(utente.getEmail()) && codifica(psw).equals(utente.getPassword())) {
							
				if(utente.isUser()) {
					session.setAttribute("username", us);
					session.setAttribute("utente", utente);
					response.sendRedirect(request.getContextPath()+"/user/home.jsp");
				}
				else if(utente.isAdmin()) {
					session.setAttribute("username", us);
					session.setAttribute("utente", utente);
					response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
				}
			}
			else {
				String chiamante = request.getParameter("chiamante");
				request.setAttribute("error", Boolean.TRUE);
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/"+chiamante);
				dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			System.out.println("Error: "+e.getMessage());
		}	
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	private String codifica(String str) {
		byte[] by = new byte[str.length()];
		for(int i=0;i<str.length();i++) {
			by[i] = (byte) str.charAt(i);
		}
		return Base64.getEncoder().encodeToString(by);
	}

}
