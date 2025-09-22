package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class PasswordCommand : ICommand {
    override val name = "password"
    override val description = "안전한 랜덤 비밀번호를 생성합니다."
    override val aliases = listOf("pw")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val length = args.firstOrNull()?.toIntOrNull() ?: 16
        if (length !in 8..128) {
            event.channel.sendMessage("❌ 비밀번호 길이는 8에서 128 사이여야 합니다.").queue()
            return
        }
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?"
        val password = (1..length).map { chars.random() }.joinToString("")

        event.author.openPrivateChannel().queue { privateChannel ->
            privateChannel.sendMessage("🔑 생성된 비밀번호: ```$password```").queue(
                { event.channel.sendMessage("✅ DM으로 안전하게 비밀번호를 전송했습니다!").queue() },
                { event.channel.sendMessage("❌ DM을 보낼 수 없습니다. DM 설정을 확인해주세요.").queue() }
            )
        }
    }
}
