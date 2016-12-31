package lin.rong.progressbutton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by dell on 2016/12/30.
 */
public class ProgressButton extends Button implements View.OnClickListener {

    private boolean startFlag = false;
    private int currentValue = 0;
    private Paint progressPaint = new Paint();
    private Paint backgroundPaint = new Paint();
    private int width;
    private int height;
    private Path progressPath;

    public ProgressButton(Context context) {
        super(context);

        init();
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("ProgressButton", "width: " + width + ", height: " + height);
    }

    private void init() {
        setOnClickListener(this);

        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        progressPaint.setStrokeWidth(1);
        progressPaint.setColor(getResources().getColor(R.color.progress_orange));

        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(getResources().getColor(R.color.progress_gray));

        progressPath = new Path();
    }

    private void drawProgress(Canvas canvas) {
        RectF backgroundRect = new RectF();
        backgroundRect.left = 0;
        backgroundRect.top = 0;
        backgroundRect.bottom = height;
        backgroundRect.right = width;
        canvas.drawRoundRect(backgroundRect, dp2px(30), dp2px(30), backgroundPaint);

        int progress = (int) (currentValue / 100f * width);



        float leftSweepAngle;
        if (progress <= dp2px(30)) {
            leftSweepAngle = 90 * (progress / dp2px(30));
        } else {
            leftSweepAngle = 90;
        }

        progressPath.reset();

        RectF leftTopRect = new RectF();
        leftTopRect.left = 0;
        leftTopRect.top = 0;
        leftTopRect.right = dp2px(60);
        leftTopRect.bottom = dp2px(60);
        progressPath.addArc(leftTopRect, 180, leftSweepAngle);
        RectF topRightRect = new RectF();
        progressPath.computeBounds(topRightRect, false);
        canvas.drawPath(progressPath, progressPaint);

        progressPath.reset();

        RectF leftBottomRect = new RectF();
        leftBottomRect.left = 0;
        leftBottomRect.top = height - dp2px(60);
        leftBottomRect.right = dp2px(60);
        leftBottomRect.bottom = height;
        progressPath.addArc(leftBottomRect, 180, -leftSweepAngle);
        RectF bottomRightRect = new RectF();
        progressPath.computeBounds(bottomRightRect, false);
        canvas.drawPath(progressPath, progressPaint);

        if (progress > 0) {
            progressPaint.setStrokeWidth(1);progressPath.reset();

            float topLeftX = 0;
            float topLeftY = dp2px(30);

            float bottomLeftX = 0;
            float bottomLeftY = height - dp2px(30);

            float topRightX = topRightRect.right;
            float topRightY = topRightRect.top;

            float bottomRightX = bottomRightRect.right;
            float bottomRightY = bottomRightRect.bottom;

            progressPath.moveTo(topLeftX, topLeftY);
            progressPath.lineTo(topRightX, topRightY);
            progressPath.lineTo(bottomRightX, bottomRightY);
            progressPath.lineTo(bottomLeftX, bottomLeftY);
            canvas.drawPath(progressPath, progressPaint);
        }


        if (progress > dp2px(30) && progress < width - dp2px(30)) {
            RectF progressRect = new RectF();
            progressRect.left = dp2px(30);
            progressRect.top = 0;
            progressRect.bottom = height;
            progressRect.right = progress;
            canvas.drawRect(progressRect, progressPaint);
        } else if (width - progress <= dp2px(30)){
            RectF progressRect = new RectF();
            progressRect.left = dp2px(30);
            progressRect.top = 0;
            progressRect.bottom = height;
            progressRect.right = width - dp2px(30);
            canvas.drawRect(progressRect, progressPaint);
        }

        float rightSweepAngle = 0;
        if (width - progress <= dp2px(30)) {
            rightSweepAngle = 90 * (1 - (width - progress) / dp2px(30));
        }

        progressPath.reset();

        RectF rightTopRect = new RectF();
        rightTopRect.left = width - dp2px(60);
        rightTopRect.top = 0;
        rightTopRect.right = width;
        rightTopRect.bottom = dp2px(60);
        progressPath.addArc(rightTopRect, 270, rightSweepAngle);
        RectF topRightRect1 = new RectF();
        progressPath.computeBounds(topRightRect1, false);
        canvas.drawPath(progressPath, progressPaint);

        progressPath.reset();

        RectF rightBottomRect = new RectF();
        rightBottomRect.left = width - dp2px(60);
        rightBottomRect.top = height - dp2px(60);
        rightBottomRect.right = width;
        rightBottomRect.bottom = height;
        progressPath.addArc(rightBottomRect, 90, -rightSweepAngle);
        RectF bottomRightRect1 = new RectF();
        progressPath.computeBounds(bottomRightRect1, false);
        canvas.drawPath(progressPath, progressPaint);

        if (width - progress < dp2px(30)) {
            progressPath.reset();

            float topLeftX1 = width - dp2px(30);
            float topLeftY1 = 0;

            float bottomLeftX1 = width - dp2px(30);
            float bottomLeftY1 = height;

            float topRightX1 = topRightRect1.right;
            float topRightY1 = topRightRect1.bottom;

            float bottomRightX1 = bottomRightRect1.right;
            float bottomRightY1 = bottomRightRect1.top;

            progressPath.moveTo(topLeftX1, topLeftY1);
            progressPath.lineTo(topRightX1, topRightY1);
            progressPath.lineTo(bottomRightX1, bottomRightY1);
            progressPath.lineTo(bottomLeftX1, bottomLeftY1);
            canvas.drawPath(progressPath, progressPaint);
        }
    }

    private float dp2px(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawProgress(canvas);

        super.onDraw(canvas);
    }

    @Override
    public void onClick(View view) {
        Log.e("ProgressButton", "onClick");


        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentValue = (int) valueAnimator.getAnimatedValue();
                Log.e("ProgressButton", currentValue + "");

                setText(currentValue + "%");
            }

        });
        valueAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                startFlag = true;
                setBackgroundResource(android.R.color.transparent);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startFlag = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                startFlag = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }

        });
        valueAnimator.setDuration(20000);
        valueAnimator.start();
    }

}
