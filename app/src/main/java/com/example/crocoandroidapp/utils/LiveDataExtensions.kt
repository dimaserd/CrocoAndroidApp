package com.example.crocoandroidapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import java.util.LinkedList

fun <T> MutableLiveData<T>.onNext(next: T) {
    this.value = next
}

inline fun <reified T : Any, reified L : LiveData<T?>> Fragment.observe(
    liveData: L,
    noinline block: (T) -> Unit
) {
    liveData.observe(this.viewLifecycleOwner, Observer { it?.let { block.invoke(it) } })
}

inline fun <reified T : Any, reified L : CommandsLiveData<T>> LifecycleOwner.observe(
    liveData: L,
    noinline block: (T) -> Unit
) {
    liveData.observe(this, Observer<LinkedList<T>> { commands ->
        while (commands != null && commands.isNotEmpty()) {
            block.invoke(commands.poll())
        }
    })
}
