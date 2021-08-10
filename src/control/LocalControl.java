package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.CinemaBean;
import model.FilmBean;
import model.LocalsModel;


@WebServlet({ "/LocalControl", "/admin/LocalControl", "/user/LocalControl" })
public class LocalControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static LocalsModel model = new LocalsModel();
	
    public LocalControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String country = request.getParameter("country");
		ArrayList<ArrayList<CinemaBean>> province = new ArrayList<>();
		String uri = request.getRequestURI();
		String action = request.getParameter("action");
		
		if(uri.contains("/admin/")) {
			
			try {
				if(action.equals("search")) {
					response.setContentType("application/json");
					
					String key = request.getParameter("key");
					Collection<CinemaBean> allCinema = model.recuperaComponenti("");
					JSONObject json = new JSONObject();
					JSONObject arrCinema = new JSONObject();
					
					if(allCinema != null && !key.equals("")) {
						try {
							for(CinemaBean c: allCinema) {
								if(c.getName().toLowerCase().contains(key.toLowerCase())) {
									JSONObject cinema = new JSONObject();
									cinema.put("indirizzo", c.getAddress());
									cinema.put("nome", c.getName());
									arrCinema.append("cinema", cinema);
								}
							}
							json.put("info", arrCinema);
							response.getWriter().print(json.toString());
						}catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
				else if(action.equals("recoveryfordelete")) {
					response.setContentType("application/json");
					Collection<CinemaBean> listaCinema = model.recuperaComponenti("");
					JSONObject arrCinema = new JSONObject();
					JSONObject json = new JSONObject();
					
					try {
						for(CinemaBean c: listaCinema) {
							JSONObject cinema = new JSONObject();
							cinema.put("indirizzo", c.getAddress());
							cinema.put("nome", c.getName());
							arrCinema.append("cinema", cinema);
							
						}
						json.put("info", arrCinema);
						System.out.println("json= "+json);
						response.getWriter().print(json.toString());
					}catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
				else if(action.equals("recovery")) {
					response.setContentType("application/json");
					
					Collection<CinemaBean> allCinema = model.recuperaComponenti("");
					ArrayList<CinemaBean> selectForCountry = selezionaPerRegione(allCinema,country);
					province = selezionaPerCitta(selectForCountry);
					
					JSONObject json = new JSONObject();
					JSONArray arrProv = new JSONArray();
					
					try {
						for(ArrayList<CinemaBean> prov: province) {
							arrProv.put(prov);
						}
						json.put("info", arrProv);
						response.getWriter().print(json.toString());
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				else if(action.equals("insert")) {
					String citta = request.getParameter("citta");
					String indirizzo = request.getParameter("indirizzo");
					String nome = request.getParameter("nome");
					String regione = request.getParameter("regione");
					String mappa = request.getParameter("mappa");
					String provincia = request.getParameter("provincia");
					
					CinemaBean cinema = new CinemaBean();
					cinema.setCity(citta);
					cinema.setAddress(indirizzo);
					cinema.setName(nome);
					cinema.setCountry(regione);
					cinema.setMaps(mappa);
					cinema.setProvince(provincia);
					
					model.Salva(cinema);
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/locals.jsp");
					dispatcher.forward(request, response);
				
				}
				else if(action.equals("delete")) {
					String indirizzo = request.getParameter("listCinema");
					model.Elimina(indirizzo);
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/locals.jsp");
					dispatcher.forward(request, response);
				}
				else if(action.equals("update")) {
					String oldIndirizzo = request.getParameter("address_old_cinema");
					CinemaBean c = model.recuperaDatiDaChiave(oldIndirizzo);
					
					String indirizzo = request.getParameter("indirizzo");
					if(indirizzo == null || indirizzo.equals("")) {
						indirizzo = c.getAddress();
					}
					String citta = request.getParameter("citta");
					if(citta == null || citta.equals("")) {
						citta = c.getCity();
					}
					String nome = request.getParameter("nome");
					if(nome == null || nome.equals("")) {
						nome = c.getName();
					}
					String regione = request.getParameter("regione");
					if(regione == null || regione.equals("")) {
						regione = c.getCountry();
					}
					String mappa = request.getParameter("mappa");
					if(mappa == null || mappa.equals("")) {
						mappa = c.getMaps();
					}
					String provincia = request.getParameter("provincia");
					if(provincia == null || provincia.equals("")) {
						provincia = c.getProvince();
					}
					
					
					CinemaBean cinema = new CinemaBean();
					cinema.setAddress(indirizzo);
					cinema.setCity(citta);
					cinema.setName(nome);
					cinema.setCountry(regione);
					cinema.setMaps(mappa);
					cinema.setProvince(provincia);
					
					model.AggiornaConChiave(cinema, oldIndirizzo);
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/locals.jsp");
					dispatcher.forward(request, response);
				}
				
			} catch (SQLException e) {
				System.out.println("Error: "+e.getMessage());
			}
			
			
			
		}
		else if(uri.contains("/user/")) {
			try {
				if(action.equals("search")) {
					response.setContentType("application/json");
					
					String key = request.getParameter("key");
					Collection<CinemaBean> allCinema = model.recuperaComponenti("");
					JSONObject json = new JSONObject();
					JSONObject arrCinema = new JSONObject();
					
					if(allCinema != null && !key.equals("")) {
						try {
							for(CinemaBean c: allCinema) {
								if(c.getName().toLowerCase().contains(key.toLowerCase())) {
									JSONObject cinema = new JSONObject();
									cinema.put("indirizzo", c.getAddress());
									cinema.put("nome", c.getName());
									arrCinema.append("cinema", cinema);
								}
							}
							json.put("info", arrCinema);
							response.getWriter().print(json.toString());
						}catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
				else if(action.equals("recovery")) {
					response.setContentType("application/json");
					
					Collection<CinemaBean> allCinema = model.recuperaComponenti("");
					ArrayList<CinemaBean> selectForCountry = selezionaPerRegione(allCinema,country);
					province = selezionaPerCitta(selectForCountry);
					
					JSONObject json = new JSONObject();
					JSONArray arrProv = new JSONArray();
					
					try {
						for(ArrayList<CinemaBean> prov: province) {
							arrProv.put(prov);
						}
						json.put("info", arrProv);
						response.getWriter().print(json.toString());
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
			} catch (SQLException e) {
				System.out.println("Error: "+e.getMessage());
			}
			
		}
		else {
			try {
				if(action.equals("search")) {
					response.setContentType("application/json");
					
					String key = request.getParameter("key");
					Collection<CinemaBean> allCinema = model.recuperaComponenti("");
					JSONObject json = new JSONObject();
					JSONObject arrCinema = new JSONObject();
					
					if(allCinema != null && !key.equals("")) {
						try {
							for(CinemaBean c: allCinema) {
								if(c.getName().toLowerCase().contains(key.toLowerCase())) {
									JSONObject cinema = new JSONObject();
									cinema.put("indirizzo", c.getAddress());
									cinema.put("nome", c.getName());
									arrCinema.append("cinema", cinema);
								}
							}
							json.put("info", arrCinema);
							System.out.println("json serach: "+json);
							response.getWriter().print(json.toString());
						}catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
				else if(action.equals("recovery")) {
					response.setContentType("application/json");
					
					Collection<CinemaBean> allCinema = model.recuperaComponenti("");
					ArrayList<CinemaBean> selectForCountry = selezionaPerRegione(allCinema,country);
					province = selezionaPerCitta(selectForCountry);
					
					JSONObject json = new JSONObject();
					JSONArray arrProv = new JSONArray();
					
					try {
						for(ArrayList<CinemaBean> prov: province) {
							arrProv.put(prov);
						}
						json.put("info", arrProv);
						response.getWriter().print(json.toString());
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
			} catch (SQLException e) {
				System.out.println("Error: "+e.getMessage());
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	
	
	
	
	private ArrayList<CinemaBean> selezionaPerRegione(Collection<CinemaBean> coll, String country){
		
		Iterator<CinemaBean> itLocal = coll.iterator();
		ArrayList<CinemaBean> selectLocals = new ArrayList<CinemaBean>();

		while(itLocal.hasNext()) { 
			CinemaBean locbean = itLocal.next();
			if(locbean.getCountry().equals(country)) {
				selectLocals.add(locbean);
			}
		}
		return selectLocals;
	}
	
	private ArrayList<ArrayList<CinemaBean>> selezionaPerCitta(ArrayList<CinemaBean>cinemaPerRegione){
		
		ArrayList<ArrayList<CinemaBean>> province = new ArrayList<>();
		System.out.println(cinemaPerRegione);
		
		while(!cinemaPerRegione.isEmpty()) {
			ArrayList<CinemaBean> perProvince = new ArrayList<>();
			perProvince.add(cinemaPerRegione.get(0));
			cinemaPerRegione.remove(0);
			for(int i=0; i<cinemaPerRegione.size(); i++) {
				if(cinemaPerRegione.get(i).getCity().equals(perProvince.get(0).getCity())) {
					perProvince.add(cinemaPerRegione.get(i));
					cinemaPerRegione.remove(cinemaPerRegione.get(i));
				}
			}
			province.add(perProvince);
		}
		return province;
	}

}
