package com.example.domain.interactor

import com.example.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<in Params> constructor(
        private val postExecutionThread: PostExecutionThread) {

    protected abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    private val disposables = CompositeDisposable()

    open fun execute(observer: DisposableCompletableObserver, params: Params? = null) {
        val completable = this.buildUseCaseCompletable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)

        addDisposable(completable.subscribeWith(observer))

    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.addAll(disposable)
    }

}