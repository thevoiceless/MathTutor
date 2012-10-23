package csci422.lwm.mathtutor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class MathTutor extends Activity
{
	private static int NUM_BANANAS = 4;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new MathView(this));
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
		private boolean bananaSelected;
		private Banana selectedBanana;
		private Bitmap monkey, tree;

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
			
			// Very fragile positioning logic, (hopefully) temporary
			for (int i = 0; i < NUM_BANANAS; i++)
			{
				bananas[i] = new Banana((70 * (i + 1)), 50);
			}
			bananaSelected = false;
		}
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			Rect origTree = new Rect();
			origTree.left = origTree.top = 0;
			origTree.right = 1000;
			origTree.bottom = 1000;
			Rect scaledTree = new Rect();
			scaledTree.left = scaledTree.top = 0;
			scaledTree.right = canvas.getWidth() / 2;
			scaledTree.bottom = canvas.getHeight();
			
			canvas.drawBitmap(tree, origTree, scaledTree, null);
			int monkeyX = canvas.getWidth() - monkey.getWidth();
			int monkeyY = canvas.getHeight() - monkey.getHeight();
			
			canvas.drawBitmap(monkey, monkeyX, monkeyY, null);
			
			for (Banana banana : bananas)
			{
				canvas.drawBitmap(banana.icon, banana.x, banana.y, null);
			}
		}
		
		public boolean onTouchEvent(MotionEvent e)
		{
			switch (e.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					break;
			}
			invalidate();
			return true;
		}
		
	}
	
	private class Banana
	{
		private int value;
		private Bitmap icon;
		private int x, y;
		
		public Banana(int x, int y)
		{
			icon = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
			this.x = x;
			this.y = y;	
		}
		
		private void updatePosition(float newX, float newY)
		{
			//x = (int) newX - ICON_HALFDIM;
			//y = (int) newY - ICON_HALFDIM;
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
