package es.diusframi.redsystest.viewmodel.state

/**
 * A data class representing the state of an error.
 *
 * @property errorMessage A nullable string that holds the error message. If it is null or empty, there is no error.
 */
data class ErrorState(
    val errorMessage: String? = null,
){
    /**
     * Checks if there is an error based on the presence of an error message.
     *
     * @return `true` if an error message exists (is not null or empty), otherwise `false`.
     */
    fun onError(): Boolean = !errorMessage.isNullOrEmpty()
}