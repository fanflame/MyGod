package com.fanyiran.mygod.base.mvp;

/**
 * Created by fanqiang on 2019-05-10.
 */
public abstract class AbstractPresenter {
    protected AbstractModle mImodel = null;
    protected IView mIview = null;

    public AbstractPresenter(IView iView) {
        mIview = iView;
    }

    abstract public void iniModel();

    public void destroy() {
        if (mImodel != null) {
            mImodel.modelDestroy();
        }
        mImodel = null;
        mIview = null;
    }

    public abstract void getWeather(String cityCode);

    public abstract void getLastWeather();

}
