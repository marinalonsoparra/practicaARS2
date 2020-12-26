package asr.proyectoFinal.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import asr.proyectoFinal.dao.CloudantPalabraStore;
import asr.proyectoFinal.dominio.Palabra;
import asr.proyectoFinal.services.Traductor;
import asr.proyectoFinal.services.Transcriptor;
import asr.proyectoFinal.services.ReconocimientoImagenes;
import asr.proyectoFinal.services.TextToSpeechService;
/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/listar", "/insertar", "/hablar","/detectarImagen"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		out.println("<html><head><meta charset=\"UTF-8\"></head><body>");
		
		CloudantPalabraStore store = new CloudantPalabraStore();
		
		System.out.println(request.getServletPath());
		
		String idioma_original; 
		String idioma_final; 
		
		switch(request.getServletPath())
		{
			case "/listar":
				if(store.getDB() == null)
					  out.println("No hay DB");
				else
					out.println("Palabras en la BD Cloudant:<br />" + store.getAll());

				break;
				
			case "/insertar":
				Palabra palabra = new Palabra();
				String parametro = request.getParameter("palabra");
				
				

				if(parametro==null)
				{
					out.println("usage: /insertar?palabra=palabra_a_traducir");
				}
				else
				{
					if(store.getDB() == null) 
					{
						out.println(String.format("Palabra: %s", palabra));
					}
					else
					{
						idioma_original = Controller.setLanguage(request.getParameter("idioma_entrada"));
						idioma_final = Controller.setLanguage(request.getParameter("idioma_salida"));
						

						if (!idioma_original.equals("en"))
							parametro = Traductor.translate(parametro, idioma_original, "en", false);
						
						if (!idioma_final.equals("en"))
							parametro = Traductor.translate(parametro, "en", idioma_final, false);
						
						palabra.setName(parametro);
						store.persist(palabra);
					    out.println(String.format("Almacenada la palabra: %s", palabra.getName()));
					    
					}
				}
				break;
				
			case "/hablar":
				String palabra_es = request.getParameter("palabra_audio");

				idioma_original = Controller.setLanguage(request.getParameter("idioma_entrada"));
				idioma_final = Controller.setLanguage(request.getParameter("idioma_salida"));
				
				
				String audio = palabra_es;
				
				if (!idioma_original.equals("en"))
					audio=Traductor.translate(palabra_es,idioma_original, "en", false);
				
				//audio = Traductor.translate(audio,"en", idioma_final, false);
				String transcripcion ="";
				TextToSpeechService.createSpeech(audio);
				 try {	
					 Thread.sleep(3);
				 }catch(InterruptedException ex) {
					 System.out.print("Fallo del sleep");
				 }
				 TextToSpeechService.reproducirSonido(audio + ".wav");
				 try {
						transcripcion = Transcriptor.transcribe(audio + ".wav");
						out.println(transcripcion);
					}catch(Exception e) {
						System.out.println("error 1");
					}
			break;
			
			case "/detectarImagen":
				
				Palabra palabraImagen = new Palabra();
				String parametroUrl = request.getParameter("url");
				

				idioma_original = Controller.setLanguage(request.getParameter("idioma_entrada"));
				idioma_final = Controller.setLanguage(request.getParameter("idioma_salida"));
				
				if(parametroUrl==null)
				{
					out.println("usage: /detectarImagen?url=url de la imagen a detectar");
				}
				else
				{	
					
					String image_to_text=ReconocimientoImagenes.reconoceImagen(parametroUrl);
					
					if (!idioma_final.equals("en"))
						image_to_text=Traductor.translate(image_to_text,"en", idioma_final, false);
						
					out.println(String.format("La imagen es %s", image_to_text));			    	  
					    
				}
			break;
			
		}
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected static String setLanguage(String input)
	{
		if (input.contains("Esp")){ return "es";}
		else if (input.contains("Ing")) {return "en";}
		else if (input.contains("Fra")) {return "fr";}
		else if (input.contains("Alem")) {return "de";}
		else return "es";
	}

}
