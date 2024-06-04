package com.example.gympal2.ui.screen.main.Map.GymBottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gympal2.R
import com.example.gympal2.model.WorkHours

@Composable
fun TrainingHistoryTab() {
//    TODO: asdasdas
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Here will be a training history tab")
    }
}

@Composable
fun WorkHoursTable(workingHours: List<WorkHours>) {

    for (workHours in workingHours) {
        WorkHours(workHours)
        HorizontalDivider(Modifier.padding(end = 100.dp))
    }
}

@Composable
fun WorkHours(workHours: WorkHours) {
    workHours.run {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Image(
                painter = painterResource(id = if (isOpen) R.drawable.green_circle else R.drawable.red_circle),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .padding(5.dp)

            )
            Text(text = "$day ")
            if (isOpen) {
                Text("$start - $end")
            }
        }
    }
}