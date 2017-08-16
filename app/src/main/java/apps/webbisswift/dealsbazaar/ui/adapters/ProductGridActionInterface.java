package apps.webbisswift.dealsbazaar.ui.adapters;

import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;

/**
 * Created by biswas on 11/05/2017.
 */

public interface ProductGridActionInterface {

    public void onProductClicked(String productURL, String title);
    public void onViewAllClicked(DealSectionVM section);
}
