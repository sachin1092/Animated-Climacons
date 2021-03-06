package saphion.testproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.widget.ImageView;

public class HailIV extends ImageView {

	int height, width;
	Bitmap cloud, drop;
	int angle = 0;
	Path path1, path2, path3;
	Paint mPaint = new Paint();
	PathMeasure pm1, pm2, pm3;
	Paint mPaint1, mPaint2, mPaint3;
	float curr1 = 0, curr2 = 30, curr3 = 15;
	float len1 = 0, len2, len3;
	float[] tan = new float[2];
	float[] pts = new float[10];

	public HailIV(Context context) {
		super(context);
	}

	public HailIV(Context context, int height, int width) {
		super(context);
		this.height = height;
		this.width = width;
		// sun = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
		cloud = BitmapFactory.decodeResource(getResources(),
				R.drawable.cloud_open);
		drop = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
		int mheight = (int) (width * 0.08);
		drop = Bitmap.createScaledBitmap(drop, mheight, mheight, true);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint1 = new Paint();
		mPaint1.setAntiAlias(true);
		mPaint2 = new Paint();
		mPaint2.setAntiAlias(true);
		mPaint3 = new Paint();
		mPaint3.setAntiAlias(true);

		cloud = Bitmap.createScaledBitmap(cloud, (int) (width), (int) (height),
				true);

		path1 = new Path();
		path2 = new Path();
		path3 = new Path();

		path1.moveTo(width / 3, height / 2);
		path2.moveTo((float) (width / 2), height / 2);
		path3.moveTo((float) (width * 0.66), height / 2);

		path1.lineTo(width / 3, height);
		path2.lineTo((float) (width / 2), height);
		path3.lineTo((float) (width * 0.66), height);

		path1.quadTo((float) (width / 4), (float) (height / 1.2),
				(float) (width / 5), (height));
		path2.quadTo((float) (width / 2), (float) (height / 1.5),
				(float) (width / 2), height);
		path3.quadTo((float) (width * 0.73), (float) (height / 1.2),
				(float) (width * 0.79), (height));

		mPaint.setPathEffect(new DashPathEffect(new float[] { 10, 10 }, 0));
		pm1 = new PathMeasure(path1, false);
		pm2 = new PathMeasure(path2, false);
		pm3 = new PathMeasure(path3, false);

		len1 = pm1.getLength() / 100;
		len2 = pm2.getLength() / 100;
		len3 = pm3.getLength() / 100;

		// sun = Bitmap.createScaledBitmap(sun, (int) (width * 0.65),
		// (int) (height * 0.65), true);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		/*
		 * canvas.save(); canvas.rotate(angle, width - (float) (sun.getWidth())
		 * + sun.getWidth() / 2, (float) sun.getHeight() / 2);
		 * 
		 * canvas.drawBitmap(sun, width - (float) (sun.getWidth()), 0, mPaint);
		 * canvas.restore();
		 */

		mPaint1.setAlpha(setval(curr1));
		mPaint2.setAlpha(setval(curr2));
		mPaint3.setAlpha(setval(curr3));

		pm1.getPosTan((float) (len1 * curr1), pts, tan);
		canvas.drawBitmap(drop, pts[0] - drop.getWidth() / 2,
				pts[1] - drop.getHeight() / 2, mPaint1);

		pm2.getPosTan((float) (len2 * curr2), pts, tan);
		canvas.drawBitmap(drop, pts[0] - drop.getWidth() / 2,
				pts[1] - drop.getHeight() / 2, mPaint2);

		pm3.getPosTan((float) (len3 * curr3), pts, tan);
		canvas.drawBitmap(drop, pts[0] - drop.getWidth() / 2,
				pts[1] - drop.getHeight() / 2, mPaint3);

		canvas.drawBitmap(cloud, (width - cloud.getWidth()) / 2,
				(height - cloud.getHeight()) / 2, mPaint);

		curr1 += 4;
		curr2 += 2;
		curr3 += 4;

		curr1 = check(curr1);
		curr2 = check(curr2);
		curr3 = check(curr3);

		// mPaint.setColor(Color.WHITE);
		// mPaint.setStyle(Style.STROKE);

		// mPaint.setStrokeWidth((float) (height * 0.0015));
		// canvas.drawPath(path1, mPaint);
		// canvas.drawPath(path2, mPaint);
		// canvas.drawPath(path3, mPaint);

		if (angle++ > 360)
			angle = 0;

		mHandler.postDelayed(runnable, 20);

	}

	private int setval(float curr) {
		int val = 0;
		if (curr <= 50) {
			val = (int) (curr * 5.1);
		} else {
			val = (int) ((100 - curr) * 5.1);
		}
		return val;
	}

	float check(float i) {
		if (i >= 99 && i < 104)
			return 0;
		return i;
	}

	Handler mHandler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			invalidate();
		}
	};

}
