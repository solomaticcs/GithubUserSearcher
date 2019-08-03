package com.tonyyang.github.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tonyyang.github.users.R
import com.tonyyang.github.users.StringProvider
import com.tonyyang.github.users.addTo
import com.tonyyang.github.users.api.UserSearchResponse
import com.tonyyang.github.users.data.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class UserViewModel(private val stringProvider: StringProvider) : ViewModel() {

    private val disposable by lazy {
        CompositeDisposable()
    }

    private val users by lazy {
        MutableLiveData<UserSearchResponse>()
    }

    private val isUpdating by lazy {
        MutableLiveData<Boolean>()
    }

    private val toastMessage by lazy {
        SingleLiveEvent<String>()
    }

    fun getUsers(): LiveData<UserSearchResponse> = users

    fun getIsUpdating(): LiveData<Boolean> = isUpdating

    fun getToastMessage(): LiveData<String> = toastMessage

    fun searchUsers(query: String) {
        isUpdating.value = true
        UserRepository.searchUsers(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<UserSearchResponse>>() {
                override fun onStart() {
                    isUpdating.value = true
                }

                override fun onSuccess(response: Response<UserSearchResponse>) {
                    if (response.isSuccessful) {
                        response.body().let { data ->
                            users.value = data
                            toastMessage.value =
                                stringProvider.getString(R.string.search_users_successful, data?.totalCount as Any)
                        }
                    } else {
                        toastMessage.value = response.errorBody()?.string()
                    }
                    isUpdating.value = false
                }

                override fun onError(e: Throwable) {
                    toastMessage.value = e.localizedMessage
                    isUpdating.value = false
                }
            }).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
