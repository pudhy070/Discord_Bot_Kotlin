package com.discord.siabot.commands.impl.music

import com.discord.siabot.commands.ICommand
import com.discord.siabot.music.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class SkipCommand : ICommand {
    override val name = "skip"
    override val description = "현재 곡을 건너뜁니다."
    override val aliases = listOf("s", "스킵")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = PlayerManager.getMusicManager(event.guild)
        musicManager.scheduler.nextTrack()
        event.channel.sendMessage("✅ 현재 곡을 건너뛰었습니다.").queue()
    }
}

