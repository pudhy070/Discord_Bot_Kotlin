package com.discord.siabot.commands.impl.general

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class AvatarCommand : ICommand {
    override val name = "avatar"
    override val description = "사용자의 프로필 사진을 보여줍니다."
    override val aliases = listOf("아바타", "프사")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val user: User = if (event.message.mentions.users.isNotEmpty()) {
            event.message.mentions.users.first()!!
        } else {
            event.author
        }

        val avatarUrl = user.effectiveAvatarUrl + "?size=1024"

        val embed = EmbedBuilder()
            .setColor(Color.MAGENTA)
            .setTitle("🖼️ ${user.name}님의 아바타")
            .setImage(avatarUrl)
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
