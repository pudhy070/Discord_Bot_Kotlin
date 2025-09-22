package com.discord.siabot.commands.impl.music

import com.discord.siabot.commands.ICommand
import com.discord.siabot.music.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class VolumeCommand : ICommand {
    override val name = "volume"
    override val description = "볼륨을 조절합니다."
    override val aliases = listOf("vol", "볼륨")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("❓ 사용법: `${name} [0-150]`").queue()
            return
        }

        val volume = args[0].toIntOrNull()
        if (volume == null || volume !in 0..150) {
            event.channel.sendMessage("❌ 볼륨은 0에서 150 사이의 숫자여야 합니다.").queue()
            return
        }

        val musicManager = PlayerManager.getMusicManager(event.guild)
        musicManager.player.volume = volume

        event.channel.sendMessage("✅ 볼륨을 `${volume}`(으)로 설정했습니다.").queue()
    }
}

