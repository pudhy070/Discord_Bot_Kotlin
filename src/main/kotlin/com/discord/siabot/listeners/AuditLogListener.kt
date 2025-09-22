package com.discord.siabot.listeners

import com.discord.siabot.config.Config
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.events.message.MessageDeleteEvent
import net.dv8tion.jda.api.events.message.MessageUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.time.Instant

class AuditLogListener : ListenerAdapter() {

    private fun sendLog(guild: net.dv8tion.jda.api.entities.Guild, embed: EmbedBuilder) {
        val logChannelId = Config.LOG_CHANNEL_ID
        if (!logChannelId.isNullOrEmpty()) {
            val logChannel = guild.getTextChannelById(logChannelId)
            logChannel?.sendMessageEmbeds(embed.build())?.queue()
        }
    }

    override fun onMessageDelete(event: MessageDeleteEvent) {
        val embed = EmbedBuilder()
            .setTitle("🗑️ 메시지 삭제됨")
            .setColor(Color.RED)
            .addField("채널", event.channel.asMention, false)
            .setTimestamp(Instant.now())

        sendLog(event.guild, embed)
    }

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        val embed = EmbedBuilder()
            .setAuthor("${event.user.name} 님이 서버에 참여했습니다.", null, event.user.effectiveAvatarUrl)
            .setColor(Color.GREEN)
            .setTimestamp(Instant.now())
        sendLog(event.guild, embed)
    }

    override fun onGuildMemberRemove(event: GuildMemberRemoveEvent) {
        val embed = EmbedBuilder()
            .setAuthor("${event.user.name} 님이 서버에서 나갔습니다.", null, event.user.effectiveAvatarUrl)
            .setColor(Color.ORANGE)
            .setTimestamp(Instant.now())
        sendLog(event.guild, embed)
    }

    override fun onMessageUpdate(event: MessageUpdateEvent) {
        if (event.author.isBot) return

        val embed = EmbedBuilder()
            .setTitle("✏️ 메시지 수정됨")
            .setColor(Color.YELLOW)
            .addField("사용자", event.author.asMention, true)
            .addField("채널", event.channel.asMention, true)
            .addField("수정 후", "```${event.message.contentRaw}```", false)
            .addField("메시지 링크", "[바로가기](${event.message.jumpUrl})", false)
            .setTimestamp(Instant.now())

        sendLog(event.guild, embed)
    }
}

