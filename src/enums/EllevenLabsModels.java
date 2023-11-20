package enums;

public enum EllevenLabsModels {
	
	ELEVEN_MULTILANGUAL_V1("1"),
	ELEVEN_MULTILANGUAL_V2("2");
	private String value;

	EllevenLabsModels(String voiceModelName)
		{ value = voiceModelName; }

	public String getValue()
		{ return value; }

}