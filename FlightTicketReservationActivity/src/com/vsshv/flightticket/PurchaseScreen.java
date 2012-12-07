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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.vsshv.flightticket.tts.TTSSingleton;

public class PurchaseScreen extends Activity implements OnInitListener{
	
	private EditText credit_edit = null;
	private Spinner exp_edit_mnth = null;
	private Spinner exp_edit_year = null;
	private Button confirm_btn = null;
	private Button top_exit_btn = null;
	private TextToSpeech tts;
	private String cardNum = "";
	private String expDate = "";
	private String expyear = "";
	private Button top_home_btn = null;
	private ImageButton btnSpeak;
	private ImageButton cardBtnSpeak;
	private ImageButton expMnthBtnSpeak;
	private ImageButton expYearBtnSpeak;
	
	protected static final int RESULT_SPEECH = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		setContentView(R.layout.purchase);
		
		tts = new TextToSpeech(this, this);
		
		credit_edit = (EditText) findViewById(R.id.credit_edit);
		exp_edit_mnth = (Spinner) findViewById(R.id.exp_edit_mnth);
		exp_edit_year = (Spinner) findViewById(R.id.exp_edit_year);
		confirm_btn = (Button) findViewById(R.id.confirm_btn);
		top_exit_btn = (Button) findViewById(R.id.top_exit_btn);
		top_home_btn = (Button) findViewById(R.id.top_home_btn);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.card_month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exp_edit_mnth.setAdapter(adapter);
        exp_edit_mnth.setSelection(0);
        exp_edit_mnth.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        //showToast("Spinner1: position=" + position + " id=" + id);
                    	expDate = (String)exp_edit_mnth.getItemAtPosition(position);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                       // showToast("Spinner1: unselected");
                    }
                });
        
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.card_year, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exp_edit_year.setAdapter(adapter2);
        exp_edit_year.setSelection(0);
        exp_edit_year.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        //showToast("Spinner1: position=" + position + " id=" + id);
                    	expyear = (String)exp_edit_year.getItemAtPosition(position);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                       // showToast("Spinner1: unselected");
                    }
                });
		
		top_exit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		confirm_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				purchaseConfirm();
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
						top_home_btn.setBackgroundResource(R.drawable.bluebutton);
						tts.stop();
					}else{
						tts.setLanguage(Locale.US);
						speakOut();
					}
				//}
				
			}
		});
		
		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				speakAction(0);
			}
		});
		
		cardBtnSpeak = (ImageButton) findViewById(R.id.cardbtnSpeak);
		cardBtnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				speakAction(1);
			}
		});
		expMnthBtnSpeak = (ImageButton) findViewById(R.id.mnthbtnSpeak);
		expMnthBtnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				speakAction(2);
			}
		});
		expYearBtnSpeak = (ImageButton) findViewById(R.id.yearbtnSpeak);
		expYearBtnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				speakAction(3);
			}
		});
	}
	
	private void speakAction(int RESULT_ID)
	{
		Intent intent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

		try {
			startActivityForResult(intent, RESULT_ID);
		} catch (ActivityNotFoundException a) {
			Toast t = Toast.makeText(getApplicationContext(),
					"Ops! Your device doesn't support Speech to Text",
					Toast.LENGTH_SHORT);
			t.show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 0: 
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				setTextToViews(0,text);
				
			}
			break;
		case 1: 
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				setTextToViews(1,text);
				
			}
			break;
		case 2: 
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				setTextToViews(2,text);
				
			}
			break;
		case 3: 
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				setTextToViews(3,text);
				
			}
			break;
		}
	}
	
	private void purchaseConfirm()
	{
		cardNum = credit_edit.getText().toString().trim();
		//expDate = exp_edit_mnth.getText().toString().trim();
		if(cardNum.equals("")){
			credit_edit.setError("Please enter your CardNumber");
			return;
		}else if(new StringBuffer(cardNum).length() < 16){
			credit_edit.setError("Card number must be 16 digits");
			return;
		}/*
		
		int place = -1;
		String[] months = getResources().getStringArray(R.array.card_month);
		for(int i=0;i<months.length;i++){
			if(expDate.contains(months[i].toLowerCase())){
				place = i;
				break;
			}
		}
		if(place == -1){
			Toast.makeText(PurchaseScreen.this, "Exp. month doesn't exists", Toast.LENGTH_SHORT).show();
			return;
		}
		
		int place2 = -1;
		String[] years = getResources().getStringArray(R.array.card_year);
		for(int i=0;i<years.length;i++){
			if(expyear.contains(years[i].toLowerCase())){
				place2 = i;
				break;
			}
		}
		if(place2 == -1){
			Toast.makeText(PurchaseScreen.this, "Exp. year doesn't exists", Toast.LENGTH_SHORT).show();
			return;
		}*/
		
		playAndGotoConfirm();
		
	}
	
	TextWatcher watcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	private void playAndGotoConfirm()
	{		
		Intent intent = new Intent(PurchaseScreen.this,Confirmation.class);
		startActivity(intent);	
		finish();
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
				//confirm_btn.setEnabled(true);
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
		//String data = "Your card number is "+cardNum.toString()+" and expiry month is "+
					//	expDate.toString()+" and year is "+expyear.toString();
		tts.speak("Your card number is "+cardNum, TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak(" and expiry month is "+expDate, TextToSpeech.QUEUE_ADD, null);
		tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
		tts.speak(" and year is "+expyear, TextToSpeech.QUEUE_ADD, null);
		//TTSSingleton.getInstance(PurchaseScreen.this).speakText(data, tts);		
	}
	
	private void setTextToViews(int id,ArrayList<String> data)
	{
		if(null != data){
			Toast.makeText(PurchaseScreen.this, data.get(0), 300).show();
			switch(id)
			{
			case 2:
				//exp_edit_mnth.setText(data.get(0));
				//expDate = data.get(0);
				
				break;
				
			case 3:
				//exp_edit_year.setText(data.get(0));
				//expyear = data.get(0);
				
				break;
			case 1:
				credit_edit.setText(data.get(0));
				break;
			case 0:
				Toast t = Toast.makeText(getApplicationContext(),
						data.get(0),
						Toast.LENGTH_SHORT);
				t.show();
				if(data.get(0).contains("confirm payment")){
					purchaseConfirm();
				}
				break;
			}
			}else{
				Toast.makeText(PurchaseScreen.this, "Wrong input, speech again", 400).show();
			}
	}
}
