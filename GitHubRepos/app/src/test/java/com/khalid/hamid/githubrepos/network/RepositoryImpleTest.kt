package com.khalid.hamid.githubrepos.network

import com.khalid.hamid.githubrepos.network.local.LocalDataSource
import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import com.khalid.hamid.githubrepos.vo.Repositories
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RepositoryImpleTest {

    lateinit var localDataSource : LocalDataSource
    lateinit var remoteDataSource : RemoteDataSource
    lateinit var repositoryImple: RepositoryImple

    @Before
    fun setUp() {
        localDataSource = mock(LocalDataSource::class.java)
        remoteDataSource = mock(RemoteDataSource::class.java)
        repositoryImple  = RepositoryImple(localDataSource, remoteDataSource)
    }

    @After
    fun tearDown() {
    }

    inline fun <reified T : Any> argumentCaptor() = ArgumentCaptor.forClass(T::class.java)

    @Test
    fun getRepositories_FirtTimeAccess() {
        `when`(localDataSource.hasCacheExpired()).thenReturn(true)

        emptyList<Repositories>()

        runBlocking {
            repositoryImple.getRepositories()
        }

        runBlocking {
            verify((remoteDataSource), times(1)).fetchRespos()
            verify((localDataSource), times(1)).saveData(any())
        }

    }

    @Test
    fun getRepositories_ConsecuentAccess() {
        `when`(localDataSource.hasCacheExpired()).thenReturn(false)

        val empty = emptyList<Repositories>()

        runBlocking {
            Mockito.`when`(localDataSource.getRepositories()).thenReturn(Result.Success(empty))
        }

        runBlocking {
            repositoryImple.getRepositories()
        }

        runBlocking {
            verify((localDataSource), times(1)).getRepositories()
            verify((remoteDataSource), never()).fetchRespos()
        }
    }

    @Test
    fun fetchRepos() {
    }
}