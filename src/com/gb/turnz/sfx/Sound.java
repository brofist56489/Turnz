package com.gb.turnz.sfx;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Sound {
//	public static final Sound Example = new Sound("/example.wav");
//	public static final Midi otherExample = new Midi("/example.mid");

	private AudioClip clip;

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		clip.stop();
	}
	
	public static class Midi {

		Sequence sequence;
		Sequencer sequencer;
		boolean running = false;

		public Midi(String name) {
			try {
				sequence = MidiSystem.getSequence(Sound.class.getResource(name));
				sequencer = MidiSystem.getSequencer();
				sequencer.open();
				sequencer.setSequence(sequence);
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}
		}

		public void play() {
			try {
				new Thread() {
					public void run() {
						sequencer.start();
						running = true;
					}
				}.start();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		public void pause() {
			sequencer.stop();
			running = false;
		}
		
		public void stop() {
			pause();
			sequencer.setMicrosecondPosition(0);
		}
		
		public boolean isRunning() {
			return running;
		}
	}


}
