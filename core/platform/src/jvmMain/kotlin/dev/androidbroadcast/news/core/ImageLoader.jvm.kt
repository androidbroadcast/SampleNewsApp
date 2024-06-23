package dev.androidbroadcast.news.core

import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.disk.directory
import okio.FileSystem
import java.io.File

internal actual fun newDiskCache(context: PlatformContext): DiskCache? {
    val tmpDirPath = System.getProperty("java.io.tmpdir")
    val tmpDir = File(tmpDirPath)
    return DiskCache.Builder()
        .directory(tmpDir)
        .maxSizePercent(0.02)
        .minimumMaxSizeBytes(10 * 1024 * 1024) // 10 MBytes
        .build()
}
