package apps.webbisswift.dealsbazaar.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by biswas on 05/03/2017.
 */

public class BaseFragment extends Fragment implements BaseView {

    BasePresenter mPresenter;

    protected void setPresenter(BasePresenter presenter){
        this.mPresenter = presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);

    }

    @Override
    public void showSnackbar(String message) {
        ((BaseActivity)getActivity()).showSnackbar(message);
    }
}
