package com.discord.siabot.commands.impl.moderation

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.util.concurrent.TimeUnit

class BanCommand : ICommand {
    override val name = "ban"
    override val description = "멤버를 서버에서 차단합니다."
    override val aliases = listOf("차단", "밴")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (!event.member!!.hasPermission(Permission.BAN_MEMBERS)) {
            event.channel.sendMessage("❌ 이 명령어를 사용할 권한이 없습니다. (멤버 차단 권한 필요)").queue()
            return
        }

        val mentionedUsers = event.message.mentions.users
        if (mentionedUsers.isEmpty()) {
            event.channel.sendMessage("사용법: `!ban @사용자 [사유]`").queue()
            return
        }

        val target = mentionedUsers.first()!!
        val reason = args.drop(1).joinToString(" ").ifEmpty { "사유 없음" }
        val member = event.guild.getMember(target)

        if (member != null && !event.member!!.canInteract(member)) {
            event.channel.sendMessage("❌ 당신보다 역할이 높거나 같은 멤버는 차단할 수 없습니다.").queue()
            return
        }

        event.guild.ban(target, 0, TimeUnit.SECONDS).reason(reason).queue({
            val embed = EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("🔨 멤버 차단")
                .addField("대상", target.asMention, true)
                .addField("실행자", event.author.asMention, true)
                .addField("사유", reason, false)
                .build()
            event.channel.sendMessageEmbeds(embed).queue()
        }, {
            event.channel.sendMessage("❌ 봇의 역할이 대상보다 낮아 차단할 수 없습니다.").queue()
        })
    }
}
