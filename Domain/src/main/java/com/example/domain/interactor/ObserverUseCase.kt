package com.example.domain.interactor

import com.example.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class ObserverUseCase<T, in Params> constructor(
        private val postExecutionThread: PostExecutionThread) {

    protected abstract fun buildUseCaseObserable(params: Params? = null): Observable<T>

    private val disposables = CompositeDisposable()

    open fun execute(observer: DisposableObserver<T>, params: Params? = null) {
        val observable = this.buildUseCaseObserable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)

        addDisposable(observable.subscribeWith(observer))

    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.addAll(disposable)
    }

}