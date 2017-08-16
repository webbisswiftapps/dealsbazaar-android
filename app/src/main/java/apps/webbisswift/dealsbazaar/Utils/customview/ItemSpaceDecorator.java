package apps.webbisswift.dealsbazaar.Utils.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by biswas on 09/05/2017.
 */

public class ItemSpaceDecorator extends RecyclerView.ItemDecoration {

    private final int mSpaceHeight;

    public ItemSpaceDecorator(int mSpaceHeight) {
        this.mSpaceHeight = mSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = mSpaceHeight;
        outRect.top = mSpaceHeight;
    }
}