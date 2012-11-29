package csci422.lwm.mathtutor;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import csci422.lwm.mathtutor.NumQuestionsDialogFragment.QuestionsDialogListener;

public class MainMenu extends FragmentActivity implements QuestionsDialogListener, MediaPlayer.OnPreparedListener
{
	public static String NUM_PROBLEMS = "csci422.lwm.mathtutor.NUM_PROBLEMS";
	private static final String MAIN_MENU_SOUND = "jungle.mp3";
	private MediaPlayer player = new MediaPlayer();
	private AssetFileDescriptor soundFile;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		// Prepare the sound file
		prepareSound();
	}
	
	@Override
	public void onPause()
	{
		// Pause and return to the beginning of the sound file
		player.pause();
		player.seekTo(0);
		super.onPause();
	}
	
	@Override
	public void onResume()
	{
		// Start playing sound if not already
		if (!player.isPlaying())
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
			player.prepareAsync();
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

}
