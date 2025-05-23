package com.example.eccomerce_app.dto.response

import com.example.hotel_mobile.services.kSerializeChanger.UUIDKserialize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import java.util.UUID

@Serializable
data  class VarientResponseDto(
    @Serializable(with= UUIDKserialize::class)
    var id:UUID,
    var name:String
)