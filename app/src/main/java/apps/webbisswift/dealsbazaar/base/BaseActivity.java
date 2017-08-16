package apps.webbisswift.dealsbazaar.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 * Created by biswas on 25/03/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    BasePresenter mPresenter;
    ViewGroup rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void setRootView(ViewGroup rootView){
        this.rootView= rootView;
    }
    protected void setPresenter(BasePresenter presenter){
        this.mPresenter = presenter;
    }

    /* Activity Transitions */
    private void setupWindowAnimations() {

    }


    /* LifeCycle */
    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void showSnackbar(String message) {
        if(rootView!=null)
            Snackbar.make(this.rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mPresenter.pause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mPresenter.destroy();
    }


}
