package com.discord.siabot.commands.impl.general

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class PingCommand : ICommand {
    override val name = "ping"
    override val description = "봇의 지연시간을 확인합니다."
    override val aliases = listOf("퐁")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val startTime = System.currentTimeMillis()
        event.channel.sendMessage("🏓 퐁! 계산 중...").queue { message ->
            val latency = System.currentTimeMillis() - startTime
            val apiPing = event.jda.gatewayPing

            val embed = EmbedBuilder()
                .setTitle("🏓 퐁!")
                .addField("메시지 지연시간", "${latency}ms", true)
                .addField("API 지연시간", "${apiPing}ms", true)
                .setColor(if (latency < 150) Color.GREEN else if (latency < 300) Color.ORANGE else Color.RED)
                .build()

            message.editMessageEmbeds(embed).queue()
        }
    }
}
