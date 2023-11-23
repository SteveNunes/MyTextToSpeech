package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import enums.ElevenLabsModels;
import exceptions.ElevenLabsApiKeyException;
import exceptions.ElevenLabsAuthenticatedRequestsException;
import exceptions.ElevenLabsSubscriptionException;
import exceptions.ElevenLabsVoiceException;
import exceptions.ElevenLabsVoiceModelException;

public abstract class TextToSpeechElevenLabs {

	private static File pyScriptFile = null;
	private static File pyExeFile = null;
	private static String pyScriptPath = null;
	private static String pyExePath = null;
	private static List<String> voiceList = null;
	private static String apiKey = "-";
	private static String voiceName = "Adam";
	private static ElevenLabsModels voiceModel = ElevenLabsModels.ELEVEN_MULTILINGUAL_V2;
	private static float gain = 1f;
	private static float speed = 1f;
	private static float stability = 0.5f;
	private static float similarity = 1f;
	private static float style = 0f;
	private static boolean speakerBoost = false;
		
	private static List<String> callScript(ProcessBuilder processBuilder) throws Exception {
		List<String> outputStream = new LinkedList<>();
    processBuilder.redirectErrorStream(true);
    Process process = processBuilder.start();
    InputStream inputStream = process.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String line = "";
    while ((line = reader.readLine()) != null) {
    	outputStream.add(line);
    	if (line.contains("Invalid API key"))
 				throw new ElevenLabsApiKeyException(line);
    	if (line.contains("You have reached the limit of unauthenticated requests"))
 				throw new ElevenLabsAuthenticatedRequestsException(line);
    	if (line.contains("Invalid index for voice model"))
 				throw new ElevenLabsVoiceModelException(line);
    	if (line.contains("You are not permitted to use instantly cloned voices"))
 				throw new ElevenLabsVoiceModelException(line);
    	if (line.matches(".*ValueError: Voice '.+' not found.*"))
 				throw new ElevenLabsVoiceException(line);
    	if (line.contains("upgrade your subscription"))
 				throw new ElevenLabsSubscriptionException(line);
    }
    process.waitFor();
    return outputStream.isEmpty() ? null : outputStream;
	}
	
	public static void speech(String text) throws Exception {
		if (text == null || text.isBlank() || text.isEmpty())
			return;
		checkFolder(); 
		callScript(new ProcessBuilder(pyExePath, pyScriptPath, "-", apiKey, voiceName, voiceModel.getValue(), "" + gain, "" + speed, "" + stability, "" + similarity, "" + style, speakerBoost ? "True" : "False", text));
	}
	
	public static void generateAndSave(String text, String outputFilePath) throws Exception {
		if (text == null || text.isBlank() || text.isEmpty())
			return;
		checkFolder();
		callScript(new ProcessBuilder(pyExePath, pyScriptPath, outputFilePath, apiKey, voiceName, voiceModel.getValue(), "" + gain, "" + speed, "" + stability, "" + similarity, "" + style, speakerBoost ? "True" : "False", text));
	}
	
	private static void checkFolder() throws Exception {
		if (pyExeFile == null)
			try {
				pyScriptFile = new File("TTS ElevenLabs/main.py");
				pyScriptPath = pyScriptFile.getAbsolutePath();
				pyExeFile = new File("TTS ElevenLabs/venv/Scripts/python.exe");
				pyExePath = pyExeFile.getAbsolutePath();
			}
			catch (Exception e) {
				if (pyScriptFile == null || pyExeFile == null || !pyScriptFile.exists() || !pyExeFile.exists())
					throw new RuntimeException("Ensure the 'TTS ElevenLabs' folder is present on your project root folder (You can get this folder from inside of the 'MyTextToSpeech.jar' file");
				else
					e.printStackTrace();
			}
	}
	
	public static List<String> getAvailableVoices() {
		if (voiceList == null)
			try {
				checkFolder();
				List<String> list;
				if (!apiKey.equals("-"))
					list = new LinkedList<>(callScript(new ProcessBuilder(pyExePath, pyScriptPath, "help", "voices", apiKey)));
				else
					list = new LinkedList<>(callScript(new ProcessBuilder(pyExePath, pyScriptPath, "help", "voices")));
				String[] result = list.get(0).substring(list.get(0).indexOf(':') + 2).split(", ");
				voiceList = new LinkedList<>(Arrays.asList(result));
			}
			catch (Exception e)
				{ throw new RuntimeException("Unable to fetch the list of available names\n" + e.getStackTrace()); }
		return voiceList;
	}
	
	public static String getApiKey()
		{ return apiKey; }
	
	public static void testApiKey(String apiKey) throws Exception {
		checkFolder();
		try
			{ callScript(new ProcessBuilder(pyExePath, pyScriptPath, "-", apiKey, "Adam", voiceModel.getValue(), "0", "1", "0.5", "1", "0", "False", ".")); }
		catch (Exception e)
			{ throw new ElevenLabsApiKeyException(e.getMessage()); }
	}
	
	public static void setApiKey(String apiKey)
		{ TextToSpeechElevenLabs.apiKey = apiKey; }
	
	public static void removeApiKey()
		{ apiKey = "-"; }

	public static String getVoice()
		{ return voiceName; }

	public static void setVoice(String voiceName)
		{ TextToSpeechElevenLabs.voiceName = voiceName; }

	public static ElevenLabsModels getVoiceModel()
		{ return voiceModel; }

	public static void setVoiceModel(ElevenLabsModels model)
		{ TextToSpeechElevenLabs.voiceModel = model; }

	public static float getStability()
		{ return stability; }

	public static void setStability(float value) {
		if (value < 0.0f || value > 1.0f)
			throw new RuntimeException("'value' must be 0.0 to 1.0");
		stability = value;
	}

	public static float getSimilarity()
		{ return similarity; }

	public static void setSimilarity(float value) {
		if (value < 0.0f || value > 1.0f)
			throw new RuntimeException("'value' must be 0.0 to 1.0");
		similarity = value;
	}

	public static float getStyleExageration()
		{ return style; }

	public static void setStyleExageration(float value) {
		if (value < 0.0f || value > 1.0f)
			throw new RuntimeException("'value' must be 0.0 to 1.0");
		style = value;
	}
	
	public static float getVolumeGain()
		{ return gain; }

	public static void setVolumeGain(float value)
		{ gain = value; }

	public static float getSpeechSpeed()
		{ return speed; }
	
	public static void setSpeechSpeed(float value)
		{ speed = value; }

	public static boolean getSpeakerBoost()
		{ return speakerBoost; }

	public static void setSpeakerBoost(boolean state)
		{ speakerBoost = state; }	
	
}
