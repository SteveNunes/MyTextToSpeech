import argparse
import sys
import pygame
from pydub import AudioSegment
from elevenlabs import generate, set_api_key, voices, Voice, VoiceSettings
from io import BytesIO


def play_from_bytes(audio_bytes, output_file, volume=1.0, speed=1.0):
    bytes_io = BytesIO(audio_bytes)
    audio_segment = AudioSegment.from_file(bytes_io)
    audio_segment = audio_segment - (60 - volume * 60)
    if speed != 1.0:
        audio_segment = audio_segment.speedup(playback_speed=speed)
    if output_file != '-':
        audio_segment.export(output_file)
    else:
        temp_audio = BytesIO()
        audio_segment.export(temp_audio)
        temp_audio.seek(0)
        pygame.mixer.init()
        pygame.mixer.music.load(temp_audio)
        pygame.mixer.music.play()
        pygame.time.wait(int(audio_segment.duration_seconds * 1000))

models = ['eleven_multilingual_v1', 'eleven_multilingual_v2', 'eleven_monolingual_v1', 'eleven_turbo_v2']
models2 = f'1 - {models[0]}, 2 - {models[1]}, 3 - {models[1]}, 4 - {models[3]}'
voicesNames = []

if len(sys.argv) > 1 and sys.argv[1] == 'help':
    if len(sys.argv) < 3:
        print('Use: help voices [api key], help models')
    elif sys.argv[2] == 'voices':
        if len(sys.argv) > 3:
            set_api_key(sys.argv[3])
        for voice_info in voices():
            voicesNames.append(voice_info.name)
        print(f'Available voices: {", ".join(voicesNames)}')
    elif sys.argv[2] == 'modelos':
        print(f'Available models: {", ".join(models)}')
    else:
        print('Invalid argument: Use: help voices, help models')
    exit(1)

parser = argparse.ArgumentParser(description='Text-to-speech based on ElevenLabs\'s API')
parser.add_argument('output_file', help='Output folder for the generated audio file. Use \'-\' on this arg, for make the generated audio to be played on-the-fly instead of generating an audio file')
parser.add_argument('api_key', help='API Key (If you don\'t got one or don\'t want to use it, use \'-\'')
parser.add_argument('voice', help='The name of the voice you want to use (help voices)')
parser.add_argument('model', type=int, default=0, help=f'Index of the voice model you want to use (help models)')
parser.add_argument('gain', type=float, default=1, help=f'Voice gain (0.0 = no sound, 1.0 = normal volume, 1.1 > higher than normal)')
parser.add_argument('speed', type=float, default=1, help=f'Voice speed (1 = normal speed, 2 = 2x speed, etc)')
parser.add_argument('stability', type=float, default=0.5, help=f'Voice stability (0.0 to 1.0)')
parser.add_argument('similarity', type=float, default=1, help=f'Voice similarity enhancement (0.0 to 1.0)')
parser.add_argument('style', type=float, default=0, help=f'Voice style exaggeration (0.0 to 1.0)')
parser.add_argument('speaker_boost', type=bool, default=True, help=f'Use speaker boost (False (Faster) or True (Slower))')
parser.add_argument('text', nargs='+', help='Texto a ser lido')
args = parser.parse_args()

if args.model < 1 or args.model > len(models):
    print(f'Invalid index for voice model. Available indexes: {models2}')
    exit(1)

if args.api_key != '-':
    set_api_key(args.api_key)

voicesList = voices()
for voice_info in voicesList:
    voicesNames.append(voice_info.name)

try:
    voice_index = voicesNames.index(args.voice)
except ValueError:
    print(f'Invalid voice name. Available voices: {voicesNames}')
    exit(1)

play_from_bytes(
    generate(
        text=' '.join(args.text),
        model=models[args.model - 1],
        voice=Voice(
            voice_id=voicesList[voice_index].voice_id,
            settings=VoiceSettings(stability=args.stability, similarity_boost=args.similarity, style=args.style,
                                   use_speaker_boost=args.speaker_boost)
        )
    ),
    args.output_file,
    args.gain,
    args.speed
)