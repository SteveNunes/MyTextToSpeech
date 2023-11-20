import sys

from elevenlabs import generate, play, set_api_key
import argparse

models = ["eleven_multilingual_v1", "eleven_multilingual_v2"]
models2 = f"1 - {models[0]}, 2 - {models[1]}"
voices = ["Adam", "Antoni", "Arnold", "Callum", "Charlie", "Clyde", "Daniel", "Dave", "Ethan", "Fin", "Harry", "James", "Jessie", "Joseph", "Josh", "Liam", "Matthew", "Michael", "Patrick", "Sam", "Thomas", "Bella", "Charlotte", "Dorothy", "Elli", "Emily", "Freya", "Gigi", "Glinda", "Grace", "Matilda", "Mimi", "Nicole", "Rachel", "Serena"]

if len(sys.argv) > 1 and sys.argv[1] == "help":
    if len(sys.argv) < 3:
        print("Use: help voices, help voices, help models")
    elif sys.argv[2] == "voices":
        print(f"Available voices: {', '.join(voices)}")
    elif sys.argv[2] == "modelos":
        print(f"Available models: {', '.join(models)}")
    else:
        print("Invalid argument: Use: help voices, help models")
    exit(1)

parser = argparse.ArgumentParser(description='Text-to-speech based on ElevenLabs\'s API')
parser.add_argument('api_key', help='API Key (If you don\'t got one or don\'t want to use it, use \'-\'')
parser.add_argument('voice', help='The name of the voice you want to use (help voices)')
parser.add_argument('model', help=f'A numeric value refer to voice model you want to use (help models)')
parser.add_argument('text_to_speech', nargs='+', help='Texto a ser lido')
args = parser.parse_args()

try:
    modelo_pos = int(args.model)
except ValueError:
    print(f"The third argument must be a numeric value refer to voice model you want to use ({models2})")
    exit(1)

if modelo_pos < 1 or modelo_pos > len(models):
    print(f"Invalid index for voice model. Available indexes: {models2}")
    exit(1)

api_key = args.api_key
voice_name = args.voice
voice_model = models[modelo_pos - 1]
text_to_speech = ' '.join(args.text_to_speech)

if api_key != "-":
    set_api_key(api_key)

audio = generate(
  text=text_to_speech,
  voice=voice_name,
  model=voice_model
)

play(audio)