package apps.webbisswift.dealsbazaar.domain.net.exceptions;

import java.io.IOException;

/**
 * Created by biswas on 25/03/2017.
 */

public class NoSlidesFoundException extends IOException {

    public NoSlidesFoundException(){
        super("No slides found.");
    }

}
