package com.discord.siabot.commands.impl.general

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class VersionCommand : ICommand {
    override val name = "version"
    override val description = "봇의 버전 정보를 보여줍니다."
    override val aliases = listOf("버전")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val jdaVersion = net.dv8tion.jda.api.JDAInfo.VERSION
        val embed = EmbedBuilder()
            .setColor(Color.DARK_GRAY)
            .setTitle("ℹ️ 봇 정보")
            .addField("봇 버전", "2.0.0", true)
            .addField("개발자", "Sia", true)
            .addField("라이브러리", "JDA $jdaVersion", true)
            .addField("언어", "Kotlin", true)
            .addField("GitHub", "[아직 등록 안함](https://github.com/your-repo)", false)
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
