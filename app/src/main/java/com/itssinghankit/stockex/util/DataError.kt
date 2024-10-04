package com.example.sharesphere.util

sealed interface DataError: Error {
    enum class Network:DataError{
        NO_INTERNET,
        INTERNAL_SERVER_ERROR,
        NOT_FOUND,
        UNAUTHORIZED,
        TIMEOUT,
        UNKNOWN,
        ALREADY_CREATED,
        BAD_REQUEST,
        PAYLOAD_TOO_LARGE
    }
    enum class Local:DataError{
        DISK_FULL,
        STORAGE_PERMISSION_DENIED,
        EMPTY,
        INCORRECT_LENGTH
    }
}