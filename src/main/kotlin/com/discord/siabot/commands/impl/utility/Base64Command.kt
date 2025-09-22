package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

class Base64Command : ICommand {
    override val name = "base64"
    override val description = "텍스트를 Base64로 인코딩/디코딩합니다."

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.size < 2) {
            event.channel.sendMessage("사용법: `!base64 [encode|decode] <텍스트>`").queue()
            return
        }
        val mode = args[0].lowercase()
        val text = args.drop(1).joinToString(" ")

        try {
            val result = when (mode) {
                "encode" -> Base64.getEncoder().encodeToString(text.toByteArray())
                "decode" -> String(Base64.getDecoder().decode(text))
                else -> {
                    event.channel.sendMessage("❌ `encode` 또는 `decode`를 선택해주세요.").queue()
                    return
                }
            }
            event.channel.sendMessage("```$result```").queue()
        } catch (e: IllegalArgumentException) {
            event.channel.sendMessage("❌ 잘못된 Base64 문자열입니다.").queue()
        }
    }
}
