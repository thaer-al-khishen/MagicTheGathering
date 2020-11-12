package com.example.magicthegathering.Utils

import io.reactivex.Scheduler

interface IRxSchedulers {
    fun main(): Scheduler
    fun io(): Scheduler
}
