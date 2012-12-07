package com.vsshv.flightticket;

import java.util.ArrayList;
import java.util.Locale;

import com.vsshv.flightticket.tts.TTSSingleton;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DepartureSelect extends Activity implements OnInitListener{
	
	private TextToSpeech tts;
	private Button top_exit_btn = null;
	private Button button1 = null;
	private Button top_home_btn = null;
	private TextView flightNumber = null;
	private TextView destinationCity = null;
	private TextView fromCity = null;
	private TextView date = null;
	private TextView time = null;
	private TextView price = null;
	private String speakdata1 = "";
	private String speakdata2 = "";
	private String speakdata3 = "";
	private String speakdata4 = "";
	private String speakdata5 = "";
	private String speakdata6 = "";
	
	private ImageButton btnSpeak;
	
	protected static final int RESULT_SPEECH = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.third);
		
		Bundle extras = getIntent().getExtras();
		
		tts = new TextToSpeech(this, this);
		
		top_exit_btn = (Button) findViewById(R.id.top_exit_btn);
		button1 = (Button) findViewById(R.id.button1);
		top_home_btn = (Button) findViewById(R.id.top_home_btn);
		
		flightNumber = (TextView) findViewById(R.id.FlightNumber);
		destinationCity = (TextView) findViewById(R.id.DestinationCity);
		fromCity = (TextView) findViewById(R.id.FromCity);
		date = (TextView) findViewById(R.id.Date);
		time = (TextView) findViewById(R.id.Time);
		price = (TextView) findViewById(R.id.Price);
		
		if(null != extras){
			flightNumber.setText("Flight Number : ABC123456");
			fromCity.setText("From City : "+extras.getString("from"));
			destinationCity.setText("To City : "+extras.getString("to"));
			date.setText("Selected date : "+extras.getString("date"));
			time.setText("Selected time : "+extras.getString("time"));
			price.setText("Fare : $100");
			speakdata1 = "Flight Number : ABC123456 ";
			speakdata2 = "From City : "+extras.getString("from");
			speakdata3 = " To City : "+extras.getString("to");
			speakdata4 = " Selected date : "+extras.getString("date");
			speakdata5 = " Selected time : "+extras.getString("time");
			speakdata6 = " Fare : $100";
		}
		
		top_exit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DepartureSelect.this,PurchaseScreen.class);
				startActivity(intent);	
				finish();
			}
		});
		top_home_btn.setText("Speak");
		top_home_btn.setBackgroundResource(R.drawable.bluebutton);
		top_home_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//if(null != tts){
					if(tts.isSpeaking()){
						top_home_btn.setText("Speak");
						tts.stop();
						top_home_btn.setBackgroundResource(R.drawable.bluebutton);
					}else{
						speakOut();
					}
				//}
				
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

				if(text.get(0).contains("select")){
					Intent intent = new Intent(DepartureSelect.this,PurchaseScreen.class);
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
	protected void onResume() {
		super.onResume();
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
				//speakOut();
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}
	}
	
	private void speakOut()
	{
		top_home_btn.setText("Stop");
		top_home_btn.setBackgroundResource(R.drawable.menu_btn_click);
		tts.speak(speakdata1, TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak(speakdata2, TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak(speakdata3, TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak(speakdata4, TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak(speakdata5, TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak(speakdata6, TextToSpeech.QUEUE_ADD, null);
		//String data = speakdata;
		//TTSSingleton.getInstance(DepartureSelect.this).speakText(data, tts);		
	}

}
