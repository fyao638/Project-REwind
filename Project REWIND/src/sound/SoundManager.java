package sound;

import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;

/**
 * 
 * @author Frank Yao
 * @version 1.0
 * This class manages sounds in the game. 
 * - only contains music and a broken laugh method
 *
 */
public class SoundManager implements JayLayerListener {

	JayLayer sound;
	
	public SoundManager() {
		//badnames
		String[] playMusic = new String[]{"title2.mp3"};
		String[] menuMusic = new String[]{"menu.mp3"};
		String[] soundEffects = new String[] {"laugh.mp3"};
		
		 sound=new JayLayer("audio/","audio/",false);
		  sound.addPlayList();
		  sound.addPlayList();
		
		  sound.addSoundEffects(soundEffects);
		  sound.addSongs(0,menuMusic);
		  sound.addSongs(1, playMusic);
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
	public void laugh() {
		sound.playSoundEffect(0);
	}
	public void next() {
		sound.changePlayList(1);
		sound.nextSong();
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
