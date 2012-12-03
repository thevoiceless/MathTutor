package csci422.lwm.mathtutor;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

public class ProgressActivity extends Activity {
	
	private ProgressHelper helper = new ProgressHelper(this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Cursor c = helper.numberOfStoredProblems();
        c.moveToFirst();
        
        CategorySeries data = new CategorySeries("Data");
        data.add("Correct", c.getInt(1));
        data.add("Two Guesses", c.getInt(2));
        data.add("Three Guesses", c.getInt(3));
        data.add("Four Guesses", c.getInt(4));
        //data.add("Total", c.getInt(0));
        
        int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED };
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(false);
        renderer.setZoomEnabled(false);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setInScroll(false);
        renderer.setChartTitleTextSize(20);
        
        startActivity(ChartFactory.getPieChartIntent(this, data, renderer, "Progress"));
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_progress, menu);
        return true;
    }
    
    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(25);
        renderer.setLegendTextSize(25);
        renderer.setMargins(new int[] { 20, 30, 15, 0 });
        for (int color : colors) {
          SimpleSeriesRenderer r = new SimpleSeriesRenderer();
          r.setColor(color);
          renderer.addSeriesRenderer(r);
        }
        return renderer;
      }
}
