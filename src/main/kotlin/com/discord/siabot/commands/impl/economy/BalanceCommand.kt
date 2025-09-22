package com.discord.siabot.commands.impl.economy

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.EconomyService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class BalanceCommand : ICommand {
    override val name = "balance"
    override val description = "코인 잔액을 확인합니다."
    override val aliases = listOf("잔액", "돈", "bal")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val targetUser: User = if (event.message.mentions.users.isNotEmpty()) {
            event.message.mentions.users.first()!!
        } else {
            event.author
        }

        val balance = EconomyService.getBalance(targetUser.id)

        val embed = EmbedBuilder()
            .setColor(Color.GREEN)
            .setTitle("💰 ${targetUser.name}님의 잔액")
            .setDescription("현재 보유하고 있는 코인은 **${balance}** 🪙 입니다.")
            .setThumbnail(targetUser.effectiveAvatarUrl)
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
