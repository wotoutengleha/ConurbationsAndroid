package com.test.admin.conurbations.presenter;

import com.test.admin.conurbations.model.api.GankService;
import com.test.admin.conurbations.retrofit.AppClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class BasePresenter<V> implements Presenter<V> {

    public V mvpView;
    protected GankService apiStores;
    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = AppClient.retrofit().create(GankService.class);
    }

    @Override
    public boolean isAttached() {
        return mvpView != null;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
        onUnSubscribe();
    }

    public void addSubscription(Observable observable, DisposableObserver disposableObserver) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add((Disposable) observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver));
    }

    //RxJava取消注册，以避免内存泄露
    private void onUnSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
