package com.tonyyang.github.users.api

import com.tonyyang.github.users.ExecuteOnceObserver
import com.tonyyang.github.users.model.User
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch

class GithubServiceTest {

    private val latch by lazy {
        CountDownLatch(1)
    }

    private val apiService by lazy {
        GithubService.getService()
    }

    @Before
    fun setup() {
    }

    @Test
    fun `enter "tony" to get the first page of 10 user`() {
        Assert.assertNotNull(apiService)
        var items: List<User>? = null
        apiService
            .searchUsers("tony", 10, 1)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(ExecuteOnceObserver(onExecuteOnceNext = {
                items = it.items
                latch.countDown()
            }, onExecuteOnceError = {
                latch.countDown()
            }))
        latch.await()
        Assert.assertTrue(items?.isNotEmpty() ?: false)
    }
}