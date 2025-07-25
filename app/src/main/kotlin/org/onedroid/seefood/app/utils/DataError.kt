package org.onedroid.seefood.app.utils

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN,
        NO_DATA
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}