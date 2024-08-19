package com.example.gympal2.feature.map.GymBottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
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
import com.example.gympal2.feature.gym.Gym

@Composable
fun WorkHoursTable(workingHours: List<Gym.WorkHours>) {

    for (workHours in workingHours) {
        WorkHours(workHours)
        HorizontalDivider(Modifier.padding(end = 100.dp))
    }
}

@Composable
fun WorkHours(workHours: Gym.WorkHours) {
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