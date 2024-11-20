package com.example.gympal2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.gympal2.core.ui.navbar.TopNavBar
import com.example.gympal2.feature.gym.Gym
import com.example.gympal2.feature.gym.GymViewModel
import com.example.gympal2.feature.map.Map
import com.example.gympal2.util.dendrogram.initDendrogram
import org.koin.androidx.compose.koinViewModel

const val numPoints = 10

@Composable
fun HomeScreen(navController: NavController, onLogout: () -> Unit) {
    val gymViewModel: GymViewModel = koinViewModel()
    val gyms by gymViewModel.getGyms().collectAsState()
    var selectedGym by remember { mutableStateOf<Gym?>(null) }
    fun onChangeGym(newGym: Gym) {
        selectedGym = newGym
    }

    initDendrogram()

//    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//    val startTime = LocalDateTime.now()
//    val points = generateRandomPointsInIsrael(numPoints)
//    val n = points.size
//    val distanceMatrix = Array(n) { DoubleArray(n) }
//
//    // Distance Matrix calculation
//    for (i in 0 until n) {
//        for (j in 0 until n) {
//            if (i == j) {
//                distanceMatrix[i][j] = 0.0
//            } else {
//                val (lat1, lon1) = points[i]
//                val (lat2, lon2) = points[j]
//                distanceMatrix[i][j] = haversine(lat1, lon1, lat2, lon2)
//            }
//        }
//    }
//
//
////    val clusters = hclust(distancesMatrix, "complete")
//
////    val dend = Dendrogram(clusters, points.size)
//
//
//    Log.d("DAVID", "Start time: $startTime")
//    Log.d("DAVID", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ now starting...")
//    val linkage = CompleteLinkage(distanceMatrix)
//    val dendrogram = HierarchicalClustering.fit(linkage)
//
//    // Partition the data into clusters
//    val clusterLabels = dendrogram.partition(3)
//    dendrogram.partition(1)
//
//    Log.d("DAVID", "clusters: ${clusterLabels.joinToString(", ")}")
//    val clusters = mutableMapOf<Int, MutableList<DoubleArray>>()
//
//    for (i in clusterLabels.indices) {
//        val cluster = clusterLabels[i]
//        clusters.computeIfAbsent(cluster) { mutableListOf() }.add(points[i])
//    }
//
//    clusters.forEach { (cluster, points) ->
//        Log.d("DAVID", "Cluster $cluster:")
//        points.forEach { point ->
//            Log.d("DAVID", "Latitude: ${point[0]}, Longitude: ${point[1]}")
//        }
//    }
//
//    Log.d("DAVID", "clusterLabels list size: ${clusterLabels.indices.joinToString(", ")}")
//    Log.d("DAVID", "SIZE of tree: ${dendrogram.height().joinToString(", ")}")
//    Log.d("DAVID", "Number of clusters: ${dendrogram.height().size}")
//    Log.d(
//        "DAVID",
//        "Top cluster: ${
//            dendrogram.partition(dendrogram.height().last()).joinToString(", ")
//        }"
//    )
//    for (i in 0 until dendrogram.height().size) {
//        val clusterSize = dendrogram.tree()[i].size
//        val dendogramHeight = dendrogram.height()[i]
////        Log.d("DAVID", "Cluster $i: Size = $clusterSize")
////        Log.d("DAVID", "Dendrogram $i: Height = $dendogramHeight")
//    }
//
//
////    dendrogram.tree().map { d ->
////
////        d.iterator().forEach {
////
////            Log.d("DAVID", "Inside the tree: $it")
////        }
////    }
//
//    val endTime = LocalDateTime.now()
//    val timeDiff = Duration.between(startTime, endTime)
//
//    Log.d("DAVID", "End time: $endTime")
//
//    Log.d("DAVID", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
//    Log.d("DAVID", "timeDiff IS: ${timeDiff.toMillis()}ms")

//    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    Scaffold(
        topBar = { TopNavBar(onLogout) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Map(navController, gyms, selectedGym) { gym -> onChangeGym(gym) }
        }
    }
}
