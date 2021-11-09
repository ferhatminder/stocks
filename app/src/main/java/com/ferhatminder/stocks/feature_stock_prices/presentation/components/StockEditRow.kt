package com.ferhatminder.stocks.feature_stock_prices.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.LiveData
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel
import com.ferhatminder.stocks.util.TestTags


@Composable
fun StockEditRow(
    viewModel: StocksViewModel,
    data: LiveData<StocksViewModel.State>
) {
    val state by data.observeAsState()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (state!!.editing) {
            TextButton(
                onClick = {
                    viewModel.onEvent(
                        StocksViewModel.Event.CancelEdit
                    )
                },
                modifier = Modifier.testTag(TestTags.CANCEL_EDIT_STOCKS)
            ) {
                Text("cancel", color = Color.Blue)
            }
        } else {
            Box {}
        }

        if (!state!!.editing) {
            IconButton(
                onClick = {
                    viewModel.onEvent(StocksViewModel.Event.StartEditing)
                },
                enabled = state!!.isEditable(),
                modifier = Modifier.testTag(TestTags.EDIT_STOCKS)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Select"
                )
            }
        } else {
            IconButton(
                onClick = {
                    viewModel.onEvent(
                        StocksViewModel.Event.SaveEdit
                    )
                },
                modifier = Modifier.testTag(TestTags.SAVE_EDIT_STOCKS)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save",
                    tint = Color.Red
                )
            }
        }
    }
}