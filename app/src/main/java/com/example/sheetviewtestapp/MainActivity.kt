package com.example.sheetviewtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import com.example.sheetviewtestapp.ui.theme.SheetViewTestAppTheme
import com.zoho.aksheetview.common.communicator.callbacks.SheetViewAppCallback
import com.zoho.aksheetview.common.communicator.callbacks.SheetViewAppCommunicator
import com.zoho.aksheetview.common.communicator.model.SVCustomizationData
import com.zoho.aksheetview.common.communicator.model.SVInitialSetupData
import com.zoho.aksheetview.common.communicator.model.SheetViewData
import com.zoho.aksheetview.common.initializer.SheetViewBuilder
import com.zoho.aksheetview.ui.base.intent.SheetViewIntent
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var sheetViewInstance: SheetViewBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeSheetViewBuilder()
        setContent {
            SheetViewTestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        sheetViewInstance.InitializeSheetView(
                            svInitialSetupData = SVInitialSetupData(),
                            svCustomizationData = SVCustomizationData()
                        )
                    }
                }
            }

            LaunchedEffect(Unit) {
                sheetViewInstance.handleSheetViewAction(SheetViewIntent.StartLoading)
                delay(2000)
                sheetViewInstance.handleSheetViewAction(
                    SheetViewIntent.SetDataToSheetView(
                        sheetViewData = SheetViewData(
                            sheetViewRowList = CommonUtil.getSampleSheetViewRows(this@MainActivity.applicationContext),
                            sheetViewColumnList = CommonUtil.getSampleSheetViewColumns(
                                this@MainActivity.applicationContext
                            )
                        )
                    )
                )
            }
        }
    }

    private fun initializeSheetViewBuilder() {
        sheetViewInstance = SheetViewBuilder.initialize(sheetViewAppCommunicator = object :
            SheetViewAppCommunicator {
            override fun getViewModelStoreOwner(): ViewModelStoreOwner {
                return this@MainActivity
            }
        }, sheetViewAppCallback = object : SheetViewAppCallback {
            override fun hideActionBar() {}
            override fun onBackPressed() {}
            override fun showActionBar() {}
        })
    }
}