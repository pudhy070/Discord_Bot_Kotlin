package com.discord.siabot.commands.impl.general

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.time.Instant

class ServerInfoCommand : ICommand {
    override val name = "serverinfo"
    override val description = "서버의 정보를 보여줍니다."
    override val aliases = listOf("서버정보", "info")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val guild = event.guild
        val embed = EmbedBuilder()
            .setColor(Color.BLUE)
            .setTitle("📊 ${guild.name} 서버 정보")
            .setThumbnail(guild.iconUrl)
            .addField("서버 ID", guild.id, true)
            .addField("서버 소유자", guild.owner?.asMention ?: "알 수 없음", true)
            .addField("멤버 수", guild.memberCount.toString(), true)
            .addField("서버 생성일", "<t:${guild.timeCreated.toEpochSecond()}:F>", false)
            .addField("채널 수", (guild.textChannels.size + guild.voiceChannels.size).toString(), true)
            .addField("역할 수", guild.roles.size.toString(), true)
            .addField("부스트 레벨", "레벨 ${guild.boostTier.key} (${guild.boostCount}회)", true)
            .setTimestamp(Instant.now())
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
