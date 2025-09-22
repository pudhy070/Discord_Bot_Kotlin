package com.discord.siabot.commands.impl.general

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.time.Instant

class UserInfoCommand : ICommand {
    override val name = "userinfo"
    override val description = "사용자의 정보를 보여줍니다."
    override val aliases = listOf("유저정보")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val member: Member = if (event.message.mentions.members.isNotEmpty()) {
            event.message.mentions.members.first()!!
        } else {
            event.member!!
        }
        val user = member.user

        val embed = EmbedBuilder()
            .setColor(Color.GREEN)
            .setTitle("👤 ${user.name}님의 정보")
            .setThumbnail(user.effectiveAvatarUrl)
            .addField("이름", user.asTag, true)
            .addField("ID", user.id, true)
            .addField("봇 여부", if (user.isBot) "✔️" else "❌", true)
            .addField("계정 생성일", "<t:${user.timeCreated.toEpochSecond()}:F>", false)
            .addField("서버 참가일", "<t:${member.timeJoined.toEpochSecond()}:F>", false)
            .addField("역할 (${member.roles.size}개)", member.roles.take(5).joinToString(" ") { it.asMention } + if (member.roles.size > 5) "..." else "", false)
            .setTimestamp(Instant.now())
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
