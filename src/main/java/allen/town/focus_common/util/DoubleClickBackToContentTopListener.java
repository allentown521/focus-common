package allen.town.focus_common.util;

import android.view.View;

import androidx.annotation.NonNull;

public class DoubleClickBackToContentTopListener implements View.OnClickListener {

    private final long delayTime = 300;
    private long lastClickTime = 0;
    private final IBackToContentTopView backToContentTopView;

    public interface IBackToContentTopView {
        void backToContentTop();
    }

    @Override
    public final void onClick(View v) {
        long nowClickTime = System.currentTimeMillis();
        if (nowClickTime - lastClickTime  > delayTime) {
            lastClickTime = nowClickTime;
        } else {
            onDoubleClick(v);
        }
    }

    public DoubleClickBackToContentTopListener(@NonNull IBackToContentTopView backToContentTopView) {
        this.backToContentTopView = backToContentTopView;
    }

    public void onDoubleClick(View v) {
        backToContentTopView.backToContentTop();
    }

}