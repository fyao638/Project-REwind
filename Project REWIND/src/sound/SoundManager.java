package sound;

import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;

public class SoundManager implements JayLayerListener {

	JayLayer sound;
	
	public SoundManager() {
		String[] songs = new String[]{"title2.mp3"};
		String[] soundEffects = new String[] {"flash.wav"};
		
		 sound=new JayLayer("audio/","audio/",false);
		  sound.addPlayList();
		  sound.addSoundEffects(soundEffects);
		  sound.addSongs(0,songs);
		  sound.changePlayList(0);
		  sound.addJayLayerListener(this);
	}
	
	public void playMenuMusic() {
		 sound.nextSong();
	}
	public void stopMusic() {
		if(sound.isPlaying()) {
			sound.stopSong();
		}
	}
	public void playFlash() {
		sound.playSoundEffect(0);
	}
	
	@Override
	public void musicStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void musicStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playlistEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void songEnded() {
		// TODO Auto-generated method stub
		
	}

}
