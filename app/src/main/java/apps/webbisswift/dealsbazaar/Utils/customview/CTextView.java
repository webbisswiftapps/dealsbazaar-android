package apps.webbisswift.dealsbazaar.Utils.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import apps.webbisswift.dealsbazaar.R;


/**
 * Created by biswas on 21/05/16.
 */
public class CTextView extends TextView {

    public final static int FSTYLE_LIGHT = 0;
    public final static int FSTYLE_REGULAR = 1;
    public final static int FSTYLE_MEDIUM = 2;
    public final static int FSTYLE_SEMIBOLD = 3;
    public final static int FSTYLE_BOLD = 4;





    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(!getRootView().isInEditMode())
            applyCustomFont(context, attrs);
    }

    public CTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


            applyCustomFont(context, attrs);
    }




    private void applyCustomFont(Context context,AttributeSet attrs) {

        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CTextView);

        String fontName = attributeArray.getString(R.styleable.CTextView_font);
        int fStyle = attributeArray.getInt(R.styleable.CTextView_fstyle,0);


            Typeface customFont = selectTypeface(context, fontName, fStyle);
            setTypeface(customFont);


        attributeArray.recycle();
    }


    private Typeface selectTypeface(Context context, String fontName,int fStyle) {

           if(fontName==null)
               return FontCache.getTypeface("quicksand/R.ttf",context);

            switch (fStyle) {
                case CTextView.FSTYLE_LIGHT:
                    return FontCache.getTypeface(fontName+"/L.ttf",context);
                case CTextView.FSTYLE_REGULAR:
                    return FontCache.getTypeface(fontName+"/R.ttf",context);
                case CTextView.FSTYLE_MEDIUM:
                    return FontCache.getTypeface(fontName+"/M.ttf",context);
                case CTextView.FSTYLE_BOLD:
                    return FontCache.getTypeface(fontName+"/B.ttf",context);
                case CTextView.FSTYLE_SEMIBOLD:
                    return FontCache.getTypeface(fontName+"/SB.ttf",context);
                default:
                    return FontCache.getTypeface(fontName+"/R.ttf", context);
            }

    }
}
