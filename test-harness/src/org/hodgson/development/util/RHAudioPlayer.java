package org.hodgson.development.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * Cedar Software All rights conferred by the law of copyright and by virtue of
 * international copyright conventions are reserved to Cedar, Inc. Use
 * duplication or sale of this product except as described in the licence
 * agreement is strictly prohibited. Violation may lead to prosecution. Copright
 * 2004, Cedar, Inc. All Rights Reserved.
 */
/**
 * @author <a href="mailto:Ralph.Hodgson@cedar.com">Ralph Hodgson
 * @since v5.1 Description -
 */
public class RHAudioPlayer
{
	private File mFile;

	/**
	 * 
	 */
	public RHAudioPlayer()
	{
		super();
	}

	public void playMidi(String file)
	{
		try
		{
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();

			// From file
			Sequence sequence = MidiSystem.getSequence(new File(file));

			/*
			 * // From URL sequence = MidiSystem.getSequence(new URL( "http://hostname/midifile"));
			 */
			sequencer.setSequence(sequence);

			// Start playing.
			sequencer.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void playSample(String file)
	{
		try
		{
			mFile = new File(file);
			// From file
			AudioInputStream stream = AudioSystem.getAudioInputStream(mFile);
			// At present, ALAW and ULAW encodings must be converted
			// to PCM_SIGNED before it can be played.
			AudioFormat format = stream.getFormat();
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
			{
				format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
						format.getSampleRate(),
						format.getSampleSizeInBits() * 2, format.getChannels(),
						format.getFrameSize() * 2, format.getFrameRate(), true); // big endian
				stream = AudioSystem.getAudioInputStream(format, stream);
			}
			DataLine.Info info = new DataLine.Info(Clip.class, stream
					.getFormat(),
			// The next two lines should be in one line.
					((int) stream.getFrameLength() * format.getFrameSize()));
			Clip clip = (Clip) AudioSystem.getLine(info);
			// This method does not return until the audio file is completely
			// loaded.
			clip.open(stream);
			// Start playing.
			clip.start();
		}
		catch (MalformedURLException e)
		{
		}
		catch (IOException e)
		{
		}
		catch (LineUnavailableException e)
		{
		}
		catch (UnsupportedAudioFileException e)
		{
		}
	}
}