package com.discord.siabot.commands.impl.music

import com.discord.siabot.commands.ICommand
import com.discord.siabot.music.PlayerManager
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class NowPlayingCommand : ICommand {
    override val name = "nowplaying"
    override val description = "현재 재생 중인 곡 정보를 봅니다."
    override val aliases = listOf("np", "현재곡")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = PlayerManager.getMusicManager(event.guild)
        val playingTrack = musicManager.player.playingTrack

        if (playingTrack == null) {
            event.channel.sendMessage("❌ 현재 재생 중인 곡이 없습니다.").queue()
            return
        }

        val embed = EmbedBuilder()
            .setColor(Color.CYAN)
            .setTitle("🎵 현재 재생 중")
            .setDescription("**[${playingTrack.info.title}](${playingTrack.info.uri})**")
            .build()
        event.channel.sendMessageEmbeds(embed).queue()
    }
}

