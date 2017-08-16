package apps.webbisswift.dealsbazaar.domain.net.exceptions;

/**
 * Created by biswas on 29/03/2017.
 */

public class NoProductsFoundInOfferException extends Exception {

    public NoProductsFoundInOfferException() {
        super("No products found in this offer.");
    }
}
