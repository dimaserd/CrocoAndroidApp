package com.example.domain.utils

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun io(): Scheduler

    fun computation(): Scheduler

    fun mainThread(): Scheduler
}
