package com.discord.siabot.commands.impl.economy

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.EconomyService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class GiveCommand : ICommand {
    override val name = "give"
    override val description = "다른 사용자에게 코인을 보냅니다."
    override val aliases = listOf("송금", "주기")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val mentions = event.message.mentions.members
        if (mentions.isEmpty() || args.size < 2) {
            event.channel.sendMessage("사용법: `!give @사용자 <금액>`").queue()
            return
        }

        val recipient = mentions.first()!!
        val amount = args[1].toIntOrNull()

        if (amount == null || amount <= 0) {
            event.channel.sendMessage("❌ 올바른 금액을 입력해주세요.").queue()
            return
        }

        if (recipient.user.isBot || recipient.id == event.author.id) {
            event.channel.sendMessage("❌ 자기 자신이나 봇에게는 코인을 보낼 수 없습니다.").queue()
            return
        }

        val success = EconomyService.transferCoins(event.author.id, recipient.id, amount)

        if (success) {
            val senderBalance = EconomyService.getBalance(event.author.id)
            val recipientBalance = EconomyService.getBalance(recipient.id)

            val embed = EmbedBuilder()
                .setColor(Color.CYAN)
                .setTitle("💸 송금 완료!")
                .setDescription("${event.author.asMention}님이 ${recipient.asMention}님에게 **$amount** 🪙를 보냈습니다.")
                .addField("보낸 후 내 잔액", "$senderBalance 🪙", true)
                .addField("받은 후 상대방 잔액", "$recipientBalance 🪙", true)
                .build()
            event.channel.sendMessageEmbeds(embed).queue()
        } else {
            val currentBalance = EconomyService.getBalance(event.author.id)
            event.channel.sendMessage("❌ 코인이 부족합니다! 현재 잔액: **$currentBalance** 🪙").queue()
        }
    }
}
