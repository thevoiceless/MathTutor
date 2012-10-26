package csci422.lwm.mathtutor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MathTutor extends Activity
{
	public static String DEBUG_TAG = "mathtutorTest";
	
	private static int NUM_BANANAS = 4;
	private boolean firstRun;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new MathView(this));
		firstRun = true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_math_tutor, menu);
		return true;
	}

	private class MathView extends View
	{
		private Banana[] bananas = new Banana[NUM_BANANAS];
		private Rect[] bananaRects = new Rect[NUM_BANANAS];
		int[] xcoords = {150, 400, 150, 400};
		int[] ycoords = {100, 100, 300, 300};
		private boolean bananaSelected;
		private Banana selectedBanana;
		private Bitmap monkey, tree;
		private int canvasWidth, canvasHeight, monkeyX, monkeyY, origBananaX, origBananaY;
		private Rect scaledTree;

		public MathView(Context context)
		{
			super(context);
			setFocusable(true);
			
			setDataMembers();
		}
		
		private void setDataMembers()
		{			
			monkey = BitmapFactory.decodeResource(getResources(), R.drawable.monkey);
			tree = BitmapFactory.decodeResource(getResources(), R.drawable.tree);			
			bananaSelected = false;
			scaledTree = new Rect();
		}
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			if (firstRun)
			{
				setDrawingCoords(canvas);
			}
			
			canvas.drawBitmap(tree, null, scaledTree, null);
			canvas.drawBitmap(monkey, monkeyX, monkeyY, null);
						
			for (Banana banana : bananas)
			{
				canvas.drawBitmap(banana.icon, 
						banana.x - Banana.ICON_HALFWIDTH, 
						banana.y - Banana.ICON_HALFHEIGHT, 
						null);
			}
		}
		
		private void setDrawingCoords(Canvas canvas)
		{
			firstRun = false;
			
			canvasWidth = canvas.getWidth();
			canvasHeight = canvas.getHeight();
			
			scaledTree.left = scaledTree.top = 0;
			scaledTree.right = canvasWidth / 2;
			scaledTree.bottom = canvasHeight;
			
			monkeyX = canvasWidth - monkey.getWidth();
			monkeyY = canvasHeight - monkey.getHeight();

			for (int i = 0; i < NUM_BANANAS; i++)
			{
				bananaRects[i] = new Rect(xcoords[i], ycoords[i], xcoords[i], ycoords[i]);
				bananas[i] = new Banana(xcoords[i], ycoords[i]);
			}
		}
		
		public boolean onTouchEvent(MotionEvent e)
		{
			switch (e.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					Log.v("test", "(" + e.getX() + "," + e.getY() + ")");
					for (int i = 0; i < NUM_BANANAS; i++)
					{
						if (bananas[i].bounds.contains((int) e.getX(), (int) e.getY()))
						{
							bananaSelected = true;
							selectedBanana = bananas[i];
							selectedBanana.setSelected();
							origBananaX = selectedBanana.x;
							origBananaY = selectedBanana.y;
						}
					}
					break;
				case MotionEvent.ACTION_MOVE:
					if (selectedBanana != null)
					{
						selectedBanana.updatePosition(e.getX(), e.getY());
					}
					break;
				case MotionEvent.ACTION_UP:
					if (bananaSelected)
					{
//						selectedBanana.setUnselected();
//						selectedBanana = null;
//						bananaSelected = false;
						Log.v("test", "(" + e.getX() + "," + e.getY() + ")");
						if (e.getX() >= monkeyX && e.getY() >= monkeyY)
						{
							Toast.makeText(MathTutor.this, "monkey", Toast.LENGTH_SHORT).show();
						}
						else
						{
							selectedBanana.updatePosition(origBananaX, origBananaY);
							selectedBanana.setUnselected();
							bananaSelected = false;
							selectedBanana = null;
						}
					}
					break;
			}
			invalidate();
			return true;
		}
	}
	
	private class Banana
	{
		private static final int ICON_WIDTH = 152;
		private static final int ICON_HEIGHT = 195;
		private static final int ICON_HALFWIDTH = 76;
		private static final int ICON_HALFHEIGHT = 97;
		private int value;
		private Bitmap icon;
		private int x, y;
		private Rect bounds;
		
		public Banana(int x, int y)
		{
			icon = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
			this.x = x;
			this.y = y;	
			bounds = new Rect(x, y, x + ICON_WIDTH, y + ICON_HEIGHT);
		}
		
		private void updatePosition(float newX, float newY)
		{
			x = (int) newX;
			y = (int) newY;
			bounds.left = x;
			bounds.top = y;
			bounds.right = x + ICON_WIDTH;
			bounds.bottom = y + ICON_HEIGHT;
		}
		
		private void setUnselected()
		{
			icon = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
		}
		
		private void setSelected()
		{
			icon = BitmapFactory.decodeResource(getResources(), R.drawable.banana_selected);
		}
		
	}
}
