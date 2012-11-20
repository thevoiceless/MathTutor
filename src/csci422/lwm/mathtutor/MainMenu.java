package csci422.lwm.mathtutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import csci422.lwm.mathtutor.NumQuestionsDialogFragment.QuestionsDialogListener;

public class MainMenu extends FragmentActivity implements QuestionsDialogListener
{
	public static String NUM_PROBLEMS = "csci422.lwm.mathtutor.NUM_PROBLEMS";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
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

}
