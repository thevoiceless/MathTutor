package csci422.lwm.mathtutor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MathTutor extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_tutor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_math_tutor, menu);
        return true;
    }
}
