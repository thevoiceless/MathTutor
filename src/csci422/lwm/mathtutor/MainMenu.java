package csci422.lwm.mathtutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import csci422.lwm.mathtutor.NumQuestionsDialogFragment.QuestionsDialogListener;

public class MainMenu extends FragmentActivity implements QuestionsDialogListener, MediaPlayer.OnPreparedListener
{
	public static String NUM_PROBLEMS = "csci422.lwm.mathtutor.NUM_PROBLEMS";
	private static final String MAIN_MENU_SOUND = "jungle.mp3";
	private AssetFileDescriptor soundFile;
	private SharedPreferences prefs;
	private MediaPlayer player = new MediaPlayer();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		setDataMembers();
		
		// Prepare the sound file
		prepareSound();
	}
	
	@Override
	public void onPause()
	{
		prefs.unregisterOnSharedPreferenceChangeListener(onPreferenceChange);
		// Pause and return to the beginning of the sound file
		player.pause();
		player.seekTo(0);
		super.onPause();
	}
	
	@Override
	public void onResume()
	{
		// Start playing sound if not already
		if (!player.isPlaying() && prefs.getBoolean(getString(R.string.key_use_sound), false))
		{
			player.start();
		}
		super.onResume();
	}
	
	@Override
	public void onDestroy()
	{
		player.release();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		// If sound is enabled, set menu entry to turn it off
		if (prefs.getBoolean(getString(R.string.key_use_sound), false))
		{
			menu.findItem(R.id.menu_toggle_sound).setIcon(R.drawable.ic_audio_vol_mute);
			menu.findItem(R.id.menu_toggle_sound).setTitle(R.string.toggle_sound_off);
		}
		// Otherwise, set menu entry to enable it
		else
		{
			menu.findItem(R.id.menu_toggle_sound).setIcon(R.drawable.ic_audio_vol);
			menu.findItem(R.id.menu_toggle_sound).setTitle(R.string.toggle_sound_on);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.menu_toggle_sound)
		{
			boolean useSound = prefs.getBoolean(getString(R.string.key_use_sound), false);
			Editor editor = prefs.edit();
			editor.putBoolean(getString(R.string.key_use_sound), !useSound);
			editor.commit();
			invalidateOptionsMenu();
		}
		if (item.getItemId() == R.id.menu_settings)
		{
			startActivity(new Intent(this, EditPreferences.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setDataMembers()
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(onPreferenceChange);
	}
	
	public void startEndless(View v)
	{
		Intent i = new Intent(this, MathTutor.class);
		startActivity(i);
	}
	
	public void selectQuizLength(View v)
	{
		int position = 0;
		DialogFragment numQuestionsDialog = new NumQuestionsDialogFragment();
		Bundle b  = new Bundle();
        /** Storing the selected item's index in the bundle object */
        b.putInt("position", position);
        /** Setting the bundle object to the dialog fragment object */
        numQuestionsDialog.setArguments(b);
		numQuestionsDialog.show(getSupportFragmentManager(), getString(R.string.quiz_length));
	}

	@Override
	public void onDialogPositiveClick(int quizLength)
	{
		Toast.makeText(this, "Selected " + quizLength, Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, MathTutor.class);
		i.putExtra(NUM_PROBLEMS, quizLength);		
		startActivity(i);
	}
	
	public void showHowto(View v)
	{
		Intent i = new Intent(this, HowTo.class);
		startActivity(i);
	}
	
	public void showProgress(View v) {
		Intent i = new Intent(this, ProgressActivity.class);
		startActivity(i);
	}
	
	private void prepareSound()
	{
		try
		{
			// Get file descriptor of sound file from assets
			soundFile = getAssets().openFd(MAIN_MENU_SOUND);
			// Set data source and stream type
			player.setDataSource(soundFile.getFileDescriptor(), soundFile.getStartOffset(), soundFile.getLength());
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// Prepare
			player.setOnPreparedListener(this);
			if (prefs.getBoolean(getString(R.string.key_use_sound), false))
			{
				player.prepareAsync();
			}
		}
		catch (Exception e)
		{
			Log.e(getString(R.string.app_name), "Exception while attempting to play mp3", e);
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp)
	{
		// Start playing immediately once prepared
		player.start();
	}
	
	SharedPreferences.OnSharedPreferenceChangeListener onPreferenceChange = new SharedPreferences.OnSharedPreferenceChangeListener()
	{
		public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
		{
			if (getString(R.string.key_use_sound).equals(key))
			{
				if (prefs.getBoolean(key, false))
				{
					player.release();
					player = new MediaPlayer();
					prepareSound();
				}
				else
				{
					player.pause();
					player.seekTo(0);
				}
				
			}
		}
	};

}
