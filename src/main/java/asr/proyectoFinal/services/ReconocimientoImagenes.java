package asr.proyectoFinal.services;


import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
//import java.io.FileNotFoundException;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
//import com.ibm.cloud.sdk.core.service.security.IamOptions;

public class ReconocimientoImagenes {
	
	public static String reconoceImagen (String miUrl) 
	{
		IamAuthenticator authenticator = new IamAuthenticator("8EPNc3w9Lv6hFbyB55UvzpATFk9pD9-qG8k0xURee7Pc");
		
	
		VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
	
		visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/d24b74eb-d8f0-4dd1-94f5-9e02374864c2");
	
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
		        .url(miUrl)
		        .build();
		ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
		
		//System.out.println(
		//        "\n******** Classify with the General model ********\n" + result
		//            + "\n******** END Classify with the General model ********\n");
		
		
		String texto=result.toString();
		return texto;
	}
}