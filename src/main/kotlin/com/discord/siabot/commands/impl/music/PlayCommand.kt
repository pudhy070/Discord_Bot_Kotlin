package com.discord.siabot.commands.impl.music

import com.discord.siabot.commands.ICommand
import com.discord.siabot.music.PlayerManager
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.net.URI
import java.net.URISyntaxException

class PlayCommand : ICommand {
    override val name = "play"
    override val description = "음성 채널에서 노래를 재생합니다."
    override val aliases = listOf("p", "재생")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("❌ 재생할 노래의 제목이나 URL을 입력해주세요.").queue()
            return
        }

        val member = event.member ?: return
        val voiceState = member.voiceState ?: return

        if (!voiceState.inAudioChannel()) {
            event.channel.sendMessage("❌ 먼저 음성 채널에 참여해야 합니다.").queue()
            return
        }

        val selfMember = event.guild.selfMember
        val voiceChannel = voiceState.channel
        if (voiceChannel != null && !selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            event.channel.sendMessage("❌ '${voiceChannel.name}' 채널에 연결할 권한이 없습니다.").queue()
            return
        }

        val audioManager = event.guild.audioManager
        if (!audioManager.isConnected) {
            audioManager.openAudioConnection(voiceChannel)
        }

        var search = args.joinToString(" ")
        if (!isUrl(search)) {
            search = "ytmsearch:$search"
        }

        PlayerManager.loadAndPlay(event.channel, search)
    }

    private fun isUrl(input: String): Boolean {
        return try {
            URI(input)
            true
        } catch (e: URISyntaxException) {
            false
        }
    }
}

