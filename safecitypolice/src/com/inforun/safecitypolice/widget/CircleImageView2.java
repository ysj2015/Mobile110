package com.inforun.safecitypolice.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形ImageView
 * 
 */
@SuppressLint("NewApi")
public class CircleImageView2 extends ImageView {

	private Bitmap mBitmap;
	private Rect bitmapRect = new Rect();
	private PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0,
			Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
	private Paint paint = new Paint();

	{
		paint.setStyle(Paint.Style.STROKE);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
	}

	private Bitmap mDstB = null;
	private PorterDuffXfermode xfermode = new PorterDuffXfermode(
			PorterDuff.Mode.MULTIPLY);

	public CircleImageView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CircleImageView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CircleImageView2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setImageBitmap(Bitmap bitmap) {
		this.mBitmap = bitmap;
		try {
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				setLayerType(LAYER_TYPE_SOFTWARE, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Bitmap makeDst(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(Color.parseColor("#ffffff"));
		c.drawOval(new RectF(0, 0, w, h), p);
		p.setColor(Color.parseColor("#ffffff"));
		c.drawOval(new RectF(3, 3, w - 3, h - 3), p);

		return bm;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (null == mBitmap) {
			return;
		}

		int mBitmap_width = mBitmap.getWidth();
		int mBitmap_height = mBitmap.getHeight();

		int real = mBitmap_width > mBitmap_height ? mBitmap_height
				: mBitmap_width;
		if (real == mBitmap_width) {
			int y = (mBitmap_height - mBitmap_width) / 2 - 1;
			y = y < 0 ? 0 : y;
			mBitmap = Bitmap.createBitmap(mBitmap, 0, y, real, real,
					new Matrix(), false);
		} else {
			int x = (mBitmap_width - mBitmap_height) / 2 - 1;
			x = x < 0 ? 0 : x;
			mBitmap = Bitmap.createBitmap(mBitmap, x, 0, real, real,
					new Matrix(), false);
		}

		if (null == mDstB) {
			mDstB = makeDst(getWidth(), getHeight());
		}

		bitmapRect.set(0, 0, getWidth(), getHeight());
		canvas.save();
		canvas.setDrawFilter(pdf);
		canvas.drawBitmap(mDstB, 0, 0, paint);
		paint.setXfermode(xfermode);
		canvas.drawBitmap(mBitmap, null, bitmapRect, paint);
		paint.setXfermode(null);
		canvas.restore();
	}
}