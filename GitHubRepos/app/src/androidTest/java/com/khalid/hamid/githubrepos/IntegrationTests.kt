package com.khalid.hamid.githubrepos

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.khalid.hamid.githubrepos.kaspresso.LoginScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntegrationTests {

    @Test
    fun testLogin(){
        onScreen<LoginScreen>{
            loginButton {
                clicked()
            }

        }
    }
}