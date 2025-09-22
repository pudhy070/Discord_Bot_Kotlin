package com.discord.siabot.commands.impl.moderation

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class KickCommand : ICommand {
    override val name = "kick"
    override val description = "멤버를 서버에서 추방합니다."
    override val aliases = listOf("추방")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val member = event.member ?: return
        if (!member.hasPermission(Permission.KICK_MEMBERS)) {
            event.channel.sendMessage("❌ 멤버를 추방할 권한이 없습니다.").queue()
            return
        }

        val mentionedMembers = event.message.mentions.members
        if (mentionedMembers.isEmpty()) {
            event.channel.sendMessage("❌ 추방할 멤버를 멘션해주세요. (예: `!kick @사용자`)").queue()
            return
        }

        val target = mentionedMembers[0]
        val selfMember = event.guild.selfMember
        if (!selfMember.canInteract(target)) {
            event.channel.sendMessage("❌ 봇보다 역할이 높은 멤버는 추방할 수 없습니다.").queue()
            return
        }

        val reason = args.drop(1).joinToString(" ").ifEmpty { "사유 없음" }

        target.kick().reason(reason).queue({
            val embed = EmbedBuilder()
                .setTitle("👢 멤버 추방")
                .setColor(Color.ORANGE)
                .addField("추방된 사용자", target.asMention, true)
                .addField("실행자", member.asMention, true)
                .addField("사유", reason, false)
                .build()
            event.channel.sendMessageEmbeds(embed).queue()
        }, {
            event.channel.sendMessage("❌ 알 수 없는 오류로 해당 멤버를 추방하지 못했습니다.").queue()
        })
    }
}
