package com.ab.core.utils.base

interface BaseIOUseCase<IN, OUT> {
    operator fun invoke(input: IN): OUT
}

interface BaseOUseCase<OUT> {
    operator fun invoke(): OUT
}

interface BaseIUseCase<IN> {
    operator fun invoke(input: IN)
}
