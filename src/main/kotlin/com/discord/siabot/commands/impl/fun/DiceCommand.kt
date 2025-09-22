package com.discord.siabot.commands.impl.`fun`

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import kotlin.random.Random

class DiceCommand : ICommand {
    override val name = "dice"
    override val description = "주사위를 굴립니다."
    override val aliases = listOf("주사위")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val sides = args.firstOrNull()?.toIntOrNull() ?: 6
        if (sides < 2 || sides > 1000) {
            event.channel.sendMessage("❌ 주사위 면은 2에서 1000 사이여야 합니다.").queue()
            return
        }

        val result = Random.nextInt(1, sides + 1)

        val embed = EmbedBuilder()
            .setColor(Color.WHITE)
            .setTitle("🎲 주사위 굴리기")
            .setDescription("${sides}면 주사위를 굴려 **${result}** 이(가) 나왔습니다!")
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
