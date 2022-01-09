package com.maluku.sma_rt.api.notification

data class SendNotification (
    val data: NotificationData,
    val to: String
)