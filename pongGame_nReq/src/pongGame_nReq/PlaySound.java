package pongGame_nReq;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySound {
	Clip clip = null;
	void playSound(String soundFile) {
	    try{
	    	File f = new File("./" + soundFile);
		    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
		    clip = AudioSystem.getClip();
		    
		    	 clip.open(audioIn);
				 clip.start();	
		    
		 
		   
	    }
	    catch(Exception e1){
	    	
	    }
	}
	
	
	

}
