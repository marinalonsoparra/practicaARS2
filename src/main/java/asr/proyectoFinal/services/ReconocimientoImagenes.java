package asr.proyectoFinal.services;


import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
//import java.io.FileNotFoundException;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImage;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

//import com.ibm.cloud.sdk.core.service.security.IamOptions;

public class ReconocimientoImagenes {
	
	public static String reconoceImagen (String miUrl) 
	{
		IamAuthenticator authenticator = new IamAuthenticator("y_8ZCDYW1YIBHlPrNVS3Q8p0xhO0J5Fha0CY1daK8J3L");
		
	
		VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
	
		visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/d0fb5270-a1bd-4fdb-8cd4-434eb718017d");
	
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
		        .url(miUrl)
		        .build();
	
		ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
		
		
		String identificacionJSON = result.toString();
		JsonObject rootObj = JsonParser.parseString(identificacionJSON).getAsJsonObject();
		JsonArray images =rootObj.getAsJsonArray("images");
		JsonArray classifiers= images.get(0).getAsJsonObject().getAsJsonArray("classifiers");
		
		String clase = "sin_identificar";
		if (classifiers.size()>0)
			 clase=classifiers.get(0).getAsJsonObject().getAsJsonArray("classes")
								 .get(0).getAsJsonObject().get("class").toString();
		
		System.out.println(identificacionJSON);
		return clase;
	}
	public static String reconoceImagenOrdenador (String imagen) 
	{
		IamAuthenticator authenticator = new IamAuthenticator("8EPNc3w9Lv6hFbyB55UvzpATFk9pD9-qG8k0xURee7Pc");
		
	
		VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
	
		visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/d24b74eb-d8f0-4dd1-94f5-9e02374864c2");
	
		ClassifyOptions classifyOptions;
		try {
			classifyOptions = new ClassifyOptions.Builder().imagesFile(
			        Paths.get(ClassLoader.getSystemResource(imagen)
			                        .toURI())
			                .toFile()).build();
			ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
			
			System.out.print(result);
			
			String identificacionJSON = result.toString();
			JsonObject rootObj = JsonParser.parseString(identificacionJSON).getAsJsonObject();
			JsonArray images =rootObj.getAsJsonArray("images");
			JsonArray classifiers= images.get(0).getAsJsonObject().getAsJsonArray("classifiers");
			
			String clase = "sin_identificar";
			if (classifiers.size()>0)
				 clase=classifiers.get(0).getAsJsonObject().getAsJsonArray("classes")
									 .get(0).getAsJsonObject().get("class").toString();
			
			System.out.println(identificacionJSON);
			return clase;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			 return "nada";
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "nada";
		}
	
		
		
	}
}