package es.diusframi.redsystest.viewmodel.state

import com.redsys.tpvvinapplibrary.ResultResponse

/**
 * A data class representing the overall UI state of the application.
 */
data class UIState (
    val terminalFUC: String = "",
    val terminalSN: String = "",
    val loginToken: String = "",
    val licenceToken: String = "",
    //Loyalty State
    val isLoading: Boolean? = false,
    val resultResponse: ResultResponse? = null,
)