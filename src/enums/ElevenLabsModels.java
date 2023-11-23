package enums;

public enum ElevenLabsModels {
	
	ELEVEN_MULTILINGUAL_V1("1"),
	ELEVEN_MULTILINGUAL_V2("2"),
	ELEVEN_MONOLINGUAL_V1("3"),
	ELEVEN_TURBO_V2("4");
	private String value;

	ElevenLabsModels(String voiceModelName)
		{ value = voiceModelName; }

	public String getValue()
		{ return value; }

}