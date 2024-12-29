package com.expense.track.android

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.expense.track.android.ui.HomeFragment
import kotlinx.serialization.Serializable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen()
                val navController = rememberNavController()
                NavHost(navController,startDestination = HomePage){
                    composable<HomePage> {
                        setHomeScreen()
                    }
                }
            }
        }
    }

    private fun setHomeScreen(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container, HomeFragment(), "composeFragment")
        }
    }
}


@Serializable
object HomePage

@Composable
fun MainScreen() {
    AndroidView(
        factory = { context ->
            FragmentContainerView(context).apply {
                id = R.id.fragment_container
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
