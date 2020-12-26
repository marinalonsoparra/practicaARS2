package asr.proyectoFinal.services;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionAlternative;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResult;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;

public class Transcriptor{
	
	public static String transcribe(String audio) throws Exception
	{
		
		Authenticator authenticator = new IamAuthenticator("qjnNOJCauIJ0UzzndNbSxVpX6Nsg4q2-5gZ_EQOBYfZL");
		SpeechToText service = new SpeechToText(authenticator);
		
		service.setServiceUrl("https://api.eu-gb.speech-to-text.watson.cloud.ibm.com/instances/5f911be5-4485-47fd-916a-6a18cad3095c");
		File audio_f = null;
		try{
			//audio = new File("/Users/carmenolleromerello/Desktop/success.wav");
			audio_f = new File(audio);
			
		}catch(Exception e){
			System.out.println("error carga archivo");
		}

		RecognizeOptions options = new RecognizeOptions.Builder()
		  .audio(audio_f)
		  .contentType(HttpMediaType.AUDIO_WAV)
		  .build();
		
		SpeechRecognitionResults transcript = service.recognize(options).execute().getResult();
		//System.out.println("Tu transcript:" + transcript);
		
		String idJSON = transcript.toString();
		JsonObject rootObj = JsonParser.parseString(idJSON).getAsJsonObject();
		JsonArray sol = rootObj.getAsJsonArray("results");
		JsonArray alt = sol.get(0).getAsJsonObject().getAsJsonArray("alternatives");
		String trans = alt.get(0).getAsJsonObject().get("transcript").toString();
		
		//System.out.println("Tu transcript final:" + trans);
	
        //return the transcript
	
        return trans;
	}

}