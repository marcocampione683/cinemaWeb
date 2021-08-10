package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import model.CinemaBean;
import model.FilmBean;
import model.UserBean;
import model.UserModel;


@WebServlet({"/UserControl", "/user/UserControl", "/admin/UserControl"})
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static UserModel model = new UserModel();
    
    public UserControl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String uri = request.getRequestURI();
		
		if(uri.contains("/admin/")) {
			if(action!=null) {
				if(action.equals("deleteUserFromAdmin")) {
					String email = request.getParameter("listUser");
					try {
						model.Elimina(email);
					} catch (SQLException e) {
						System.out.println("Error:" + e.getMessage());
					}
					
					request.setAttribute("utenteEliminato", "usereliminato");
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/AreaPersonale.jsp");
					dispatcher.forward(request, response);
				}
				else if(action.equals("recoveryForDeleteFromAdmin")) {
					response.setContentType("application/json");
					Collection<UserBean> listaUtenti;
					JSONObject arrUtenti = new JSONObject();
					JSONObject json = new JSONObject();
					try {
						listaUtenti = model.recuperaComponenti("");
						
						try {
							for(UserBean u: listaUtenti) {
								JSONObject user = new JSONObject();
								user.put("nome", u.getName());
								user.put("cognome", u.getSurname());
								user.put("dataNascita", u.getBirth());
								user.put("email", u.getEmail());
								
								arrUtenti.append("utenti", user);
								
							}
							json.put("info", arrUtenti);
							System.out.println("json= "+json);
							response.getWriter().print(json.toString());
						}catch (JSONException e) {
							e.printStackTrace();
						}
						
					} catch (SQLException e1) {
						System.out.println("Error:" + e1.getMessage());
					}
				}
				 else if(action.equals("update")) {
						
						UserBean a = (UserBean) request.getSession().getAttribute("utente");
						String oldEmail = a.getEmail();
						
						String name = request.getParameter("nome");
						if(name == null || name.equals("")) {
							name = a.getName();
						}
						String cognome = request.getParameter("cognome");
						if(cognome == null || cognome.equals("")) {
							cognome = a.getSurname();
						}
						String nascita = request.getParameter("nascita");
						if(nascita == null || nascita.equals("")) {
							nascita = a.getBirth();
						}
						String email = request.getParameter("email");
						if(email == null || email.equals("")) {
							email = a.getEmail();
						}
						String password = request.getParameter("confirmPassword");
						if(password == null || password.equals("")) {
							password = a.getPassword();
						}
						else {
							password = codifica(password);
						}
						
						
						UserBean admin = new UserBean();
						admin.setName(name);
						admin.setSurname(cognome);
						admin.setBirth(nascita);
						admin.setEmail(email);
						admin.setPassword(password);
						
						try {
							model.AggiornaConChiave(admin, oldEmail);
						} catch (SQLException e) {
							System.out.println("Error:" + e.getMessage());
						}
						
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/LogoutControl");
						dispatcher.forward(request, response);
				 }
			}
		}
		else if(uri.contains("/user/")) {
			try {
				if(action != null) {
				 if(action.equals("deleteuser")) {
						String email = request.getParameter("email");
						
						model.Elimina(email);
						request.setAttribute("Registrazione", "delete");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user/ProfileDeleted.jsp");
						dispatcher.forward(request, response);
					}
				 else if(action.equals("update")) {
						
						UserBean u = (UserBean) request.getSession().getAttribute("utente");
						String oldEmail = u.getEmail();
						
						String name = request.getParameter("nome");
						if(name == null || name.equals("")) {
							name = u.getName();
						}
						String cognome = request.getParameter("cognome");
						if(cognome == null || cognome.equals("")) {
							cognome = u.getSurname();
						}
						String nascita = request.getParameter("nascita");
						if(nascita == null || nascita.equals("")) {
							nascita = u.getBirth();
						}
						String email = request.getParameter("email");
						if(email == null || email.equals("")) {
							email = u.getEmail();
						}
						String password = request.getParameter("confirmPassword");
						if(password == null || password.equals("")) {
							password = u.getPassword();
						}
						else {
							password = codifica(password);
						}
						
						UserBean user = new UserBean();
						user.setName(name);
						user.setSurname(cognome);
						user.setBirth(nascita);
						user.setEmail(email);
						user.setPassword(password);
						
						model.AggiornaConChiave(user, oldEmail);
						
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/LogoutControl");
						dispatcher.forward(request, response);
				 }
				}
			}catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}
		}
		else {
			try {
				if(action != null) {
					if(action.equals("newuser")) {
						UserBean nuovoUtente = new UserBean();
						LocalDateTime currentTime = LocalDateTime.now();
						LocalDate data = currentTime.toLocalDate();	
						String psw = request.getParameter("psw-repeat");
						String codPsw = codifica(psw);
						
						nuovoUtente.setName(request.getParameter("nome"));
						nuovoUtente.setSurname(request.getParameter("cognome"));
						nuovoUtente.setBirth(request.getParameter("nascita"));
						nuovoUtente.setEmail(request.getParameter("email"));
						nuovoUtente.setPassword(codPsw);
						nuovoUtente.setRegistration(data.toString());
						nuovoUtente.setRole("utente");
						
						model.Salva(nuovoUtente);
						
						request.setAttribute("Registrazione", "new");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home.jsp");
						dispatcher.forward(request, response);
					}
				}
			}catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}
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
