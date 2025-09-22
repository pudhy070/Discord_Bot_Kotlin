package com.discord.siabot.listeners

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.time.Instant

class WelcomeListener : ListenerAdapter() {
    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        val welcomeChannel = event.guild.systemChannel ?: event.guild.defaultChannel
        val member = event.member

        val embed = EmbedBuilder()
            .setTitle("🎉 새로운 멤버가 입장했습니다!")
            .setDescription("${member.asMention}님, **${event.guild.name}**에 오신 것을 환영합니다!")
            .setColor(Color.GREEN)
            .setThumbnail(member.user.effectiveAvatarUrl)
            .addField("멤버 수", "${event.guild.memberCount}명", true)
            .addField("계정 생성일", "<t:${member.user.timeCreated.toEpochSecond()}:F>", true)
            .setTimestamp(Instant.now())

        if (welcomeChannel is MessageChannel) {
            welcomeChannel.sendMessageEmbeds(embed.build()).queue()
        }
    }
}

