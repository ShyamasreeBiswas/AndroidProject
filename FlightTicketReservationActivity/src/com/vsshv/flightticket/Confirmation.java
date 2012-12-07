package com.vsshv.flightticket;

import java.util.Locale;

import com.vsshv.flightticket.tts.TTSSingleton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Confirmation extends Activity implements OnInitListener{
	
	private TextToSpeech tts;
	private Button top_exit_btn = null;
	private Button top_home_btn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.conformation);
		
		tts = new TextToSpeech(this, this);
		
		top_exit_btn = (Button) findViewById(R.id.top_exit_btn);
		top_home_btn = (Button) findViewById(R.id.top_home_btn);
		top_home_btn.setVisibility(View.VISIBLE);
		top_exit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		top_home_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Confirmation.this,FlightTicketReservationActivity.class);
				startActivity(intent);	
				finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//speakOut();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);

			// tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				speakOut();
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}
	}
	
	private void speakOut()
	{
		String data = "Your Confirmation code is : 3456 and Thank you for Choosing ABC";
		tts.speak("Your Confirmation code is : 3456", TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak("and Thank you for Choosing ABC", TextToSpeech.QUEUE_ADD, null);
		//TTSSingleton.getInstance(Confirmation.this).speakText(data, tts);		
	}
}
