package xiaoan.com.application.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by An on 2017/7/2.
 */

public class VideoLoginView extends VideoView {
    public VideoLoginView(Context context) {
        super(context);
    }

    public VideoLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoLoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VideoLoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
