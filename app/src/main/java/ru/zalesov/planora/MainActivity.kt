package ru.zalesov.planora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import ru.zalesov.planora.features.home.screens.home.HomeScreenRoot
import ru.zalesov.planora.ui.themes.theme.PlanoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanoraTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    HomeScreenRoot()
                }
            }
        }
    }
}