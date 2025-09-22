package com.discord.siabot.commands.impl.moderation

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.time.Instant

class AnnounceCommand : ICommand {
    override val name = "announce"
    override val description = "지정된 채널에 공지를 보냅니다."
    override val aliases = listOf("공지")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (!event.member!!.hasPermission(Permission.ADMINISTRATOR)) {
            event.channel.sendMessage("❌ 이 명령어를 사용할 권한이 없습니다. (서버 관리자 권한 필요)").queue()
            return
        }

        val mentionedChannels = event.message.mentions.channels
        if (mentionedChannels.isEmpty() || args.size < 2) {
            event.channel.sendMessage("사용법: `!announce #채널 <공지 내용>`").queue()
            return
        }

        val targetChannel = mentionedChannels.first() as TextChannel
        val message = args.drop(1).joinToString(" ")

        val embed = EmbedBuilder()
            .setColor(Color.CYAN)
            .setTitle("📢 공지사항")
            .setDescription(message)
            .setTimestamp(Instant.now())
            .setFooter("${event.guild.name} | 작성자: ${event.author.name}")
            .build()

        targetChannel.sendMessageEmbeds(embed).queue {
            event.channel.sendMessage("✅ `${targetChannel.name}` 채널에 공지를 보냈습니다.").queue()
        }
    }
}
