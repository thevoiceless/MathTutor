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
import android.view.View;
import android.widget.RadioButton;

public class ProgressActivity extends Activity {

	private ProgressHelper helper = new ProgressHelper(this);
	private String difficulty = "";
	private String type = "";
	private String difficulty_title = "";
	private String type_title = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);

	}

	private void startPieChart() {
		Cursor c = helper.getProgressStats(difficulty + type);
		c.moveToFirst();

		CategorySeries data = new CategorySeries("Data");
		data.add("Correct (" + c.getInt(1) + ")", c.getInt(1));
		data.add("Two Guesses (" + c.getInt(2) + ")", c.getInt(2));
		data.add("Three Guesses (" + c.getInt(3) + ")", c.getInt(3));
		data.add("Four Guesses (" + c.getInt(4) + ")", c.getInt(4));

		int[] colors = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.RED };
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		renderer.setZoomButtonsVisible(false);
		renderer.setZoomEnabled(false);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setInScroll(false);
		renderer.setChartTitleTextSize(20);

		StringBuilder title = new StringBuilder("Progress (");
		if (difficulty_title != "" || type_title != "") {
			title
				.append(difficulty_title)
				.append(" ")
				.append(type_title)
				.append(") (");
		}
		title.append("Total: " + c.getInt(0) + ")");
		
		startActivity(ChartFactory.getPieChartIntent(this, data, renderer, title.toString()));
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

	public void onChangeDiff(View view) {
		boolean checked = ((RadioButton) view).isChecked();

		switch (view.getId()) {
		case R.id.diff_any:
			if (checked) {
				difficulty = "";
				difficulty_title = "";
			}
			break;
		case R.id.diff_easy:
			if (checked) {
				difficulty = " AND diff = " + MathProblemGenerator.EASY;
				difficulty_title = "Easy";
			}
			break;
		case R.id.diff_medium:
			if (checked) {
				difficulty = " AND diff = " + MathProblemGenerator.MEDIUM;
				difficulty_title = "Medium";
			}
			break;
		case R.id.diff_hard:
			if (checked) {
				difficulty = " AND diff = " + MathProblemGenerator.HARD;
				difficulty_title = "Hard";
			}
			break;
		}
	}

	public void onChangeType(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		
		switch (view.getId()) {
		case R.id.type_any:
			if (checked) {
				type = "";
				type_title = "";
			}
			break;
		case R.id.type_add:
			if (checked) {
				type = " AND (type = " + MathProblemGenerator.ADDITION + ")";
				type_title = "Addition";
			}
			break;
		case R.id.type_sub:
			if (checked) {
				type = " AND (type = " + MathProblemGenerator.SUBTRACTION + ")";
				type_title = "Subtraction";
			}
			break;
		}
	}

	public void generateStats(View view) {
		startPieChart();
	}

}
