package es.diusframi.redsysinapppayment.service.redsys

import com.redsys.tpvvinapplibrary.ErrorResponse
import com.redsys.tpvvinapplibrary.IPaymentResult
import com.redsys.tpvvinapplibrary.ResultResponse

class IPaymentImplementation: IPaymentResult {
    override fun paymentResultOK(p0: ResultResponse?) {

    }

    override fun paymentResultKO(p0: ErrorResponse?) {

    }

}