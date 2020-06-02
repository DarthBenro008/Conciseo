package com.benrostudios.conciseo.data.models
import com.google.gson.annotations.SerializedName


data class ShortnerResponse(
    val ok: Boolean,
    val result: ShortnerResult
)

data class ShortnerResult(
    val code: String,
    @SerializedName("full_share_link")
    val fullShareLink: String,
    @SerializedName("full_short_link")
    val fullShortLink: String,
    @SerializedName("full_short_link2")
    val fullShortLink2: String,
    @SerializedName("original_link")
    val originalLink: String,
    @SerializedName("share_link")
    val shareLink: String,
    @SerializedName("short_link")
    val shortLink: String,
    @SerializedName("short_link2")
    val shortLink2: String
)