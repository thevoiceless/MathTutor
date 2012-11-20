package csci422.lwm.mathtutor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends FragmentActivity
{
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
		DialogFragment numQuestionsDialog = new NumQuestionsDialogFragment();
		numQuestionsDialog.show(getSupportFragmentManager(), getString(R.string.quiz_length));
	}
	
	private class NumQuestionsDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			int selected = 0;
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.dialog_quiz_number_of_probs);
			builder.setSingleChoiceItems(R.array.numberOfQuizProblems, selected, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Toast.makeText(MainMenu.this, "Selected " + getResources().getStringArray(R.array.numberOfQuizProblems)[which], Toast.LENGTH_LONG).show();
					}
				});
			builder.setPositiveButton(R.string.dialog_quiz_start, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Intent i = new Intent(MainMenu.this, MathTutor.class);
						startActivity(i);
					}
				});
			builder.setNegativeButton(R.string.dialog_quiz_cancel, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						
					}
				});
			return builder.create();
		}
	}

}
