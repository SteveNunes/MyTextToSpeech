package enums;

public enum EllevenLabsVoices {
	
	ADAM("Adam"),
	ANTONI("Antoni"),
	ARNOLD("Arnold"),
	CALLUM("Callum"),
	CHARLIE("Charlie"),
	CLYDE("Clyde"),
	DANIEL("Daniel"),
	DAVE("Dave"),
	ETHAN("Ethan"),
	FIN("Fin"),
	HARRY("Harry"),
	JAMES("James"),
	JESSIE("Jessie"),
	JOSEPH("Joseph"),
	JOSH("Josh"),
	LIAM("Liam"),
	MATTHEW("Matthew"),
	MICHAEL("Michael"),
	PATRICK("Patrick"),
	SAM("Sam"),
	THOMAS("Thomas"),
	BELLA("Bella"),
	CHARLOTTE("Charlotte"),
	DOROTHY("Dorothy"),
	ELLI("Elli"),
	EMILY("Emily"),
	FREYA("Freya"),
	GIGI("Gigi"),
	GLINDA("Glinda"),
	GRACE("Grace"),
	MATILDA("Matilda"),
	MIMI("Mimi"),
	NICOLE("Nicole"),
	RACHEL("Rachel"),
	SERENA("Serena");
	
	private String value;

	EllevenLabsVoices(String voiceName)
		{ value = voiceName; }

	public String getValue()
		{ return value; }
	
}