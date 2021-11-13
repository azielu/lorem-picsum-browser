package com.azielu.lorempicsumbrowser.mvp

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

interface MvpPresenter<V : MvpView> {
    fun initialize(view: V)
    fun terminate()
}

abstract class BasePresenter<V : MvpView>(
    private val compositeDisposable: CompositeDisposable?
) : MvpPresenter<V> {

    protected var view: V? = null

    @CallSuper
    override fun initialize(view: V) {
        this.view = view
    }

    protected fun disposable(disposableProvider: () -> Disposable) {
        checkNotNull(compositeDisposable) { "CompositeDisposable wasn't set in presenter constructor" }
        compositeDisposable += disposableProvider()
    }

    override fun terminate() {
        view = null
        compositeDisposable?.clear()
    }
}
