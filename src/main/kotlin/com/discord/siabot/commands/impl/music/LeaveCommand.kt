package com.discord.siabot.commands.impl.music

import com.discord.siabot.commands.ICommand
import com.discord.siabot.music.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class LeaveCommand : ICommand {
    override val name = "leave"
    override val description = "음성 채널에서 나갑니다."
    override val aliases = listOf("나가기")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (event.guild.selfMember.voiceState?.inAudioChannel() != true) {
            event.channel.sendMessage("❌ 저는 이미 음성 채널에 없습니다.").queue()
            return
        }

        val musicManager = PlayerManager.getMusicManager(event.guild)
        musicManager.scheduler.queue.clear()
        musicManager.player.stopTrack()

        event.guild.audioManager.closeAudioConnection()
        event.channel.sendMessage("✅ 음성 채널에서 나갔습니다.").queue()
    }
}

