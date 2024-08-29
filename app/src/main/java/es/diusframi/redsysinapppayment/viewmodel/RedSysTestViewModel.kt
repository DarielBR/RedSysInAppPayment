package es.diusframi.redsysinapppayment.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redsys.tpvvinapplibrary.ErrorResponse
import com.redsys.tpvvinapplibrary.IPaymentResult
import com.redsys.tpvvinapplibrary.ResultResponse
import com.redsys.tpvvinapplibrary.TPVV
import com.redsys.tpvvinapplibrary.TPVVConstants
import es.diusframi.redsystest.viewmodel.state.ErrorState
import es.diusframi.redsystest.viewmodel.state.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RedSysTestViewModel: ViewModel() {
    /**
     * State representing the overall UI state.
     */
    var uiState by mutableStateOf(UIState())
        private set
    /**
     * State representing the error state of the application.
     */
    var errorState by mutableStateOf(ErrorState())
        private set

    /**
     * Job for coroutine/job cancellation
     */
    private var redSysJob: Job? = null

    init {

    }

    /*region UIState*/
    fun setLoadingState(newValue: Boolean){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = newValue)
        }
    }

    fun setResultResponse(newValue: ResultResponse?){
        viewModelScope.launch {
            uiState = uiState.copy(resultResponse = newValue)
        }
    }
    /*endregion*/

    /*region ErrorState*/
    /**
     * Updates the `errorState` with a new error message.
     *
     * @param newValue The new error message to be set in the error state.
     */
    private fun setErrorState(newValue: String){
        viewModelScope.launch {
            errorState = errorState.copy(errorMessage = newValue)
        }
    }
    /**
     * Resets the `errorState` to its default values.
     */
    private fun setErrorState(){
        viewModelScope.launch {
            errorState = ErrorState()
        }
    }
    /*endregion*/

    /*region RedSys*/
    fun doRedSysPayment(amount: Double, context: Context){
        setLoadingState(true)
        setErrorState()
        val redSysCallback = object: IPaymentResult{
            override fun paymentResultOK(p0: ResultResponse?) {
                setResultResponse(p0)
            }

            override fun paymentResultKO(p0: ErrorResponse?) {
                p0?.let {
                    setErrorState("${p0.code}: ${p0.desc}")
                } ?: setErrorState("Unknown error")
            }

        }
        redSysJob = viewModelScope.launch(Dispatchers.IO) {
            TPVV.doDirectPayment(context, "A00000001", amount, TPVVConstants.PAYMENT_TYPE_NORMAL, TPVVConstants.REQUEST_REFERENCE, "Test Product", null, redSysCallback)
        }
    }
    /**
     * This function will cancel a Http call assigned to [redSysJob] object. This is specially useful
     * to handle http calls made through use cases->repository when the use the same retrofit object.
     */
    fun cancelRedSysJob(){
        redSysJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        redSysJob?.cancel()
    }
    /**/
}