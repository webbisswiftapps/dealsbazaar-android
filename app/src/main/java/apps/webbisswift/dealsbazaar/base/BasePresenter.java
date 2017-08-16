package apps.webbisswift.dealsbazaar.base;

/**
 * Created by biswas on 25/03/2017.
 */


public interface BasePresenter<T extends BaseView> {


    /**
     * Method that controls the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void destroy();

    void attachView(T view);

}