package org.opencoin.core.messages

class ResponseCddSerial(
    `type`: String = "response cdd serial",
    message_reference: Int,
    status_code: Int,
    status_description: String,
    serial: Int)