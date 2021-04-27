package com.thanh.date_between.common.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V>(v: V): IBasePresenter {
    protected var mView: V = v
    protected var mComposite = CompositeDisposable()

    override fun onViewCreated() {
        onCompositedEventAdded()
    }

    override fun onDestroy() {
        mComposite.clear()
    }

    protected open fun onCompositedEventAdded() {}

    fun add(event: Disposable){
        mComposite.add(event)
    }
}