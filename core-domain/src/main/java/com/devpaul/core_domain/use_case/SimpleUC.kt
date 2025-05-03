package com.devpaul.core_domain.use_case

sealed interface SimpleUC {

    interface ParamsAndResult<Parameters, Result>: SimpleUC {
        suspend operator fun invoke(params: Parameters): Result
    }

    interface OnlyParams<Parameters>: SimpleUC {
        suspend operator fun invoke(params: Parameters)
    }

    interface OnlyResult<Result>: SimpleUC {
        suspend operator fun invoke(): Result
    }

    interface Empty: SimpleUC {
        suspend operator fun invoke()
    }
}