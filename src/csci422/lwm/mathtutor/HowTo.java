package csci422.lwm.mathtutor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

public class HowTo extends Activity
{
	private HowToView howTo;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_howto);
		howTo = new HowToView(this);
		setContentView(howTo);
//		ScrollView scroll = new ScrollView(this);
//		scroll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
//		                                             LayoutParams.FILL_PARENT));
//		scroll.addView(howTo);
//		setContentView(scroll);
	}
	
	private class HowToView extends View
	{
		private Bitmap howToImage;
		
		public HowToView(Context context)
		{
			super(context);
			howToImage = BitmapFactory.decodeResource(getResources(), R.drawable.howto);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawBitmap(howToImage, 0, 0, null);
		}
		
	}
}
