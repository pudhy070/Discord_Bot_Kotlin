package com.discord.siabot.commands.impl.economy

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.EconomyService
import com.discord.siabot.services.PurchaseResult
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class BuyCommand : ICommand {
    override val name = "buy"
    override val description = "상점의 아이템을 구매합니다."
    override val aliases = listOf("구매")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val itemId = args.firstOrNull()?.toIntOrNull()
        if (itemId == null) {
            event.channel.sendMessage("사용법: `!buy <아이템 번호>`").queue()
            return
        }

        val result = EconomyService.purchaseItem(event.author.id, itemId)
        val item = EconomyService.getShopItems()[itemId]

        when (result) {
            PurchaseResult.SUCCESS -> {
                val embed = EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("✅ 구매 완료!")
                    .setDescription("${event.author.asMention}님이 **${item!!.name}**을(를) 구매했습니다!")
                    .addField("남은 잔액", EconomyService.getBalance(event.author.id).toString(), false)
                    .build()
                event.channel.sendMessageEmbeds(embed).queue()
            }
            PurchaseResult.NOT_ENOUGH_COINS -> {
                event.channel.sendMessage("❌ 코인이 부족합니다! (필요: ${item!!.price}, 보유: ${EconomyService.getBalance(event.author.id)})").queue()
            }
            PurchaseResult.ITEM_NOT_FOUND -> {
                event.channel.sendMessage("❌ 존재하지 않는 아이템 번호입니다.").queue()
            }
        }
    }
}
