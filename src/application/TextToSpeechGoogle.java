package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import enums.GoogleLanguages;
import exceptions.GoogleLanguageException;

public abstract class TextToSpeechGoogle {
	
	private static File pyScriptFile = null;
	private static File pyExeFile = null;
	private static String pyScriptPath = null;
	private static String pyExePath = null;
	private static String language = "en";
	private static float gain = 1f;
	private static float speed = 1f;
	private static boolean slow = false;
	
	private static List<String> callScript(ProcessBuilder processBuilder) throws Exception {
		List<String> outputStream = new LinkedList<>();
    processBuilder.redirectErrorStream(true);
    processBuilder.environment().put("PYTHONIOENCODING", "UTF-8");
    Process process = processBuilder.start();
    InputStream inputStream = process.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    String line = "";
    while ((line = reader.readLine()) != null) {
    	outputStream.add(line);
    	if (line.contains("ValueError: Language not supported:"))
 				throw new GoogleLanguageException(line);
    }
    process.waitFor();
    return outputStream.isEmpty() ? null : outputStream;
	}

	public static void speech(String text) throws Exception {
		if (text == null || text.isBlank() || text.isEmpty())
			return;
		checkFolder();
		callScript(new ProcessBuilder(pyExePath, pyScriptPath, "-", language, "" + gain, "" + speed, slow ? "True" : "False", text));
	}

	public static void generateAndSave(String text, String outputFilePath) throws Exception {
		if (text == null || text.isBlank() || text.isEmpty())
			return;
		checkFolder();
		callScript(new ProcessBuilder(pyExePath, pyScriptPath, outputFilePath, language, "" + gain, "" + speed, slow ? "True" : "False", text));
	}

	public static float getVolumeGain()
		{ return gain; }

	public static void setVolumeGain(float value)
		{ gain = value; }

	public static float getSpeechSpeed()
		{ return speed; }

	public static void setSpeechSpeed(float value)
		{ speed = value; }
	
	public static String getLanguage()	
		{ return language; }
	
	public static void testLanguage(String language) throws Exception {
		checkFolder();
		callScript(new ProcessBuilder(pyExePath, pyScriptPath, "-", language, "0", "1", "False", "."));
		TextToSpeechGoogle.language = language;
	}
	
	public static void setLanguage(String language) throws Exception {
		GoogleLanguages lang;
		try {
			lang = GoogleLanguages.valueOf(language);
			TextToSpeechGoogle.language = lang.name();
		}
		catch (Exception e)
			{ throw new GoogleLanguageException(language + " - Invalid language"); }
	}
	
	private static void checkFolder() throws Exception {
		if (pyExeFile == null)
			try {
				pyScriptFile = new File("gTTS/main.py");
				pyScriptPath = pyScriptFile.getAbsolutePath();
				pyExeFile = new File("gTTS/venv/Scripts/python.exe");
				pyExePath = pyExeFile.getAbsolutePath();
			}
			catch (Exception e) {
				if (pyScriptFile == null || pyExeFile == null || !pyScriptFile.exists() || !pyExeFile.exists())
					throw new RuntimeException("Ensure the 'gTTS' folder is present on your project root folder (You can get this folder from inside of the 'MyTextToSpeech.jar' file");
				else
					e.printStackTrace();
			}
	}

}
