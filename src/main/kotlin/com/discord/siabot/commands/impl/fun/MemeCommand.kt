package com.discord.siabot.commands.impl.`fun`

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class MemeCommand : ICommand {
    override val name = "meme"
    override val description = "랜덤 밈을 보여줍니다."
    override val aliases = listOf("밈")

    private val memeUrls = listOf(
        "https://i.imgflip.com/1bij.jpg",
        "https://i.imgflip.com/26am.jpg",
        "https://i.imgflip.com/1g8my4.jpg",
        "https://i.imgflip.com/30b1gx.jpg",
        "https://i.imgflip.com/1ur9b0.jpg"
    )

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val embed = EmbedBuilder()
            .setColor(Color.ORANGE)
            .setTitle("🤣 랜덤 밈")
            .setImage(memeUrls.random())
            .build()
        event.channel.sendMessageEmbeds(embed).queue()
    }
}
