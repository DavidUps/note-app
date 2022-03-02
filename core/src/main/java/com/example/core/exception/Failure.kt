package com.example.core.exception

sealed class Failure {
    data class Throwable(val throwable: kotlin.Throwable?) : Failure()
    data class CustomError(val errorCode: Int, val errorMessage: String?) : Failure()
}

/**
 * TODO send the log to Crashlytics, Analytics to get all the error's information
 */
fun Failure.toView(): FailureView {
    return when (this) {
        is Failure.Throwable,
        is Failure.CustomError -> FailureView(
            title = "Something happened",
            message = "Try again later"
        )
    }
}

data class FailureView(val title: String? = null, val message: String? = null)
