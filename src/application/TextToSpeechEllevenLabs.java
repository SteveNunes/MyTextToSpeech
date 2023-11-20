package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import enums.EllevenLabsModels;
import enums.EllevenLabsVoices;

public abstract class TextToSpeechEllevenLabs {

	private static EllevenLabsVoices voice = EllevenLabsVoices.ADAM;
	private static EllevenLabsModels model = EllevenLabsModels.ELEVEN_MULTILANGUAL_V2;
	private static String customVoice = null;
	private static String apiKey = "-";
	private static String testApiKey = "{[(TEST API KEY)]}";
	
	public static void speech(String text) {
		if (text == null || text.isBlank() || text.isEmpty())
			return;
		File pyScriptFile = null, pyExeFile = null;
		try {
			pyScriptFile = new File("./TTS ElevenLabs/main.py");
			String pyScriptPath = pyScriptFile.getAbsolutePath();
			pyExeFile = new File("./TTS ElevenLabs/venv/Scripts/python.exe");
			String pyExePath = pyExeFile.getAbsolutePath();
			ProcessBuilder processBuilder = new ProcessBuilder(pyExePath, pyScriptPath, apiKey == null ? "-" : apiKey, customVoice != null ? customVoice : voice.getValue(), model.getValue(), text.equals(testApiKey) ? " " : text);
      processBuilder.redirectErrorStream(true);
      Process process = processBuilder.start();
      InputStream inputStream = process.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line = "";
      while ((line = reader.readLine()) != null) {
      	if (line.contains("Invalid API key") ||
      			line.contains("You have reached the limit of unauthenticated requests") ||
      			line.contains("Invalid index for voice model") ||
      			line.contains("upgrade your subscription"))
      				throw new RuntimeException(line);
      }
      process.waitFor();
    }
		catch (Exception e) {
			if (text.equals(testApiKey))
				throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static boolean isValidApiKey(String apiKey) {
		String key = apiKey;
		setApiKey(apiKey);
		try
			{ speech(testApiKey); }
		catch (Exception e) {
			setApiKey(key);
			if (e.getMessage().contains("Invalid API key"))
				return false;
		}
		setApiKey(key);
		return true;
	}
	
	public static EllevenLabsVoices getVoice()
		{ return voice; }

	public static void setVoice(EllevenLabsVoices voice) {
		customVoice = null;
		TextToSpeechEllevenLabs.voice = voice;
	}

	public static String getCustomVoice()
		{ return customVoice; }
	
	public static void setCustomVoice(String customVoice) {
		voice = null;
		TextToSpeechEllevenLabs.customVoice = customVoice;
	}

	public static EllevenLabsModels getModel()
		{ return model; }

	public static void setModel(EllevenLabsModels model)
		{ TextToSpeechEllevenLabs.model = model; }

	public static String getApiKey()
		{ return apiKey; }

	public static void setApiKey(String apiKey)
		{ TextToSpeechEllevenLabs.apiKey = apiKey; }	
	
}
