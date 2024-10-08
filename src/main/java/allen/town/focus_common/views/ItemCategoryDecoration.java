package allen.town.focus_common.views;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import allen.town.focus_common.util.Util;

/**
 * Source: https://stackoverflow.com/a/30794046
 */
public class ItemCategoryDecoration extends RecyclerView.ItemDecoration {
    private final int itemOffset;

    public ItemCategoryDecoration(@NonNull Context context, int itemOffsetDp) {
        itemOffset = (int) (itemOffsetDp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemOffset, Util.dp2Px(view.getContext(), -3), itemOffset, Util.dp2Px(view.getContext(), -3));
    }
}
