package com.expense.track.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.expense.track.android.Theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffoldExample(skipPartiallyExpanded:Boolean = true,paddingTop: Dp =0.dp, containerColor:Color = AppColors.ItemBackground, onDismiss: () -> Unit, content: @Composable () -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    ModalBottomSheet(
        modifier = Modifier.background(Color.Transparent)
            .padding(top = paddingTop)
            .windowInsetsPadding(
                WindowInsets.statusBars
            )
            .navigationBarsPadding(),
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = containerColor,
        windowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)
    ) {
        content()
    }

}
