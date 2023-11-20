package application;

import java.io.File;

public abstract class TextToSpeechGoogle {

	
	private static String outputTempFile = "";

	public static void speech(String text, float gain, float speechSpeed, int passes) {
		if (text == null || text.isBlank() || text.isEmpty())
			return;
		File pyScriptFile = null;
		File pyExeFile = null;
		try {
			pyScriptFile = new File("./gTTS/main.py");
			String pyScriptPath = pyScriptFile.getAbsolutePath();
			pyExeFile = new File("./gTTS/venv/Scripts/python.exe");
			String pyExePath = pyExeFile.getAbsolutePath();
			ProcessBuilder processBuilder = new ProcessBuilder(pyExePath, pyScriptPath, outputTempFile, "" + gain, "" + speechSpeed, "" + passes, text);
			processBuilder.redirectErrorStream(false);
			processBuilder.start().waitFor();
    }
		catch (Exception e) {
			if (pyScriptFile == null || pyExeFile == null || !pyScriptFile.exists() || !pyExeFile.exists())
				System.err.println("Verifique se a pasta 'gTTS' foi copiada para a pasta raiz do seu projeto (Você pode encontrar essa pasta dentro do arquivo 'MyPersonalLib.jar'");
			else
				e.printStackTrace();
		}
	}

	public static void speech(String text)
		{ speech(text, 0, 1, 1); }
	
	/** Local onde o script python irá gerar o arquivo de áudio para então reproduzí-lo */
	public static void setOutputTempFile(String filePath)
		{ outputTempFile = filePath; }
	
}
