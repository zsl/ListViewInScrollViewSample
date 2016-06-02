package zsl.com.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by zhang on 2016/6/2.
 */
public class DiscoverScrollView extends ScrollView {
    public interface ScrollListener {
        void onScrollChanged(int scrollX, int scrollY);
    }

    private boolean enableScroll = true;
    private ScrollListener  scrollListener;

    public DiscoverScrollView(Context context) {
        super(context);
    }

    public DiscoverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscoverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void enableScroll(boolean enable) {
        this.enableScroll = enable;
        if (!enable) {
            smoothScrollBy(0, 0);
        }
    }

    public void setScrollListener(ScrollListener listener) {
        this.scrollListener = listener;
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

    @Override
    protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);

        if (scrollListener != null) {
            scrollListener.onScrollChanged(scrollX, scrollY);
        }
    }
}
