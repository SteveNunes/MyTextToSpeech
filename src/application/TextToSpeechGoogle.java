package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private static Map<String, String> partialLanguageList = new LinkedHashMap<>() {{
		put("af", "Africâner");
		put("sq", "Albanês");
		put("de", "Alemão");
		put("am", "Amárico");
		put("ar", "Árabe");
		put("hy", "Armênio");
		put("as", "Assamês");
		put("ay", "Aymara");
		put("az", "Azerbaijano");
		put("eu", "Basco");
		put("be", "Bielorrusso");
		put("bn", "Bengali");
		put("bho", "Bhojpuri");
		put("bs", "Bósnio");
		put("bg", "Búlgaro");
		put("ca", "Catalão");
		put("ceb", "Cebuano");
		put("zh-CN", "Chinês (Simplificado)");
		put("zh-TW", "Chinês (Tradicional)");
		put("co", "Corso");
		put("hr", "Croata");
		put("cs", "Tcheco");
		put("da", "Dinamarquês");
		put("dv", "Dhivehi");
		put("doi", "Dogri");
		put("nl", "Holandês");
		put("en", "Inglês");
		put("eo", "Esperanto");
		put("et", "Estoniano");
		put("ee", "Ewe");
		put("fil", "Filipino (Tagalo)");
		put("fi", "Finlandês");
		put("fr", "Francês");
		put("fy", "Frísio");
		put("gl", "Galego");
		put("ka", "Georgiano");
		put("de", "Alemão");
		put("el", "Grego");
		put("gn", "Guarani");
		put("gu", "Gujarati");
		put("ht", "Crioulo haitiano");
		put("ha", "Hausa");
		put("haw", "Havaiano");
		put("he", "Hebraico");
		put("hi", "Hindi");
		put("hmn", "Hmong");
		put("hu", "Húngaro");
		put("is", "Islandês");
		put("ig", "Igbo");
		put("ilo", "Ilocano");
		put("id", "Indonésio");
		put("ga", "Irlandês");
		put("it", "Italiano");
		put("ja", "Japonês");
		put("jv", "Javanês");
		put("kn", "Kannada");
		put("kk", "Cazaque");
		put("km", "Khmer");
		put("rw", "Kinyarwanda");
		put("gom", "Konkani");
		put("ko", "Coreano");
		put("kri", "Krio");
		put("ku", "Curdo");
		put("ckb", "Curdo (Sorani)");
		put("ky", "Quirguiz");
		put("lo", "Laosiano");
		put("la", "Latim");
		put("lv", "Letão");
		put("ln", "Lingala");
		put("lt", "Lituano");
		put("lg", "Luganda");
		put("lb", "Luxemburguês");
		put("mk", "Macedônio");
		put("mai", "Maithili");
		put("ms", "Malaio");
		put("ml", "Malaiala");
		put("mt", "Maltês");
		put("mi", "Maori");
		put("mr", "Marathi");
		put("mni-Mtei", "Meiteilon (Manipuri)");
		put("lus", "Mizo");
		put("mn", "Mongol");
		put("my", "Birmanês");
		put("ne", "Nepalês");
		put("no", "Norueguês");
		put("ny", "Nyanja (Chichewa)");
		put("or", "Odia (Oriya)");
		put("om", "Oromo");
		put("ps", "Pashto");
		put("fa", "Persa");
		put("pl", "Polonês");
		put("pt", "Português (Português (Brasil))");
		put("pa", "Punjabi");
		put("qu", "Quechua");
		put("ro", "Romeno");
		put("ru", "Russo");
		put("sm", "Samoano");
		put("sa", "Sânscrito");
		put("gd", "Gaélico escocês");
		put("nso", "Sepedi");
		put("sr", "Sérvio");
		put("st", "Sesotho");
		put("sn", "Shona");
		put("sd", "Sindhi");
		put("si", "Cingalês");
		put("sk", "Eslovaco");
		put("sl", "Esloveno");
		put("so", "Somali");
		put("es", "Espanhol");
		put("sw", "Suaíli");
		put("sv", "Sueco");
		put("tl", "Tagalo (Filipino)");
		put("tg", "Tadjique");
		put("ta", "Tâmil");
		put("tt", "Tártaro");
		put("te", "Telugu");
		put("th", "Tailandês");
		put("ti", "Tigrínia");
		put("ts", "Tsonga");
		put("tr", "Turco");
		put("tk", "Turcomeno");
		put("ak", "Twi (Akan)");
		put("uk", "Ucraniano");
		put("ur", "Urdu");
		put("ug", "Uigur");
		put("uz", "Uzbeque");
		put("vi", "Vietnamita");
		put("cy", "Galês");
		put("xh", "Xhosa");
		put("yi", "Iídiche");
		put("yo", "Ioruba");
		put("zu", "Zulu");		
	}};
	
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
	
	public static List<String> getLanguageList()
		{ return new ArrayList<>(partialLanguageList.keySet()); }
	
	public static Map<String, String> getLanguageMap()
		{ return partialLanguageList; }

	public static void testLanguage(String language) throws Exception {
		checkFolder();
		callScript(new ProcessBuilder(pyExePath, pyScriptPath, "-", language, "0", "1", "False", "."));
		TextToSpeechGoogle.language = language;
	}
	
	public static void setLanguage(String language)
		{ TextToSpeechGoogle.language = language; }
	
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
