package csci422.lwm.mathtutor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class EditPreferences extends PreferenceActivity
{
	SharedPreferences prefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(onChange);
	}
	
	@Override
	public void onPause()
	{
		prefs.unregisterOnSharedPreferenceChangeListener(onChange);
		super.onPause();
	}
	
	SharedPreferences.OnSharedPreferenceChangeListener onChange = new SharedPreferences.OnSharedPreferenceChangeListener()
	{
		public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
		{
			if (getString(R.string.key_use_sound).equals(key))
			{
				boolean enabled = prefs.getBoolean(key, false);
				Toast.makeText(EditPreferences.this, "Use sound: " + String.valueOf(enabled), Toast.LENGTH_SHORT).show();
			}
		}
	};

}
