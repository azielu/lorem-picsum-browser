package com.azielu.lorempicsumbrowser.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BasePresenterActivity<
        V : MvpView,
        P : MvpPresenter<V>
        > : AppCompatActivity(), MvpView {

    protected abstract val presenter: P

    protected abstract val mvpView: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initialize(mvpView)
    }

    override fun onDestroy() {
        presenter.terminate()
        super.onDestroy()
    }
}
