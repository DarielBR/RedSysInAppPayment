package es.diusframi.redsysinapppayment.di.modules

import es.diusframi.redsysinapppayment.viewmodel.RedSysTestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * This Koin module provides dependency injection [RedSysTestViewModel] class.
 * It will be handled by Koin as a singleton instance.
 *
 */
val viewModelModule = module {
    viewModel {
        RedSysTestViewModel()
    }
}