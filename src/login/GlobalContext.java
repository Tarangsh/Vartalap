package login;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 18/11/12
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalContext {

    private static Context context;

    public static void setContext(Context applicationContext) {
        context = applicationContext;
    }

    public static Context getContext() {
        return  context;
    }
}
