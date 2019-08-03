package com.tonyyang.interview.m17pretest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tonyyang.interview.m17pretest.addTo
import com.tonyyang.interview.m17pretest.api.UserSearchResponse
import com.tonyyang.interview.m17pretest.data.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class UserViewModel : ViewModel() {

    private val disposable by lazy {
        CompositeDisposable()
    }

    private val users by lazy {
        MutableLiveData<UserSearchResponse>()
    }

    fun getUsers(): LiveData<UserSearchResponse> = users

    fun searchUsers(query: String) {
        UserRepository.searchUsers(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<UserSearchResponse>>() {
                override fun onSuccess(response: Response<UserSearchResponse>) {
                    users.value = response.body()
                }

                override fun onError(e: Throwable) {
                    //TODO: error handling
                }
            }).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
