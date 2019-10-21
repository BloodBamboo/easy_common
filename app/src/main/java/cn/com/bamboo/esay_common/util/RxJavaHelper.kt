package cn.com.bamboo.esay_common.util

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ExecuteOnceMaybeObserver<T>(val onExecuteOnSuccess: (T) -> Unit = {},
                                  val onExecuteOnceComplete: () -> Unit = {},
                                  val onExecuteOnceError: (Throwable) -> Unit = {}) : MaybeObserver<T> {
    private var mDisposable: Disposable? = null
    override fun onSuccess(t: T) {
        onExecuteOnSuccess(t)
        mDisposable?.dispose()
    }

    override fun onComplete() {
        onExecuteOnceComplete()
        mDisposable?.dispose()
    }

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
    }

    override fun onError(e: Throwable) {
        onExecuteOnceError(e)
        mDisposable?.dispose()
    }
}

class ExecuteOnceObserver<T>(val onExecuteOnceNext: (T) -> Unit = {},
                             val onExecuteOnceComplete: () -> Unit = {},
                             val onExecuteOnceError: (Throwable) -> Unit = {}) : Observer<T> {
    private var mDisposable: Disposable? = null

    override fun onComplete() {
        onExecuteOnceComplete()
        mDisposable?.dispose()
    }

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
    }

    override fun onNext(t: T) {
        try {
            onExecuteOnceNext(t)
            this.onComplete()
        } catch (e: Throwable) {
            this.onError(e)
        } finally {
            if (mDisposable != null && !mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
        }
    }
    override fun onError(e: Throwable) {
        onExecuteOnceError(e)
        mDisposable?.dispose()
    }
}

class RxJavaHelper {
    companion object {
        fun <T> schedulersTransformer(): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                return@ObservableTransformer upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onTerminateDetach() as ObservableSource<T>
            }
        }

//    public static SingleTransformer schedulersTransformerSingle() {
//        return new SingleTransformer() {
//            @Override
//            public SingleSource apply(Single upstream) {
//                return upstream.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .onTerminateDetach();
//            }
//        };
//    }

        fun <T> schedulersTransformerMaybe(): MaybeTransformer<T, T> {
            return MaybeTransformer { upstream ->
                upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onTerminateDetach() as MaybeSource<T>
            }
        }
    }
}