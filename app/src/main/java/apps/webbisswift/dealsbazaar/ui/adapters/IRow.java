package apps.webbisswift.dealsbazaar.ui.adapters;

/**
 * Created by biswas on 14/08/2017.
 */

public interface IRow {

    public static final int TYPE_SECTION = 0;
    public static final int TYPE_AD = 1;
    public static final int TYPE_AD_LARGE = 4;
    public static final int TYPE_SLIDER = 2;
    public static final int TYPE_PROGRESS= 3;

    public int getType();

}
