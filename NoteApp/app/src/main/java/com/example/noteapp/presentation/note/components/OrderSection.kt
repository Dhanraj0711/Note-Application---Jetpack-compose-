package com.example.noteapp.presentation.note.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column() {
        Row(modifier = modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Title",
                check = noteOrder is NoteOrder.Title,
                onCheck = { onOrderChange(NoteOrder.Title(orderType = noteOrder.orderType)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                check = noteOrder is NoteOrder.Date,
                onCheck = { onOrderChange(NoteOrder.Date(orderType = noteOrder.orderType)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                check = noteOrder is NoteOrder.Color,
                onCheck = { onOrderChange(NoteOrder.Color(orderType = noteOrder.orderType)) },
            )
        }
        Row(modifier = modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                check = noteOrder.orderType is OrderType.Ascending,
                onCheck = { onOrderChange(noteOrder.copy(OrderType.Ascending)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                check = noteOrder is NoteOrder.Date,
                onCheck = { onOrderChange(noteOrder.copy(OrderType.Descending)) },
            )
        }
    }

}