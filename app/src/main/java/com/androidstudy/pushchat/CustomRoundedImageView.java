package com.vaultmicro.kidsnote.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomRoundedImageView extends ImageView
{
    public CustomRoundedImageView(Context context) {
        super(context);
    }

    public CustomRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
	class CircleDrawable extends Drawable
	{
		//private final BitmapShader mBitmapShader;
		//private final Paint mPaint;
		private final Bitmap mSrcBitmap;
		private Bitmap mBitmap = null;

		CircleDrawable(Bitmap bitmap)
		{
			mSrcBitmap = bitmap;
		}

		@Override
		protected void onBoundsChange(Rect bounds) {
			super.onBoundsChange(bounds);
		}

		@Override
		public void draw(Canvas canvas)
		{
			Rect rectView = new Rect(getBounds());
/*			Rect rectBitmap = new Rect(0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight());

			if (mBitmap == null)
				mBitmap = getRoundedRectBitmap(mSrcBitmap, rectBitmap);
			
			rectBitmap.offset(rectView.centerX() - rectBitmap.centerX(), rectView.centerY() - rectBitmap.centerY());

			if (mBitmap != null)
				canvas.drawBitmap(mBitmap, null, rectBitmap, null);
*/
			if (mBitmap == null)
				mBitmap = getRoundedRectBitmap(mSrcBitmap, rectView);
			if (mBitmap != null)
				canvas.drawBitmap(mBitmap, null, rectView, null);
		}
		
	    public Bitmap getRoundedRectBitmap(Bitmap bitmap, Rect rectDest)
	    {
	    	Bitmap result = Bitmap.createBitmap(rectDest.width(), rectDest.height(), Bitmap.Config.ARGB_8888);
	    	Canvas canvas = new Canvas(result);
	    	
	    	int color = 0xff424242;
	    	Paint paint = new Paint();
	    	
	    	paint.setAntiAlias(true);
	    	canvas.drawARGB(0, 0, 0, 0);
	    	paint.setColor(color);
	    	canvas.drawCircle(rectDest.width()/2, rectDest.height()/2, rectDest.width()/2, paint);
	    	
	    	paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
	    	canvas.drawBitmap(bitmap, null, rectDest, paint);
	    	
	    	return result;
	    }

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		@Override
		public void setAlpha(int alpha) {
			//mPaint.setAlpha(alpha);
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
			//mPaint.setColorFilter(cf);
		}
	}
}