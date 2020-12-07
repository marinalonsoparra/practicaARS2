package asr.proyectoFinal.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;

public class Traductor
{
	public static String translate(String palabra, String sourceModel, String destModel, boolean conversational)
	{
		String model;
		if(sourceModel.equals("en") || sourceModel.equals("es") || destModel.equals("en") || destModel.equals("es"))
		{
			model=sourceModel+"-"+destModel;
			if(conversational) 
				model+="-conversational";
		}
		else
			model="en-es";

		Authenticator authenticator = new IamAuthenticator("1FE5-hQIc25tkFT_TbPsxJsQserpy6yFFYobbnsrOzjd");
		LanguageTranslator languageTranslator = new LanguageTranslator("2020-11-15",authenticator);

		languageTranslator.setServiceUrl("https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/7b06525e-3edd-430c-8386-aa382dde53e1");
		
		TranslateOptions translateOptions = new TranslateOptions.Builder()
		  .addText(palabra)
		  .modelId(model)
		  .build();
		
		TranslationResult translationResult = languageTranslator.translate(translateOptions).execute().getResult();

		System.out.println(translationResult);		
		
		String traduccionJSON = translationResult.toString();
		JsonObject rootObj = JsonParser.parseString(traduccionJSON).getAsJsonObject();
		JsonArray traducciones = rootObj.getAsJsonArray("translations");
		String traduccionPrimera = palabra;
		if(traducciones.size()>0)
			traduccionPrimera = traducciones.get(0).getAsJsonObject().get("translation").getAsString();
		
		return traduccionPrimera;
	}
}