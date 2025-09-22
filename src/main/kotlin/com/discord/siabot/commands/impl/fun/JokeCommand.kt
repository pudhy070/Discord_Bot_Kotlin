package com.discord.siabot.commands.impl.`fun`

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class JokeCommand : ICommand {
    override val name = "joke"
    override val description = "농담을 들려줍니다."
    override val aliases = listOf("농담", "아재개그")

    private val jokes = listOf(
        "세상에서 가장 뜨거운 과일은? 천도복숭아",
        "왕이 넘어지면 뭐가 될까? 킹콩",
        "소가 계단을 오르면? 소오름",
        "딸기가 직장을 잃으면? 딸기시럽",
        "세상에서 가장 쉬운 숫자는? 190,000 (십구만)"
    )

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val embed = EmbedBuilder()
            .setColor(Color.YELLOW)
            .setTitle("😂 오늘의 농담")
            .setDescription(jokes.random())
            .build()
        event.channel.sendMessageEmbeds(embed).queue()
    }
}
