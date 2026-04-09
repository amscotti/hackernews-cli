package io.scotti.hackernews

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Story(
    @Transient val index: Int = 0,
    val title: String = "",
    val by: String = "",
    val descendants: Int = 0,
    val id: Int = 0,
    val kids: List<Int> = emptyList(),
    val score: Int = 0,
    val time: Int = 0,
    val type: String = "",
    val url: String = ""
)
