package com.example.gympal2.util.dendrogram

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

const val RANDOM_DEVICE_ID_LEN = 10

fun generateRandomId(length: Int): String {
    val charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { Random.nextInt(0, charPool.length) }
        .map(charPool::get)
        .joinToString("")
}


//fun generateRandomPointsInIsrael(numPoints: Int): Array<DoubleArray> {
//    return Array(numPoints) {
//        generateRandomPointInIsrael()
//    }
//}

fun generateRandomPointInIsrael(): Location {
    val latitude = Random.nextDouble(29.5, 33.3) // Israel's latitude range
    val longitude = Random.nextDouble(34.2, 35.7) // Israel's longitude range
    return Location(latitude, longitude)
}

fun generateRandomDevices(num: Int): List<Device> {
    return List(num) {
        Device(
            location = generateRandomPointInIsrael(),
            id = generateRandomId(RANDOM_DEVICE_ID_LEN)
        )
    }
}

fun calcDistanceMatrix(devices: List<Device>): Array<DoubleArray> {
    val distanceMatrix = Array(devices.size) { DoubleArray(devices.size) }
    for (i in devices.indices) {
        for (j in i + 1 until devices.size) {
            val distance = haversine(
                devices[i].location.latitude,
                devices[i].location.longitude,
                devices[j].location.latitude,
                devices[j].location.longitude
            )
            distanceMatrix[i][j] = distance
            distanceMatrix[j][i] = distance
        }
    }

    return distanceMatrix
}


fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0 // Earth radius in kilometers
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a =
        sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(
            2
        )
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}

fun getZoomLevelThershold(zoomLevel: Int): Double {
    return when (zoomLevel) {
        in 7..9 -> 50.0 // 50 km
        in 10..12 -> 20.0 // 20 km
        in 13..15 -> 5.0 // 5 km
        in 16..18 -> 0.5 // 500 meters
        else -> 0.1 // 100 meters
    }
}


fun calculateCentroid(cluster: List<Device>): Pair<Double, Double> {
    val totalLat = cluster.sumOf { it.location.latitude }
    val totalLng = cluster.sumOf { it.location.longitude }
    val size = cluster.size
    return Pair(totalLat / size, totalLng / size)
}