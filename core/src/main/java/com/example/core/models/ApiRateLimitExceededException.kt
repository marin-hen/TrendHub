package com.example.core.models

class ApiRateLimitExceededException(
    override val message: String,
) : BusinessException.WarningException(cause = null)