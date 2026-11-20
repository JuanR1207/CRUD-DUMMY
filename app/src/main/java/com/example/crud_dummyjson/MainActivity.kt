package com.example.crud_dummyjson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.crud_dummyjson.navegation.AppNavigation
import com.example.crud_dummyjson.ui.theme.CRUDDUMMYJSONTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CRUDDUMMYJSONTheme {
                AppNavigation()
            }
        }
    }
}
