package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class RemindCommand : ICommand {
    override val name = "remind"
    override val description = "지정한 시간 후에 알림을 보냅니다."
    override val aliases = listOf("알림")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.size < 2) {
            event.channel.sendMessage("사용법: `!remind <시간(분)> <메시지>`").queue()
            return
        }

        val minutes = args[0].toLongOrNull()
        if (minutes == null || minutes <= 0) {
            event.channel.sendMessage("❌ 유효한 시간을 분 단위로 입력해주세요.").queue()
            return
        }

        val message = args.drop(1).joinToString(" ")
        val author = event.author

        event.channel.sendMessage("⏰ **${minutes}분** 후에 \"${message}\"(으)로 알려드릴게요!").queue()

        CoroutineScope(Dispatchers.Default).launch {
            delay(minutes * 60 * 1000)
            val reminderEmbed = EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTitle("⏰ 알림!")
                .setDescription("${author.asMention}, 약속된 알림입니다:\n> $message")
                .build()
            event.channel.sendMessageEmbeds(reminderEmbed).queue()
        }
    }
}
