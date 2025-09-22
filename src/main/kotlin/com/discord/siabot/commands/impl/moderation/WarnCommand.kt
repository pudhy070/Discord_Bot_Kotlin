package com.discord.siabot.commands.impl.moderation

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class WarnCommand : ICommand {
    override val name = "warn"
    override val description = "멤버에게 경고를 줍니다."
    override val aliases = listOf("경고")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (event.member?.hasPermission(Permission.MODERATE_MEMBERS) != true) {
            event.channel.sendMessage("❌ 이 명령어를 사용할 권한이 없습니다.").queue()
            return
        }

        val targetMember = event.message.mentions.members.firstOrNull()
        if (targetMember == null) {
            event.channel.sendMessage("사용법: `!warn @멤버 <사유>`").queue()
            return
        }

        val reason = if (args.size > 1) args.drop(1).joinToString(" ") else "사유 없음"
        val embed = EmbedBuilder()
            .setColor(Color.YELLOW)
            .setTitle("⚠️ 경고")
            .setDescription("${targetMember.asMention}님에게 경고가 주어졌습니다.")
            .addField("사유", reason, false)
            .setFooter("실행자: ${event.author.name}")
            .build()

        event.channel.sendMessageEmbeds(embed).queue()

        targetMember.user.openPrivateChannel().queue {
            it.sendMessageEmbeds(embed).queue()
        }
    }
}
