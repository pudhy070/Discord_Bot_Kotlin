package com.discord.siabot.services

import java.util.concurrent.ConcurrentHashMap

data class Poll(
    var messageId: Long,
    val channelId: String,
    val question: String,
    val options: List<String>,
    val votes: MutableMap<String, Int> = mutableMapOf() // userId, optionIndex
)

object PollService {
    val numberEmojis = listOf("1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "🔟")
    private val activePolls = ConcurrentHashMap<Long, Poll>() // messageId to Poll

    fun createPoll(channelId: String, question: String, options: List<String>): Poll {
        val poll = Poll(0L, channelId, question, options) // messageId는 나중에 설정
        return poll
    }
}
