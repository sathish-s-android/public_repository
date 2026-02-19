package com.google.bottomsheettest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.bottomsheettest.components.HandleDraggableBottomSheet
import com.google.bottomsheettest.components.rememberHandleDraggableBottomSheetState
import com.google.bottomsheettest.ui.theme.BottomsheetTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            //sathish
            BottomsheetTestTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val showBottomSheet = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { showBottomSheet.value = true }
        ) {
            Text("Show Bottom Sheet")
        }

        if (showBottomSheet.value) {
            val sheetState = rememberHandleDraggableBottomSheetState(
                sheetHeight = 520.dp,
                dismissThresholdFraction = 0.4f,
            )

            HandleDraggableBottomSheet(
                onDismiss = { showBottomSheet.value = false },
                state = sheetState,
                sheetHeight = 520.dp,
                containerColor = Color.White,
                scrimColor = Color.Black.copy(alpha = 0.1f),
            ) {
                // Content: LazyColumn with 100 items separated by dividers
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(100) { index ->
                        Text(
                            text = "Item ${index + 1}",
                            modifier = Modifier.padding(16.dp)
                        )
                        if (index < 99) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}