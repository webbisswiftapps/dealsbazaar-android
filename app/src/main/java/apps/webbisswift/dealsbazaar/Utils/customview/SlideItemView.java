package apps.webbisswift.dealsbazaar.Utils.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import apps.webbisswift.dealsbazaar.R;

/**
 * Created by biswas on 25/03/2017.
 */

public class SlideItemView extends BaseSliderView {

    private Object tag;

    public SlideItemView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.slide_layout,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        setScaleType(ScaleType.Fit);
        bindEventAndShow(v, target);
        return v;
    }


    @Override
    protected void bindEventAndShow(View v, ImageView targetImageView) {

        super.bindEventAndShow(v, targetImageView);
    }

    @Override
    public BaseSliderView image(String url) {
        return super.image(url);
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

}
