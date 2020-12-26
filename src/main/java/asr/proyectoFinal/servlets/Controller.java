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
						parametro = Traductor.translate(parametro, "es", "en", false);
						palabra.setName(parametro);
						store.persist(palabra);
					    out.println(String.format("Almacenada la palabra: %s", palabra.getName()));			    	  
					}
				}
				break;
				
			case "/hablar":
				String palabra_es = request.getParameter("palabra_audio");
				String audio = Traductor.translate(palabra_es, "es", "en", false);
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
				
				if(parametroUrl==null)
				{
					out.println("usage: /detectarImagen?url=url de la imagen a detectar");
				}
				else
				{	
					
					String image_to_text=ReconocimientoImagenes.reconoceImagen(parametroUrl);
						
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

}
