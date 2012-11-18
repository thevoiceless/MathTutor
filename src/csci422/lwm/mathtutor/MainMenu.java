package csci422.lwm.mathtutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}
	
	public void startGame(View v)
	{
		Intent i = new Intent(this, MathTutor.class);
		startActivity(i);
	}

}
