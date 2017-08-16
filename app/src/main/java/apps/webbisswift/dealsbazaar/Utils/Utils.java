package apps.webbisswift.dealsbazaar.Utils;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by biswas on 01/11/2016.
 */

public class Utils {

    public static String convertDoubleToString(double cTemp){
        String temp = String.format("%.2f", cTemp);
        temp = temp.replaceAll("[.]","\u0387");
        temp = temp.replaceAll("[-]", "‒");
        return temp;
    }

    public static String convertEnglishSymbolsToNepali(String str){
        str = str.replaceAll("[.]","\u0387");
        str = str.replaceAll("[-]", "‒");

        return str;
    }

    public static long getCurrentTimeStamp(){
        return  System.currentTimeMillis() / 1000L;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String sha1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }


    public static float getScreenDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

}
