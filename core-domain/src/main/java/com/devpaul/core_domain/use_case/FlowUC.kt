package com.devpaul.core_domain.use_case

import kotlinx.coroutines.flow.Flow

sealed interface FlowUC {

    interface ParamsAndResult<Parameters, Result>: FlowUC {
        suspend operator fun invoke(params: Parameters): Flow<Result>
    }

    interface OnlyResult<Result>: FlowUC {
        suspend operator fun invoke(): Flow<Result>
    }
}