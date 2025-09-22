package com.discord.siabot.commands.impl.`fun`

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import kotlin.random.Random

class CoinflipCommand : ICommand {
    override val name = "coinflip"
    override val description = "동전을 던집니다."
    override val aliases = listOf("동전")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val result = if (Random.nextBoolean()) "앞면" else "뒷면"
        val embed = EmbedBuilder()
            .setColor(if (result == "앞면") Color.YELLOW else Color.GRAY)
            .setTitle("🪙 동전 던지기")
            .setDescription("결과는 **$result** 입니다!")
            .build()
        event.channel.sendMessageEmbeds(embed).queue()
    }
}
