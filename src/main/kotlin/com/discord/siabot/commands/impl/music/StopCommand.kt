package com.discord.siabot.commands.impl.music

import com.discord.siabot.commands.ICommand
import com.discord.siabot.music.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class StopCommand : ICommand {
    override val name = "stop"
    override val description = "음악 재생을 중지합니다."
    override val aliases = listOf("정지")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = PlayerManager.getMusicManager(event.guild)
        musicManager.scheduler.queue.clear()
        musicManager.player.stopTrack()
        event.channel.sendMessage("✅ 재생을 중지하고 대기열을 초기화했습니다.").queue()
    }
}

