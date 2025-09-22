package com.discord.siabot.commands.impl.music

import com.discord.siabot.commands.ICommand
import com.discord.siabot.music.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class ClearQueueCommand : ICommand {
    override val name = "clearqueue"
    override val description = "음악 대기열을 비웁니다."
    override val aliases = listOf("cq")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (event.member?.voiceState?.inAudioChannel() != true) {
            event.channel.sendMessage("❌ 음성 채널에 먼저 참가해주세요.").queue()
            return
        }
        val musicManager = PlayerManager.getMusicManager(event.guild)
        musicManager.scheduler.queue.clear()
        event.channel.sendMessage("✅ 대기열을 모두 비웠습니다.").queue()
    }
}
