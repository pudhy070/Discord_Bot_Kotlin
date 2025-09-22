package com.discord.siabot.commands.impl.moderation

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.util.concurrent.TimeUnit

class MuteCommand : ICommand {
    override val name = "mute"
    override val description = "멤버를 일정 시간 동안 채팅 금지시킵니다."
    override val aliases = listOf("뮤트", "타임아웃")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (!event.member!!.hasPermission(Permission.MODERATE_MEMBERS)) {
            event.channel.sendMessage("❌ 이 명령어를 사용할 권한이 없습니다. (멤버 관리 권한 필요)").queue()
            return
        }

        val mentionedMembers = event.message.mentions.members
        if (mentionedMembers.isEmpty() || args.size < 2) {
            event.channel.sendMessage("사용법: `!mute @사용자 <시간(분)> [사유]`").queue()
            return
        }

        val target = mentionedMembers.first()!!
        val duration = args[1].toLongOrNull()
        val reason = args.drop(2).joinToString(" ").ifEmpty { "사유 없음" }

        if (duration == null || duration <= 0) {
            event.channel.sendMessage("❌ 유효한 시간을 분 단위로 입력해주세요.").queue()
            return
        }

        if (!event.member!!.canInteract(target)) {
            event.channel.sendMessage("❌ 당신보다 역할이 높거나 같은 멤버는 뮤트할 수 없습니다.").queue()
            return
        }

        target.timeoutFor(duration, TimeUnit.MINUTES).reason(reason).queue({
            val embed = EmbedBuilder()
                .setColor(Color.YELLOW)
                .setTitle("🔇 멤버 뮤트")
                .addField("대상", target.asMention, true)
                .addField("시간", "${duration}분", true)
                .addField("실행자", event.author.asMention, true)
                .addField("사유", reason, false)
                .build()
            event.channel.sendMessageEmbeds(embed).queue()
        }, {
            event.channel.sendMessage("❌ 봇의 역할이 대상보다 낮아 뮤트할 수 없습니다.").queue()
        })
    }
}
