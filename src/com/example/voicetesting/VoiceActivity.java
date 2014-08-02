package com.example.voicetesting;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import com.example.voicetesting.VoiceActivity;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class VoiceActivity extends ActionBarActivity {

    TextToSpeech mTts;
	EditText editText1;
	EditText editText2;
	EditText editText3;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		

		editText1 = (EditText)findViewById(R.id.editText1);
		editText2 = (EditText)findViewById(R.id.editText2);
		editText3 = (EditText)findViewById(R.id.editText3);
		mTts = new TextToSpeech(VoiceActivity.this, new TextToSpeech.OnInitListener()
		{
	@Override
      public void onInit(int status) {
         if(status != TextToSpeech.ERROR){
			mTts.setLanguage(Locale.US);
            }		
		
	}
	});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.voice, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_voice,
					container, false);
			return rootView;
		}
	}
	public void speakTts(View view)
	{
		if(editText2.getText() != null){
		mTts.setPitch( Float.parseFloat(editText2.getText().toString()));
		}
		else
		{
			mTts.setPitch((float) 1.0);
		}

		//mTts.speak(editText1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
		synthesizeFile(editText1.getText().toString());

	}

	
public void synthesizeFile(String textToConvert)
{
	HashMap<String, String> myHashRender = new HashMap();

    String exStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    File appTmpPath = new File(exStoragePath + "/sounds/");
    boolean isDirectoryCreated = appTmpPath.mkdirs();
    String tempFilename = editText3.getText().toString()+".wav";
    String tempDestFile = appTmpPath.getAbsolutePath() + File.separator + tempFilename;
	myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, textToConvert);
	mTts.synthesizeToFile(textToConvert, myHashRender, tempDestFile);
}
}
