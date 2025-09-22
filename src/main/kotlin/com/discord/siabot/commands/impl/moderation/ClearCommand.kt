package com.discord.siabot.commands.impl.moderation

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.time.Instant
import java.util.concurrent.TimeUnit

class ClearCommand : ICommand {
    override val name = "clear"
    override val description = "메시지를 삭제합니다."
    override val aliases = listOf("청소", "purge")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (!event.member!!.hasPermission(Permission.MESSAGE_MANAGE)) {
            event.channel.sendMessage("❌ 이 명령어를 사용할 권한이 없습니다. (메시지 관리 권한 필요)").queue()
            return
        }

        val amount = args.firstOrNull()?.toIntOrNull()
        if (amount == null || amount < 1 || amount > 100) {
            event.channel.sendMessage("사용법: `!clear <1~100 사이의 숫자>`").queue()
            return
        }

        event.message.delete().queue()

        event.channel.history.retrievePast(amount).queue { messages ->
            event.channel.purgeMessages(messages)

            val embed = EmbedBuilder()
                .setColor(Color.GREEN)
                .setDescription("✅ **${messages.size}**개의 메시지를 삭제했습니다.")
                .setTimestamp(Instant.now())
                .build()

            event.channel.sendMessageEmbeds(embed).queue {
                it.delete().queueAfter(5, TimeUnit.SECONDS)
            }
        }
    }
}
