package com.example.gympal2.util.dendrogram

import android.util.Log
import smile.clustering.HierarchicalClustering
import smile.clustering.linkage.CompleteLinkage
import java.time.Duration
import java.time.Instant

data class Location(
    val latitude: Double,
    val longitude: Double
)

data class Device(
    val id: String,
    val location: Location,
)

data class ClusterForZoomLevel(
    val ids: List<String>,
    val centroid: Location,
    val size: Int,
)

data class CachedData(
    val dendrogram: HierarchicalClustering,
    val devices: List<Device>
)

var cachedData: CachedData? = null

fun createDendrogram(devices: List<Device>): CachedData {
    val distanceMatrix = calcDistanceMatrix(devices)

    val linkage = CompleteLinkage(distanceMatrix)
    val dendrogram = HierarchicalClustering.fit(linkage)

    cachedData = CachedData(dendrogram, devices)

    return cachedData as CachedData
}

fun getClusterForZoomLevel(zoomLevel: Int): List<ClusterForZoomLevel> {
    val threshold = getZoomLevelThershold(zoomLevel)

    if (cachedData == null) return listOf()

    val dendrogram = cachedData!!.dendrogram
    val devices = cachedData!!.devices

    val clusters = mutableListOf<MutableList<Device>>()
    val clusterIds = dendrogram.partition(threshold)

    val maxClusterId = clusterIds.maxOrNull() ?: 0
    for (i in 0..maxClusterId) {
        clusters.add(mutableListOf())
    }

    clusterIds.forEachIndexed { index, clusterId ->
        if (clusters.size <= clusterId) {
            clusters.add(mutableListOf())
        }
        clusters[clusterId].add(devices[index])
    }

    return clusters.map { cluster ->
        val ids = cluster.map { it.id }
        val centroid = calculateCentroid(cluster)
        ClusterForZoomLevel(
            ids = ids,
            centroid = Location(centroid.first, centroid.second),
            size = cluster.size
        )
    }

}

fun initDendrogram(devicesNum: Int = 6000, zoomLevel: Int = 7) {
    val startTime = Instant.now()

    val devices = generateRandomDevices(devicesNum)
    createDendrogram(devices)
    val clusters = getClusterForZoomLevel(zoomLevel)

    val endTime = Instant.now()
    val duration = Duration.between(startTime, endTime)
    val timeDiff = duration.toMillis()

    Log.d("DAVID", "Time spent in calculation for $devicesNum devices $timeDiff ms")

    clusters.forEachIndexed { index, cluster ->
        Log.d("DAVID", "Cluster $index:")
        Log.d("DAVID", "  IDs: ${cluster.ids}")
        Log.d("DAVID", "  Centroid: (${cluster.centroid.latitude}, ${cluster.centroid.longitude})")
        Log.d("DAVID", "  Size: ${cluster.size}")
    }

}