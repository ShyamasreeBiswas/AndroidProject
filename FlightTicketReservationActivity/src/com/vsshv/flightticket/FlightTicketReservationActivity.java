package com.vsshv.flightticket;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vsshv.flightticket.tts.TTSSingleton;

public class FlightTicketReservationActivity extends Activity implements OnInitListener{
    /** Called when the activity is first created. */
	
	private Button button1 = null;
	private TextToSpeech tts;
	private ImageButton btnSpeak;
	
	protected static final int RESULT_SPEECH = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first);
        
        tts = new TextToSpeech(this, this);
        
        button1 = (Button) findViewById(R.id.button1);
        
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FlightTicketReservationActivity.this,SelectRoutes.class);
				startActivity(intent);	
				finish();
			}
		});
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Ops! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				Toast t = Toast.makeText(getApplicationContext(),
						text.get(0),
						Toast.LENGTH_SHORT);
				t.show();
				if(text.get(0).contains("book ticket")){
					Intent intent = new Intent(FlightTicketReservationActivity.this,SelectRoutes.class);
					startActivity(intent);	
					finish();
				}
			}
			break;
		}

		}
	}
    
	@Override
	protected void onDestroy() {
		/*if (tts != null) {
			tts.stop();
			tts.shutdown();
		}*/
		super.onDestroy();
	}
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);

			 //tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported"); 
				Intent installIntent = new Intent();
	            installIntent.setAction(
	                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	            startActivity(installIntent);
			} //else {
				speakOut();
			//}

		} else {
			Log.e("TTS", "Initilization Failed");
		}
	}
	
	private void speakOut()
	{
		//String data = "WELCOME TO ABC AIRLINES"+"  Please click the button to proceed or say book ticket";
		tts.speak("WELCOME TO ABC AIRLINES", TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak(" Please click the button to proceed or say book ticket", TextToSpeech.QUEUE_ADD, null);
		//TTSSingleton.getInstance(FlightTicketReservationActivity.this).speakText(data, tts);		
	}
}