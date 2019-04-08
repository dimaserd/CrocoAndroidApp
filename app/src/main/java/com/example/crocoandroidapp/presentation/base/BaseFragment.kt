package com.example.crocoandroidapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    abstract fun getLayout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun showSnackbar(messageResource: Int) {
        view?.let { Snackbar.make(it, getString(messageResource), Snackbar.LENGTH_SHORT).show() }
    }

    protected fun Disposable.disposeOnDestroyView(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}
