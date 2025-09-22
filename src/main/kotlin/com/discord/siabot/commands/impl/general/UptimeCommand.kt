package com.discord.siabot.commands.impl.general

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit

class UptimeCommand : ICommand {
    override val name = "uptime"
    override val description = "봇의 가동 시간을 보여줍니다."
    override val aliases = listOf("가동시간")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val uptimeMillis = ManagementFactory.getRuntimeMXBean().uptime

        val days = TimeUnit.MILLISECONDS.toDays(uptimeMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(uptimeMillis) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMillis) % 60

        val uptimeString = String.format("%d일 %d시간 %d분 %d초", days, hours, minutes, seconds)

        val embed = EmbedBuilder()
            .setColor(Color.YELLOW)
            .setTitle("⏰ 봇 가동 시간")
            .setDescription(uptimeString)
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
