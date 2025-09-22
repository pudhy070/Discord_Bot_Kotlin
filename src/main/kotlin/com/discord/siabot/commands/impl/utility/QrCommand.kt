package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class QrCommand : ICommand {
    override val name = "qr"
    override val description = "QR 코드를 생성합니다."
    override val aliases = listOf("큐알")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("사용법: `!qr <텍스트 또는 URL>`").queue()
            return
        }
        val text = args.joinToString(" ")
        val encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8.toString())
        val qrApiUrl = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=$encodedText"

        val embed = EmbedBuilder()
            .setColor(Color.DARK_GRAY)
            .setTitle("📱 QR 코드 생성 완료")
            .setDescription("입력 내용: `$text`")
            .setImage(qrApiUrl)
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
