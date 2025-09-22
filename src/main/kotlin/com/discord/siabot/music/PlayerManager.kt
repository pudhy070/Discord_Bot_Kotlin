package com.discord.siabot.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import java.awt.Color

object PlayerManager {
    private val musicManagers: MutableMap<Long, GuildMusicManager> = mutableMapOf()
    private val audioPlayerManager: DefaultAudioPlayerManager

    init {
        audioPlayerManager = DefaultAudioPlayerManager()

        // 유튜브 기본 소스 매니저 등록
        audioPlayerManager.registerSourceManager(YoutubeAudioSourceManager())
        audioPlayerManager.registerSourceManager(SoundCloudAudioSourceManager.createDefault())
        audioPlayerManager.registerSourceManager(TwitchStreamAudioSourceManager())

        AudioSourceManagers.registerLocalSource(audioPlayerManager)
    }

    fun getMusicManager(guild: Guild): GuildMusicManager {
        return musicManagers.computeIfAbsent(guild.idLong) {
            val musicManager = GuildMusicManager(audioPlayerManager)
            guild.audioManager.sendingHandler = musicManager.sendHandler
            musicManager
        }
    }

    fun loadAndPlay(channel: MessageChannel, trackUrl: String) {
        val guild = (channel as? GuildMessageChannel)?.guild ?: return
        val musicManager = getMusicManager(guild)

        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                val embed = EmbedBuilder()
                    .setTitle("🎵 노래를 대기열에 추가했습니다")
                    .setDescription("`[${track.info.title}](${track.info.uri})`")
                    .setColor(Color.GREEN)
                channel.sendMessageEmbeds(embed.build()).queue()

                musicManager.scheduler.queue(track)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                val tracks = playlist.tracks
                val embed = EmbedBuilder()
                    .setTitle("🎶 재생 목록을 대기열에 추가했습니다")
                    .setDescription("`[${playlist.name}]` (${tracks.size}곡)")
                    .setColor(Color.GREEN)
                channel.sendMessageEmbeds(embed.build()).queue()

                for (track in tracks) {
                    musicManager.scheduler.queue(track)
                }
            }

            override fun noMatches() {
                channel.sendMessage("❌ 해당 노래를 찾을 수 없습니다.").queue()
            }

            override fun loadFailed(exception: FriendlyException) {
                channel.sendMessage("❗ 노래를 재생할 수 없습니다: ${exception.message}").queue()
            }
        })
    }
}
