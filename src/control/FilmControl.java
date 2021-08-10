package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FilmBean;
import model.FilmModel;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(urlPatterns= {"/FilmControl", "/admin/FilmControl", "/user/FilmControl"})
public class FilmControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static FilmModel model = new FilmModel();
	
    public FilmControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String action = request.getParameter("action");
			String uri = request.getRequestURI();
			ServletContext contesto = getServletContext();
				
				if(uri.contains("/admin/")) {
					try {
						if(action.equals("filmView")) {
							String film;
							film = request.getParameter("film");
							request.setAttribute("film", model.recuperaDatiDaChiave(film));
							
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/SchedaFilmView.jsp");
							dispatcher.forward(request, response);
						}
						else if(action.equals("insFilm")) {
							contesto.removeAttribute("films");
							contesto.setAttribute("films", model.recuperaComponenti(""));
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/home.jsp");
							dispatcher.forward(request, response);
						}
						else if(action.equals("search")) {
							response.setContentType("application/json");
							
							String key = request.getParameter("key");
							Collection<FilmBean> allFilm = (Collection<FilmBean>) contesto.getAttribute("films");
							JSONObject json = new JSONObject();
							JSONObject arrFilm = new JSONObject();
							
							if(allFilm!=null && !key.equals("")) {
								try {
									for(FilmBean f: allFilm) {
										if(f.getTitle().toLowerCase().contains(key.toLowerCase())) {
											JSONObject film = new JSONObject();
											film.put("titolo",f.getTitle());
											film.put("locandina", f.getPhoto());
											arrFilm.append("films", film);
										}
									}
									json.put("info", arrFilm);
									response.getWriter().print(json.toString());
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							else {
								try {
									for(FilmBean f: allFilm) {
										JSONObject film = new JSONObject();
										film.put("titolo",f.getTitle());
										film.put("locandina", f.getPhoto());
										arrFilm.append("films", film);
									}
									json.put("info", arrFilm);
								}catch (JSONException e) {
									e.printStackTrace();
								}
								response.getWriter().print(json.toString());
							}	
						}
						else if(action.equals("aggiorna")) {
							contesto.removeAttribute("films");
							contesto.setAttribute("films", model.recuperaComponenti(""));
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/film.jsp");
							dispatcher.forward(request, response);
						}
						else if(action.equals("delete")) {
							String titolo = request.getParameter("titolo_film");
							model.Elimina(titolo);
							request.setAttribute("aggiorna", "eliminato");
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/film.jsp");
							dispatcher.forward(request, response);
						}
						else if(action.equals("insert")) {
							String titolo = request.getParameter("titolo_film");
							String genere = request.getParameter("genere");
							String durata = request.getParameter("durata");
							String regia = request.getParameter("regia");
							String trama = request.getParameter("trama");
							String locandina = request.getParameter("locandina");
							String video = request.getParameter("cod_video");
							
							FilmBean film = new FilmBean();
							film.setTitle(titolo);
							film.setType(genere);
							film.setDuration(durata);
							film.setDirectedBy(regia);
							film.setStory(trama);
							film.setPhoto(locandina);
							film.setVideo(video);
							
							model.Salva(film);
							request.setAttribute("aggiorna", "aggiornato");
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/film.jsp");
							dispatcher.forward(request, response);
							
						}
						else if(action.equals("update")) {
							String oldTitolo = request.getParameter("titolo_old_film");
							FilmBean f = model.recuperaDatiDaChiave(oldTitolo);
							
							String titolo = request.getParameter("titolo_film");
							if(titolo == null || titolo.equals("")) {
								titolo = f.getTitle();
							}
							String genere = request.getParameter("genere");
							if(genere == null || genere.equals("")) {
								genere = f.getType();
							}
							String durata = request.getParameter("durata");
							if(durata == null || durata.equals("")) {
								durata = f.getDuration();
							}
							String regia = request.getParameter("regia");
							if(regia == null || regia.equals("")) {
								regia = f.getDirectedBy();
							}
							String trama = request.getParameter("trama");
							if(trama == null || trama.equals("")) {
								trama = f.getStory();
							}
							String locandina = request.getParameter("locandina");
							if(locandina == null || locandina.equals("")) {
								locandina = f.getPhoto();
							}
							String video = request.getParameter("cod_video");
							if(video == null || video.equals("")) {
								video = f.getVideo();
							}
							
							
							
							FilmBean film = new FilmBean();
							film.setTitle(titolo);
							film.setType(genere);
							film.setDuration(durata);
							film.setDirectedBy(regia);
							film.setStory(trama);
							film.setPhoto(locandina);
							film.setVideo(video);
							
							model.AggiornaConChiave(film, oldTitolo);
							request.setAttribute("aggiorna", "aggiornato");
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/film.jsp");
							dispatcher.forward(request, response);
						}
					} catch (SQLException e) {
						System.out.println("Error: "+e.getMessage());
					}
					
				}
				else if(uri.contains("/user/")) {
					try {
						if(action.equals("filmView")) {
							String film;
							film = request.getParameter("film");
							System.out.println("parametro film: "+film);
							request.setAttribute("film", model.recuperaDatiDaChiave(film));
							
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/SchedaFilmView.jsp");
							dispatcher.forward(request, response);
						}
						else if(action.equals("insFilm")) {
							contesto.removeAttribute("films");
							contesto.setAttribute("films", model.recuperaComponenti(""));
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/home.jsp");
							dispatcher.forward(request, response);
						}
						else if(action.equals("search")) {
							response.setContentType("application/json");
							
							String key = request.getParameter("key");
							Collection<FilmBean> allFilm = (Collection<FilmBean>) contesto.getAttribute("films");
							JSONObject json = new JSONObject();
							JSONObject arrFilm = new JSONObject();
							
							if(allFilm!=null && !key.equals("")) {
								try {
									for(FilmBean f: allFilm) {
										if(f.getTitle().toLowerCase().contains(key.toLowerCase())) {
											JSONObject film = new JSONObject();
											film.put("titolo",f.getTitle());
											film.put("locandina", f.getPhoto());
											arrFilm.append("films", film);
										}
									}
									json.put("info", arrFilm);
									response.getWriter().print(json.toString());
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							else {
								try {
									for(FilmBean f: allFilm) {
										JSONObject film = new JSONObject();
										film.put("titolo",f.getTitle());
										film.put("locandina", f.getPhoto());
										arrFilm.append("films", film);
									}
									json.put("info", arrFilm);
								}catch (JSONException e) {
									e.printStackTrace();
								}
								response.getWriter().print(json.toString());
							}	
						}
					} catch (SQLException e) {
						System.out.println("Error: "+e.getMessage());
					}
					
				}
				else {
					try {
						if(action.equals("filmView")) {
							String film;
							film = request.getParameter("film");
							request.setAttribute("film", model.recuperaDatiDaChiave(film));
							
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/SchedaFilmView.jsp");
							dispatcher.forward(request, response);
						}
						else if(action.equals("insFilm")) {
							contesto.removeAttribute("films");
							contesto.setAttribute("films", model.recuperaComponenti(""));
							RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/home.jsp");
							dispatcher.forward(request, response);
						}
						else if(action.equals("search")) {
							response.setContentType("application/json");
							
							String key = request.getParameter("key");
							Collection<FilmBean> allFilm = (Collection<FilmBean>) contesto.getAttribute("films");
							JSONObject json = new JSONObject();
							JSONObject arrFilm = new JSONObject();
							
							if(allFilm!=null && !key.equals("")) {
								try {
									for(FilmBean f: allFilm) {
										if(f.getTitle().toLowerCase().contains(key.toLowerCase())) {
											JSONObject film = new JSONObject();
											film.put("titolo",f.getTitle());
											film.put("locandina", f.getPhoto());
											arrFilm.append("films", film);
										}
									}
									json.put("info", arrFilm);
									response.getWriter().print(json.toString());
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							else {
								try {
									for(FilmBean f: allFilm) {
										JSONObject film = new JSONObject();
										film.put("titolo",f.getTitle());
										film.put("locandina", f.getPhoto());
										arrFilm.append("films", film);
									}
									json.put("info", arrFilm);
								}catch (JSONException e) {
									e.printStackTrace();
								}
								response.getWriter().print(json.toString());
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

}
