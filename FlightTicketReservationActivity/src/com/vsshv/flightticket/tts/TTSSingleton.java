package com.vsshv.flightticket.tts;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class TTSSingleton{
	
	private static TTSSingleton tts = null;
	
	public static TTSSingleton getInstance(Context ctx)
	{
		if(null == tts){
			tts = new TTSSingleton();
		}
		
		return tts;
	}

	public void speakText(String data, TextToSpeech ttsObj)
	{
		ttsObj.speak(data, TextToSpeech.QUEUE_FLUSH, null);
	}
	
}
