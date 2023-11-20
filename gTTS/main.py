from gtts import gTTS
import argparse
import os
import pygame
from pydub import AudioSegment

def play_mp3(file_path):
    pygame.mixer.init()
    pygame.mixer.music.load(file_path)
    pygame.mixer.music.play()
    while pygame.mixer.music.get_busy():
        pygame.time.Clock().tick(10)
    pygame.mixer.quit()

def play_speeded_mp3(file_path, volume, speed_factor, passes):
    sound = AudioSegment.from_mp3(file_path)
    sound = sound + volume
    speeded_sound = sound.speedup(playback_speed=speed_factor)
    for i in range(passes - 1):
      speeded_sound = speeded_sound.speedup(playback_speed=speed_factor)
    speeded_sound.export(os.path.join(output_dir, "temp.wav"), format="wav")
    play_mp3(os.path.join(output_dir, "temp.wav"))

parser = argparse.ArgumentParser(description='Conversor de texto em fala')
parser.add_argument('output_dir', help='Diretório de saída do arquivo de áudio gerado')
parser.add_argument('speech_gain', help='Volume da fala')
parser.add_argument('speech_speed', help='Velocidade da fala')
parser.add_argument('passes', help='O total de vezes que será aplicada a velocidade em cima do audio gerado')
parser.add_argument('text_to_speech', nargs='+', help='Texto a ser lido')
args = parser.parse_args()

if args.output_dir is None:
    print("O primeiro parâmetro deve conter o local onde o arquiuvo temporário de áudio será gerado")
    exit(1)

try:
    voice_gain = float(args.speech_gain)
except ValueError:
    print("O segundo argumento deve ser um valor numérico referente ao volume de reprodução da voz, onde 1 representa a velocidade original, 1 representa volume máximo.")
    exit(1)

try:
    voice_speed = float(args.speech_speed)
except ValueError:
    print("O terceiro argumento deve ser um valor numérico referente a velocidade de reprodução da voz, onde 1 representa a velocidade original, 2 representa o dobro.")
    exit(1)

try:
    speed_passes = int(args.passes)
except ValueError:
    print("O quarto argumento deve ser um valor numérico referente ao total de vezes que a velocidade será aplicada no audio gerado")
    exit(1)

if args.text_to_speech is None:
    print("Você deve especificar o texto a ser lido após o quarto parâmetro")
    exit(1)

text_to_speech = ' '.join(args.text_to_speech)
ttsengine = gTTS(text=text_to_speech, lang='pt', slow=False if voice_speed == 1 else True)
output_dir = args.output_dir
output_path = os.path.join(output_dir, "output.mp3")
ttsengine.save(output_path)

if voice_speed == 1:
    play_mp3(output_path)
else:
    play_speeded_mp3(output_path, voice_gain, voice_speed, speed_passes)
