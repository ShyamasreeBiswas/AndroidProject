package com.vsshv.flightticket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vsshv.flightticket.tts.TTSSingleton;

public class SelectRoutes extends Activity implements OnInitListener{
	
	private TextToSpeech tts;
	private Button top_exit_btn = null;
	private Button top_home_btn = null;
	private EditText editText2 = null;
	private EditText EditText01 = null;
	private Button button1 = null;
	private String from = "";
	private String to = "";
	private Button datePicker = null;
	private Button timePicker = null;
	private String date = "";
	private String time = "";
	private int cyear;
	private int cmonth;
	private int cday;
	private int hour;
	private int minute;
	
	private ImageButton btnSpeak;
	private ImageButton frombtnSpeak;
	private ImageButton tobtnSpeak;
	private ImageButton datebtnSpeak;
	private ImageButton timebtnSpeak;
	
	protected static final int RESULT_SPEECH = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.second);
		
		tts = new TextToSpeech(this, this);
		
		top_exit_btn = (Button) findViewById(R.id.top_exit_btn);
		editText2 = (EditText) findViewById(R.id.editText2);
		EditText01 = (EditText) findViewById(R.id.EditText01);
		button1 = (Button) findViewById(R.id.button1);
		top_home_btn = (Button) findViewById(R.id.top_home_btn);
		datePicker = (Button) findViewById(R.id.DepartureDate);
		timePicker = (Button) findViewById(R.id.timePicker1);
		
		/*//editText2.setAdapter(new Arr)
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.from_cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editText2.setAdapter(adapter);
        //editText2.setSelection(0);
        editText2.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        //showToast("Spinner1: position=" + position + " id=" + id);
                    	from = (String)editText2.getItemAtPosition(position);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                       // showToast("Spinner1: unselected");
                    }
                });
        adapter = ArrayAdapter.createFromResource(this, R.array.to_cities,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditText01.setAdapter(adapter);
        //EditText01.setSelection(0);
        EditText01.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        //showToast("Spinner2: position=" + position + " id=" + id);
                    	to = (String)EditText01.getItemAtPosition(position);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //showToast("Spinner2: unselected");
                    }
                });*/
		
		Calendar c = Calendar.getInstance();
		cyear = c.get(Calendar.YEAR);
		cmonth = c.get(Calendar.MONTH);
		cday = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		
		//timePicker.setCurrentHour(hour);
		//timePicker.setCurrentMinute(minute);
		
		datePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		timePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		
		top_exit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		button1.setOnClickListener(new OnClickListener() {
			
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
		
		frombtnSpeak = (ImageButton) findViewById(R.id.frombtnSpeak);
		frombtnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {				
				speakAction(1);				
			}
		});
		tobtnSpeak = (ImageButton) findViewById(R.id.tobtnSpeak);
		tobtnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {				
				speakAction(2);				
			}
		});
		datebtnSpeak = (ImageButton) findViewById(R.id.datebtnSpeak);
		datebtnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {				
				speakAction(3);				
			}
		});
		timebtnSpeak = (ImageButton) findViewById(R.id.timebtnSpeak);
		timebtnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {				
				speakAction(4);				
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
			
		case 4: 
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				setTextToViews(4,text);
				
			}
			break;
		

		}
	}
	
	DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// onDateSet method
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			/*if(cyear > year){
				Toast.makeText(SelectRoutes.this, "Year must be greater than current year", Toast.LENGTH_SHORT).show();
			}else if(cmonth > monthOfYear){
				Toast.makeText(SelectRoutes.this, "Month must be greater than current month", Toast.LENGTH_SHORT).show();
			}else if(cday > dayOfMonth && cmonth > monthOfYear){
				Toast.makeText(SelectRoutes.this, "Day must be greater than current day", Toast.LENGTH_SHORT).show();
			}*/
			
			if(!isDateAfter(view)){
				Toast.makeText(SelectRoutes.this,"Date must be greater than current date.", Toast.LENGTH_SHORT).show();
			}
			else{
				date = String.valueOf(monthOfYear+1)+" / "+String.valueOf(dayOfMonth)+" / "+String.valueOf(year);
				Toast.makeText(SelectRoutes.this, "Selected Date is ="+date, Toast.LENGTH_SHORT).show();
				datePicker.setText(date);
			}		
		}
	};
	
	private boolean isDateAfter(DatePicker tempView) {
        Calendar mCalendar = Calendar.getInstance();
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(tempView.getYear(), tempView.getMonth(), tempView.getDayOfMonth(), 0, 0, 0);
        if(tempCalendar.after(mCalendar))
            return true;
        else 
            return false;
    }
	
	TimePickerDialog.OnTimeSetListener mTimeSetListener = new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			time = new StringBuilder().append(pad(hourOfDay))
					.append(":").append(pad(minute)).toString();
			Toast.makeText(SelectRoutes.this, "Selected Time is ="+time, Toast.LENGTH_SHORT).show();
			timePicker.setText(time);
			// set current time into timepicker
			//timePicker.setCurrentHour(hourOfDay);
			//timePicker.setCurrentMinute(minute);
		}
	};
	
	static final int TIME_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID = 888;
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, 
					mTimeSetListener, hour, minute,false);
			
		case DATE_DIALOG_ID:
			// set time picker as current time
			return new DatePickerDialog(this,  mDateSetListener,  cyear, cmonth, cday);
 
		}
		return null;
	}
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	private void purchaseConfirm()
	{
		from = editText2.getText().toString().trim();
		to = EditText01.getText().toString().trim();
		if(from.contains("Cities")){
			//editText2.setError("Please enter from");
			Toast.makeText(SelectRoutes.this, "Please select from city", Toast.LENGTH_SHORT).show();
			return;
		}else if(to.contains("Cities")){
			//EditText01.setError("Please enter to");
			Toast.makeText(SelectRoutes.this, "Please select to city", Toast.LENGTH_SHORT).show();
			return;
		}else if(date.equals("")){
			Toast.makeText(SelectRoutes.this, "Please select date", Toast.LENGTH_SHORT).show();
			return;
		}else if(time.equals("")){
			Toast.makeText(SelectRoutes.this, "Please select time", Toast.LENGTH_SHORT).show();
			return;
		}
		
		int place = -1;
		String[] cities = getResources().getStringArray(R.array.from_cities);
		for(int i=0;i<cities.length;i++){
			if(from.equalsIgnoreCase(cities[i])){
				place = i;
				break;
			}
		}
		if(place == -1){
			Toast.makeText(SelectRoutes.this, "From city doesn't exists", Toast.LENGTH_SHORT).show();
			return;
		}
		
		int place2 = -1;
		String[] toCities = getResources().getStringArray(R.array.to_cities);
		for(int i=0;i<toCities.length;i++){
			if(to.equalsIgnoreCase(toCities[i])){
				place2 = i;
				break;
			}
		}
		if(place2 == -1){
			Toast.makeText(SelectRoutes.this, "To city doesn't exists", Toast.LENGTH_SHORT).show();
			return;
		}
		
		playAndGotoDeparture();
		
	}
	
	private void playAndGotoDeparture()
	{		
		Intent intent = new Intent(SelectRoutes.this,DepartureSelect.class);
		intent.putExtra("date", date);
		intent.putExtra("time", time);
		intent.putExtra("to", to);		
		intent.putExtra("from", from);
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

			 //tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				//speakOut();
			}

		} else {
			Log.e("TTS", "Initialization Failed");
		}
	}
	
	private void speakOut()
	{
		top_home_btn.setText("Stop");
		top_home_btn.setBackgroundResource(R.drawable.menu_btn_click);
		String data = "";
		if(from.contains("Cities")){
			//editText2.setError("Please enter from");
			//Toast.makeText(SelectRoutes.this, "Please select from city", Toast.LENGTH_SHORT).show();
			data = "Please select from city";
			tts.speak(data, TextToSpeech.QUEUE_ADD, null);
			//return;
		}else if(to.contains("Cities")){
			//EditText01.setError("Please enter to");
			//Toast.makeText(SelectRoutes.this, "Please select to city", Toast.LENGTH_SHORT).show();
			data = "Please select to city";
			tts.speak(data, TextToSpeech.QUEUE_ADD, null);
			//return;
		}else if(date.equals("")){
			//Toast.makeText(SelectRoutes.this, "Please select date", Toast.LENGTH_SHORT).show();
			data = "Please select date";
			tts.speak(data, TextToSpeech.QUEUE_ADD, null);
			//return;
		}else if(time.equals("")){
			data = "Please select time";
			tts.speak(data, TextToSpeech.QUEUE_ADD, null);
			//return;
		}else{
			tts.speak("Your ticket from "+from, TextToSpeech.QUEUE_ADD, null);
			tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
			tts.speak(" to "+to, TextToSpeech.QUEUE_ADD, null);
			tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
			tts.speak(" on "+date, TextToSpeech.QUEUE_ADD, null);
			tts.playSilence(300, TextToSpeech.QUEUE_ADD, null);
			tts.speak(" at "+time, TextToSpeech.QUEUE_ADD, null);
			//data = "Your ticket from "+from+" to "+to+" on "+date+" at "+time;
		}		
		
		//TTSSingleton.getInstance(SelectRoutes.this).speakText(data, tts);		
	}
	
	private void setTextToViews(int id,ArrayList<String> data)
	{
		if(null != data){
			Toast.makeText(SelectRoutes.this, data.get(0), 300).show();
		switch(id)
		{
		case 1:
			editText2.setText(data.get(0));
			from = data.get(0);
			break;
			
		case 2:
			EditText01.setText(data.get(0));
			to = data.get(0);
			
			break;
		case 0:			
			if(data.get(0).equalsIgnoreCase("next")){
				purchaseConfirm();
			}
			break;
		}
		}else{
			Toast.makeText(SelectRoutes.this, "Wrong input, speech again", 400).show();
		}
	}

}
