import java.nio.file.Files
import java.nio.file.Paths
import java.time.Instant
import java.time.temporal.ChronoUnit

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

}

buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin)
    }
}

tasks.register<Sync>("cacheLatestToMavenLocal") {
    val cacheFromTimeMillis = Instant.now().minus(10, ChronoUnit.DAYS)

    from(fileTree(File(gradle.gradleUserHomeDir, "caches/modules-2/files-2.1"))) {
        // Filter files that have been modified in the last month
        include { file ->
            val javaFile = file.file  // Convert the Gradle file to a Java File
            val lastModifiedTime = Instant.ofEpochMilli(
                Files.getLastModifiedTime(Paths.get(javaFile.absolutePath)).toMillis()
            )
            lastModifiedTime.isAfter(cacheFromTimeMillis)
        }
    }
    into("${rootDir}/local-m2")

    // Last copy target wins
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    eachFile {
        val parts = relativePath.toString().split("/")
        // Construct a Maven repo file tree from the path
        path = "${parts[0].replace('.', '/')}/${parts[1]}/${parts[2]}/${parts[4]}"
    }
    includeEmptyDirs = false
}

tasks.register<Sync>("cacheToMavenLocal") {
    from(fileTree(File(gradle.gradleUserHomeDir, "caches/modules-2/files-2.1")))
    into("${rootDir}/local-m2")

    // Last copy target wins
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    eachFile {
        val parts = relativePath.toString().split("/")
        // Construct a maven repo file tree from the path
        path = "${parts[0].replace('.', '/')}/${parts[1]}/${parts[2]}/${parts[4]}"
    }
    includeEmptyDirs = false
}