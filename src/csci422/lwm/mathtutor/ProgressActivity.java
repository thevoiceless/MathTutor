package csci422.lwm.mathtutor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ProgressActivity extends Activity {
	
	private ProgressHelper helper = new ProgressHelper(this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        
        TextView tv = (TextView)findViewById(R.id.progressText);
        tv.setText("Total number of problems completed in a quiz: " 
        		+ helper.numberOfStoredProblems()
        		+ ", Total number of problems correct in a quiz: "
        		+ helper.numberOfCorrectProblems());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_progress, menu);
        return true;
    }
}
