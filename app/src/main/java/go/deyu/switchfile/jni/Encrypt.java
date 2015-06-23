package go.deyu.switchfile.jni;

/**
 * Created by huangeyu on 15/6/22.
 */
public class Encrypt {

    public native int encryptfile(String filepath);

    static {
        System.loadLibrary("Encryptjni");
    }

}
