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
import com.zoho.aksheetview.ui.base.model.logic.entities.commonModel.LoadMoreType
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var sheetViewInstance: SheetViewBuilder
    private var isDataSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeSheetViewBuilder()
        setContent {
            SheetViewTestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        sheetViewInstance.InitializeSheetView(
                            svInitialSetupData = SVInitialSetupData(),
                            svCustomizationData = SVCustomizationData()
                        )
                    }
                }
            }

            LaunchedEffect(Unit) {
                if (isDataSet.not()) {
                    sheetViewInstance.handleSheetViewAction(SheetViewIntent.StartLoading)
                    delay(2000)
                    sheetViewInstance.handleSheetViewAction(
                        SheetViewIntent.SetDataToSheetView(
                            sheetViewData = SheetViewData(
                                sheetViewRowList = CommonUtil.getSampleSheetViewRows(this@MainActivity.applicationContext),
                                sheetViewColumnList = CommonUtil.getSampleSheetViewColumns(
                                    this@MainActivity.applicationContext
                                )
                            ), loadMoreType = LoadMoreType.LoadMoreCompleteOrError()
                        )
                    )
                    isDataSet = true
                }
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
            override fun hideActionBar() {
                this@MainActivity.actionBar?.hide()
            }

            override fun onBackPressed() {
                this@MainActivity.onBackPressed()
            }

            override fun showActionBar() {
                this@MainActivity.actionBar?.show()
            }
        })
    }
}