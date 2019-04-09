package com.example.domain.utils

class UnauthorizedServerException(code: String?, message: String?) : ServerException(code, message)