package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.PollService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.entities.emoji.Emoji
import java.awt.Color

class PollCommand : ICommand {
    override val name = "poll"
    override val description = "투표를 시작합니다."
    override val aliases = listOf("투표")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val fullArgs = event.message.contentRaw.substringAfter(name).trim()
        val parts = fullArgs.split("\"").map { it.trim() }.filter { it.isNotEmpty() }

        if (parts.size < 3) {
            event.channel.sendMessage("사용법: `!poll \"질문\" \"선택지 1\" \"선택지 2\" ...` (선택지는 최대 10개)").queue()
            return
        }

        val question = parts[0]
        val options = parts.drop(1)

        if (options.size > 10) {
            event.channel.sendMessage("❌ 선택지는 최대 10개까지 설정할 수 있습니다.").queue()
            return
        }

        val poll = PollService.createPoll(event.channel.id, question, options)

        val embed = EmbedBuilder()
            .setColor(Color.ORANGE)
            .setTitle("📊 투표: $question")
            .setDescription("아래의 반응을 눌러 투표해주세요!")
            .setAuthor(event.author.name, null, event.author.effectiveAvatarUrl)

        val pollMessage = poll.options.mapIndexed { index, option ->
            "${PollService.numberEmojis[index]} **$option**"
        }.joinToString("\n\n")

        embed.addField("선택지", pollMessage, false)

        event.channel.sendMessageEmbeds(embed.build()).queue { message ->
            poll.messageId = message.idLong
            poll.options.indices.forEach { index ->
                message.addReaction(Emoji.fromUnicode(PollService.numberEmojis[index])).queue()
            }
        }
    }
}
