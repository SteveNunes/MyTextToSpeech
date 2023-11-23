# MyTextToSpeech
An easy way for use Text-To-Speech engine from Google API or ElevenLabs API

# WARNING
You *MUST* copy the ``./gTTS/``  and ``./TTS ElevenLabs/`` both folders from this project (or from the *JAR*) into your root project directory where you want to use it, in order to make it work propperly

## TextToSpeechGoogle

#### Speeching from a String
````
TextToSpeechGoogle.speech("Hello world");
````

#### Generating an audio fille from a String
````
TextToSpeechGoogle.generateAndSave("Hello world");
````

#### Setting up the language
````
TextToSpeechGoogle.setLanguage("en");
````

#### Testing if a String is a valid language
````
if (TextToSpeechGoogle.testLanguage("en")) {
  System.out.println("It's a valid language");
}
````

#### Retrieve the current language value
````
TextToSpeechGoogle.getLanguage();
````

#### Setting up the speech speed (1f = Default speed, 2f = 2x speed)
````
TextToSpeechGoogle.setSpeechSpeed(1f);
````

#### Retrieve the current speech speed
````
TextToSpeechGoogle.getSpeechSpeed();
````

#### Setting up the voice volume gain (1f = No gain (Default volume), 2f = double gain)
````
TextToSpeechGoogle.setVolumeGain(1f);
````

#### Retrieve the current voice volume gain
````
TextToSpeechGoogle.getVolumeGain();
````

## TextToSpeechElevenLabs

#### Speeching from a String
````
TextToSpeechElevenLabs.speech("Hello world");
````

#### Generating an audio fille from a String
````
TextToSpeechElevenLabs.generateAndSave("Hello world");
````

#### Setting up the voice
````
TextToSpeechElevenLabs.setVoice("Name");
````

#### Retrieve the current voice
````
TextToSpeechElevenLabs.getVoice();
````

#### Setting up the voice model
````
TextToSpeechElevenLabs.setVoice(ElevenLabsModels.ELEVEN_MULTILINGUAL_V2);
````

#### Retrieve the current voice model
````
TextToSpeechElevenLabs.getVoiceModel();
````

#### Setting up the voice similarity (0.0 to 1.0)
````
TextToSpeechElevenLabs.setSimilarity(1f);
````

#### Retrieve the current voice similarity
````
TextToSpeechElevenLabs.getSimilarity();
````

#### Setting up the voice stability (0.0 to 1.0)
````
TextToSpeechElevenLabs.setStability(1f);
````

#### Retrieve the current voice stability
````
TextToSpeechElevenLabs.getStability();
````

#### Setting up the voice style exageration (0.0 to 1.0)
````
TextToSpeechElevenLabs.setStyleExageration(1f);
````

#### Retrieve the current voice style exageration
````
TextToSpeechElevenLabs.getStyleExageration();
````

#### Setting up the speaker boost (true or false)
````
TextToSpeechElevenLabs.setSpekerBoost(true);
````

#### Retrieve the current speaker boost
````
TextToSpeechElevenLabs.getSpekerBoost();
````

#### Setting up the speech speed (1f = Default speed, 2f = 2x speed)
````
TextToSpeechElevenLabs.setSpeechSpeed(1f);
````

#### Retrieve the current speech speed
````
TextToSpeechElevenLabs.getSpeechSpeed();
````

#### Setting up the voice volume gain (1f = No gain (Default volume), 2f = double gain)
````
TextToSpeechElevenLabs.setVolumeGain(1f);
````

#### Retrieve the current voice volume gain
````
TextToSpeechElevenLabs.getVolumeGain();
````

#### Setting up the API Key (For be able to use custom voices and character limits from your ElevenLabs account)
````
TextToSpeechElevenLabs.setApiKey("yourAPIKeyHere");
````

#### Test if the current API Key is valid (Throws an ElevenLabsApiKeyException if the API Key is not valid)
````
TextToSpeechElevenLabs.testApiKey("API Key for test"));
````

#### Remove the current API Key
````
TextToSpeechElevenLabs.removeApiKey();
````

#### Retrieve the current API Key
````
TextToSpeechElevenLabs.getApiKey();
````

#### Retrieve a list of all available voices (Even the cloned ones if are you using a valid API Key and you have cloned voices on your account)
````
TextToSpeechElevenLabs.getAvailableVoices();
````