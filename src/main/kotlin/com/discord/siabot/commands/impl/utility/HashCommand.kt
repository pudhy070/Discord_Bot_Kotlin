package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.security.MessageDigest

class HashCommand : ICommand {
    override val name = "hash"
    override val description = "텍스트를 해시합니다 (SHA-256)."

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("사용법: `!hash <텍스트>`").queue()
            return
        }
        val text = args.joinToString(" ")
        val bytes = MessageDigest.getInstance("SHA-256").digest(text.toByteArray())
        val result = bytes.joinToString("") { "%02x".format(it) }
        event.channel.sendMessage("SHA-256: ```$result```").queue()
    }
}
