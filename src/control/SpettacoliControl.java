package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import model.AccessBean;
import model.AccessModel;
import model.Cart;
import model.CinemaBean;
import model.CinemaModel;
import model.FilmBean;
import model.LocalsModel;
import model.ShowBean;
import model.ShowModel;
import model.UserBean;
import model.UserModel;


@WebServlet({ "/SpettacoliControl", "/user/SpettacoliControl", "/admin/SpettacoliControl" })
public class SpettacoliControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static ShowModel model = new ShowModel();
	static LocalsModel modelLocals = new LocalsModel();
	static AccessModel modelAccess = new AccessModel();
	static UserModel modelUser = new UserModel();
	
    public SpettacoliControl() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession sessione = request.getSession();
		Cart<ShowBean> cart = (Cart<ShowBean>)sessione.getAttribute("carrello");
		if(cart == null) {
			cart = new Cart<ShowBean>();
			sessione.setAttribute("carrello", cart);
		}
		
		String cinema = request.getParameter("cinema");
		String action = request.getParameter("action");
		
		
		String uri = request.getRequestURI();
		ServletContext contesto = getServletContext();
		
			
			if(uri.contains("/admin/")) {
				try {
					if(action.equals("recoveryShow")) {
						String titolo_scelto = request.getParameter("title");
						Collection<ShowBean> spettacoli = (Collection<ShowBean>) contesto.getAttribute("spettacoli");
						JSONObject json = new JSONObject();
						JSONObject arrShow = new JSONObject();
						
						if(spettacoli != null) {
							try {
								for(ShowBean sh: spettacoli) {
									if(sh.getTitleFilm().equals(titolo_scelto)) {
										JSONObject show = new JSONObject();
										show.put("id", sh.getShowId());
										show.put("titolo", sh.getTitleFilm());
										show.put("data", sh.getDate());
										show.put("ora", sh.getHour());
										show.put("sala", sh.getHall());
										show.put("prezzo", sh.getPrice());
										
										arrShow.append("shows", show);
									}
								}
							json.put("info", arrShow);
							response.getWriter().print(json.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
					else if(action.equals("recoveryfordelete")) {
						response.setContentType("application/json");
						CinemaBean cinem = (CinemaBean) contesto.getAttribute("Cinema");
						Collection<ShowBean> listaSpettacoli = model.recuperaDatiDaAttributo(cinem.getAddress());
						JSONObject arrSpett = new JSONObject();
						JSONObject json = new JSONObject();
						
						try {
							for(ShowBean s: listaSpettacoli) {
								JSONObject spett = new JSONObject();
								spett.put("id", s.getShowId());
								spett.put("titolo", s.getTitleFilm());
								spett.put("data", s.getDate());
								spett.put("sala", s.getHall());
								spett.put("indirizzo", s.getAddress());
								spett.put("ora", s.getHour());
								spett.put("prezzo", s.getPrice());
								arrSpett.append("spettacoli", spett);
								
							}
							json.put("info", arrSpett);
							System.out.println("json= "+json);
							response.getWriter().print(json.toString());
						}catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
					else if(action.equals("insert")) {
						String id = request.getParameter("id_spettacolo");
						String titolo = request.getParameter("titolo_film");
						String data = request.getParameter("data");
						String indirizzo = request.getParameter("indirizzo");
						String ora = request.getParameter("ora");
						int sala = Integer.parseInt(request.getParameter("sala"));
						double prezzo = Double.parseDouble(request.getParameter("prezzo"));
						
						ShowBean spettacolo = new ShowBean();
						spettacolo.setShowId(id);
						spettacolo.setTitleFilm(titolo);
						spettacolo.setDate(data);
						spettacolo.setAddress(indirizzo);
						spettacolo.setHour(ora);
						spettacolo.setHall(sala);
						spettacolo.setPrice(prezzo);
						
						model.Salva(spettacolo);
						request.setAttribute("aggiorna", "aggiornato");
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/Spettacoli.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("delete")) {
						String id = request.getParameter("listShow");
						model.Elimina(id);
						
						request.setAttribute("aggiorna", "aggiornato");
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/Spettacoli.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("update")) {
						String oldIdSpettacolo = request.getParameter("id_old_spettacolo");
						ShowBean s = model.recuperaDatiDaChiave(oldIdSpettacolo);
						
						String idspettacolo = request.getParameter("id_spettacolo");
						if(idspettacolo == null || idspettacolo.equals("")) {
							idspettacolo = s.getShowId();
						}
						String titolo = request.getParameter("titolo_film");
						if(titolo == null || titolo.equals("")) {
							titolo = s.getTitleFilm();
						}
						String data = request.getParameter("data");
						if(data == null || data.equals("")) {
							data = s.getDate();
						}
						String indirizzo = request.getParameter("indirizzo");
						if(indirizzo == null || indirizzo.equals("")) {
							indirizzo = s.getAddress();
						}
						String ora = request.getParameter("ora");
						if(ora == null || ora.equals("")) {
							ora = s.getHour();
						}
						int sala;
						 String hall = request.getParameter("sala");
						 if(hall == null || hall.equals("")) {
								sala = s.getHall();
						}
						 else { sala = Integer.parseInt(hall); }
						 
						 double prezzo;
						String price = request.getParameter("prezzo");
						if(price == null || price.equals("")) {
							prezzo = s.getPrice();
						}
						else { prezzo =  Double.parseDouble(price); }
						
						ShowBean spettacolo = new ShowBean();
						spettacolo.setShowId(idspettacolo);
						spettacolo.setTitleFilm(titolo);
						spettacolo.setDate(data);
						spettacolo.setAddress(indirizzo);
						spettacolo.setHour(ora);
						spettacolo.setHall(sala);
						spettacolo.setPrice(prezzo);
						
						model.AggiornaConChiave(spettacolo, oldIdSpettacolo);
						request.setAttribute("aggiorna", "aggiornato");
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/Spettacoli.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("aggiorna")) {
						String indirizzo = request.getParameter("indirizzo");
						contesto.removeAttribute("spettacoli");
						contesto.setAttribute("spettacoli", model.recuperaDatiDaAttributo(indirizzo));
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/Spettacoli.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("recoveryAll")) {
					
						Collection<ShowBean> spettacoli = model.recuperaDatiDaAttributo(cinema);
						
						contesto.removeAttribute("spettacoli");
						contesto.setAttribute("spettacoli", spettacoli);
						CinemaBean cin = modelLocals.recuperaDatiDaChiave(cinema);
						contesto.removeAttribute("Cinema");
						contesto.setAttribute("Cinema", cin);
						
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/Spettacoli.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("prenota")) {
						String spettacolo = request.getParameter("spettacolo");
						ShowBean spett_sel = model.recuperaDatiDaChiave(spettacolo);
						JSONObject json = new JSONObject();
						
						cart.addItem(spett_sel);
						request.setAttribute("cart", cart);
						
						try {
							json.put("messaggio", "Inserito in carrello il seguente spettacolo:<br>"
														  +"Titolo: "+spett_sel.getTitleFilm()+"<br>"
														  +"data: "+spett_sel.getDate()+"<br>"
														  +"ora: "+spett_sel.getHour()+"<br>"
														  +"sala: "+spett_sel.getHall()+"<br>"
														  +"prezzo"+spett_sel.getPrice()+"€");

							response.getWriter().print(json.toString());
						}catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else if(action.equals("deleteTicket")) {
						String id = request.getParameter("id");
						ShowBean spettacolo = model.recuperaDatiDaChiave(id);
						
						cart.deleteItem(spettacolo);
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					
					else if(action.equals("confirm")) {
						UserBean admin = (UserBean) sessione.getAttribute("utente");
						int nAccess = admin.getAccessNumber();
						
						List<ShowBean> carrello = cart.getItems();
						for(ShowBean sh : carrello) {
							AccessBean accesso = new AccessBean();
							accesso.setEmail(admin.getEmail());
							accesso.setId_spettacolo(sh.getShowId());
							accesso.setM_accessi("prenotato");
							modelAccess.Salva(accesso);
							nAccess++;		
						}
						admin.setAccessNumber(nAccess);
						modelUser.Aggiorna(admin);
						cart.deleteItems();
						request.setAttribute("cart", cart);
						request.setAttribute("ok", "prenotazione effettuata");
						request.setAttribute("aggiorna", "ok!");
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("Area")) {
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("recoveryPrenotazioni")) {
						UserBean admin = (UserBean)sessione.getAttribute("utente");
						
						Collection<AccessBean> prenotati = modelAccess.recuperaComponenti("");
						Collection<ShowBean> spettPrenotati = new ArrayList<>();
						LocalDateTime currentTime = LocalDateTime.now();
						LocalDate data = currentTime.toLocalDate();
						
						for(AccessBean ab: prenotati) {
							if(ab.getEmail().equals(admin.getEmail())) {
								ShowBean sh = model.recuperaDatiDaChiave(ab.getId_spettacolo());
								LocalDate data_sh = LocalDate.parse(sh.getDate());
								if(data_sh.compareTo(data) > 0) {
									spettPrenotati.add(sh);
								}
							}
						}
						sessione.setAttribute("prenotati", spettPrenotati);
						
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("deletePrenotazione")) {
						String idSpettacolo = request.getParameter("id");
						UserBean admin = (UserBean) sessione.getAttribute("utente");
						int nAccessi = admin.getAccessNumber();
						
						modelAccess.Elimina(idSpettacolo);
						nAccessi--;
						admin.setAccessNumber(nAccessi);
						modelUser.Aggiorna(admin);
						request.setAttribute("aggiorna", "ok!");
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					
				} catch (SQLException e) {
					System.out.println("Error: "+e.getMessage());
				}
				
			}
			else if(uri.contains("/user/")) {
				try {
					if(action.equals("recoveryShow")) {
						String titolo_scelto = request.getParameter("title");
						Collection<ShowBean> spettacoli = (Collection<ShowBean>) contesto.getAttribute("spettacoli");
						JSONObject json = new JSONObject();
						JSONObject arrShow = new JSONObject();
						
						if(spettacoli != null) {
							try {
								for(ShowBean sh: spettacoli) {
									if(sh.getTitleFilm().equals(titolo_scelto)) {
										JSONObject show = new JSONObject();
										show.put("id", sh.getShowId());
										show.put("titolo", sh.getTitleFilm());
										show.put("data", sh.getDate());
										show.put("ora", sh.getHour());
										show.put("sala", sh.getHall());
										show.put("prezzo", sh.getPrice());
										
										arrShow.append("shows", show);
									}
								}
							json.put("info", arrShow);
							response.getWriter().print(json.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
					else if(action.equals("recoveryAll")) {
					
						Collection<ShowBean> spettacoli = model.recuperaDatiDaAttributo(cinema);
						
						contesto.removeAttribute("spettacoli");
						contesto.setAttribute("spettacoli", spettacoli);
						CinemaBean cin = modelLocals.recuperaDatiDaChiave(cinema);
						contesto.removeAttribute("Cinema");
						contesto.setAttribute("Cinema", cin);
						
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/Spettacoli.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("prenota")) {
						String spettacolo = request.getParameter("spettacolo");
						ShowBean spett_sel = model.recuperaDatiDaChiave(spettacolo);
						JSONObject json = new JSONObject();
						
						cart.addItem(spett_sel);
						request.setAttribute("cart", cart);
						
						try {
							json.put("messaggio", "Inserito in carrello il seguente spettacolo:<br>"
														  +"Titolo: "+spett_sel.getTitleFilm()+"<br>"
														  +"data: "+spett_sel.getDate()+"<br>"
														  +"ora: "+spett_sel.getHour()+"<br>"
														  +"sala: "+spett_sel.getHall()+"<br>"
														  +"prezzo"+spett_sel.getPrice()+"€");

							response.getWriter().print(json.toString());
						}catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else if(action.equals("delete")) {
						String id = request.getParameter("id");
						ShowBean spettacolo = model.recuperaDatiDaChiave(id);
						
						cart.deleteItem(spettacolo);
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					
					else if(action.equals("confirm")) {
						UserBean utente = (UserBean) sessione.getAttribute("utente");
						int nAccess = utente.getAccessNumber();
						
						List<ShowBean> carrello = cart.getItems();
						for(ShowBean sh : carrello) {
							AccessBean accesso = new AccessBean();
							accesso.setEmail(utente.getEmail());
							accesso.setId_spettacolo(sh.getShowId());
							accesso.setM_accessi("prenotato");
							modelAccess.Salva(accesso);
							nAccess++;		
						}
						utente.setAccessNumber(nAccess);
						modelUser.Aggiorna(utente);
						cart.deleteItems();
						request.setAttribute("cart", cart);
						request.setAttribute("ok", "prenotazione effettuata");
						request.setAttribute("aggiorna", "ok!");
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("Area")) {
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("recoveryPrenotazioni")) {
						UserBean utente = (UserBean)sessione.getAttribute("utente");
						
						Collection<AccessBean> prenotati = modelAccess.recuperaComponenti("");
						Collection<ShowBean> spettPrenotati = new ArrayList<>();
						LocalDateTime currentTime = LocalDateTime.now();
						LocalDate data = currentTime.toLocalDate();
						
						for(AccessBean ab: prenotati) {
							if(ab.getEmail().equals(utente.getEmail())) {
								ShowBean sh = model.recuperaDatiDaChiave(ab.getId_spettacolo());
								LocalDate data_sh = LocalDate.parse(sh.getDate());
								if(data_sh.compareTo(data) > 0) {
									spettPrenotati.add(sh);
								}
							}
						}
						sessione.setAttribute("prenotati", spettPrenotati);
						
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					else if(action.equals("deletePrenotazione")) {
						String idSpettacolo = request.getParameter("id");
						UserBean utente = (UserBean) sessione.getAttribute("utente");
						int nAccessi = utente.getAccessNumber();
						
						modelAccess.Elimina(idSpettacolo);
						nAccessi--;
						utente.setAccessNumber(nAccessi);
						modelUser.Aggiorna(utente);
						request.setAttribute("aggiorna", "ok!");
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/AreaPersonale.jsp");
						dispatcher.forward(request, response);
					}
					
				} catch (SQLException e) {
					System.out.println("Error: "+e.getMessage());
				}
				
			}
			else {
				try {
					if(action.equals("recoveryShow")) {
						String titolo_scelto = request.getParameter("title");
						Collection<ShowBean> spettacoli = (Collection<ShowBean>) contesto.getAttribute("spettacoli");
						JSONObject json = new JSONObject();
						JSONObject arrShow = new JSONObject();
						
						if(spettacoli != null) {
							try {
								for(ShowBean sh: spettacoli) {
									if(sh.getTitleFilm().equals(titolo_scelto)) {
										JSONObject show = new JSONObject();
										show.put("titolo", sh.getTitleFilm());
										show.put("data", sh.getDate());
										show.put("ora", sh.getHour());
										show.put("sala", sh.getHall());
										show.put("prezzo", sh.getPrice());
										
										arrShow.append("shows", show);
									}
								}
							json.put("info", arrShow);
							response.getWriter().print(json.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
					else if(action.equals("recoveryAll")) {
					
						Collection<ShowBean> spettacoli = model.recuperaDatiDaAttributo(cinema);
						
						contesto.removeAttribute("spettacoli");
						contesto.setAttribute("spettacoli", spettacoli);
						CinemaBean cin = modelLocals.recuperaDatiDaChiave(cinema);
						contesto.removeAttribute("Cinema");
						contesto.setAttribute("Cinema", cin);
						
						RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/Spettacoli.jsp");
						dispatcher.forward(request, response);
					}
					
				} catch (SQLException e) {
					System.out.println("Error: "+e.getMessage());
				}
				
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
