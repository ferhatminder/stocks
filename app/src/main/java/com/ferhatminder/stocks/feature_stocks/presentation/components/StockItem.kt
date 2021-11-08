package com.ferhatminder.stocks.feature_stocks.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel


@Composable
fun StockItem(viewModel: StocksViewModel, item: Stock, editing: Boolean) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.code, fontSize = 24.sp)
            Spacer(Modifier.width(4.dp))
            if (editing) {
                Checkbox(
                    modifier = Modifier.testTag(item.code + "_select"),
                    checked = item.tracking,
                    onCheckedChange = {
                        viewModel.onEvent(
                            StocksViewModel.Event.OnCheckChange(
                                it,
                                item
                            )
                        )
                    })
            }
        }
    }
}
