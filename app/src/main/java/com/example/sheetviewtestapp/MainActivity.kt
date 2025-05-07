package com.example.sheetviewtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    private val shouldShowTopBar = mutableStateOf(true)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeSheetViewBuilder()
        setContent {
            SheetViewTestAppTheme {
                val needTopBar = remember(shouldShowTopBar.value) { shouldShowTopBar.value }
                Scaffold(
                    topBar = {
                        if (needTopBar) {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = colorResource(R.color.top_bar_color),
                                    titleContentColor = colorResource(R.color.black),
                                ), title = {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(R.string.sheet_title)
                                    )
                                })
                        }
                    }
                ) { padding ->
                    SheetViewContent(padding = if (needTopBar) padding else PaddingValues(0.dp))
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

    @Composable
    fun SheetViewContent(padding: PaddingValues = PaddingValues(0.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            sheetViewInstance.InitializeSheetView(
                svInitialSetupData = SVInitialSetupData(),
                svCustomizationData = SVCustomizationData()
            )
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
                shouldShowTopBar.value = false
            }

            override fun onBackPressed() {
                this@MainActivity.onBackPressed()
            }

            override fun showActionBar() {
                shouldShowTopBar.value = true
            }
        })
    }
}