package com.discord.siabot.commands.impl.music

import com.discord.siabot.commands.ICommand
import com.discord.siabot.music.PlayerManager
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class QueueCommand : ICommand {
    override val name = "queue"
    override val description = "재생 대기열을 봅니다."
    override val aliases = listOf("q", "대기열")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        // 'PlayerManager.getInstance()'가 아닌 올바른 방식으로 수정했습니다.
        val musicManager = PlayerManager.getMusicManager(event.guild)
        val queue = musicManager.scheduler.queue

        if (queue.isEmpty()) {
            event.channel.sendMessage("❌ 대기열이 비어있습니다.").queue()
            return
        }

        val embed = EmbedBuilder()
            .setColor(Color.BLUE)
            .setTitle("🎶 재생 대기열 (${queue.size}곡)")

        // 대기열 목록을 10개까지만 표시합니다.
        val queueList = queue.take(10).mapIndexed { index, track ->
            "`${index + 1}.` [${track.info.title}](${track.info.uri})"
        }.joinToString("\n")

        embed.setDescription(queueList)

        if (queue.size > 10) {
            embed.setFooter("... 외 ${queue.size - 10}곡 더")
        }

        event.channel.sendMessageEmbeds(embed.build()).queue()
    }
}

