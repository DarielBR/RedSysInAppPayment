package es.diusframi.redsysinapppayment.di

import android.app.Application
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.redsys.tpvvinapplibrary.TPVV
import com.redsys.tpvvinapplibrary.TPVVConfiguration
import com.redsys.tpvvinapplibrary.TPVVConstants
import com.redsys.tpvvinapplibrary.UIDirectPaymentConfig
import es.diusframi.redsysinapppayment.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * [RedSysTestApplication] represents current android application. The main purpose for this class is
 * dependency injection initialization.
 * Inherits from [Application]
 */
class RedSysTestApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Redsys initialization
         */
        TPVVConfiguration.setLicense("sq7HjrUOBfKmC576ILgskD5srU870gJ7")
        TPVVConfiguration.setEnvironment(TPVVConstants.ENVIRONMENT_TEST)
        TPVVConfiguration.setFuc("999008881")
        TPVVConfiguration.setTerminal("001")
        TPVVConfiguration.setCurrency("978")
        /**
         * Koin framework initialization for the current application
         */
        startKoin {
            androidContext(this@RedSysTestApplication)

            val allModules = listOf(
                viewModelModule,
            )
            modules(allModules)
        }
    }
}