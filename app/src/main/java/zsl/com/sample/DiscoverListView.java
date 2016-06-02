package zsl.com.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by zhang on 2016/6/3.
 */
public class DiscoverListView extends ListView {
    private boolean enableScroll = true;

    public DiscoverListView(Context context) {
        super(context);
    }

    public DiscoverListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscoverListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void enableScroll(boolean enable) {
        enableScroll = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!enableScroll) {
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_MOVE && !enableScroll) {
            return false;
        }

        return super.onTouchEvent(ev);
    }
}
