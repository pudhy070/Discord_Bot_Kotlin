package com.discord.siabot.commands.impl.economy

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.EconomyService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class ShopCommand : ICommand {
    override val name = "shop"
    override val description = "상점을 보여줍니다."
    override val aliases = listOf("상점")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val shopItems = EconomyService.getShopItems()
        val embed = EmbedBuilder()
            .setColor(Color.ORANGE)
            .setTitle("🛒 상점")
            .setDescription("`!buy <아이템 번호>`로 구매하세요!")

        shopItems.forEach { (id, item) ->
            embed.addField("${id}. ${item.name}", "가격: **${item.price}** 🪙\n*${item.description}*", false)
        }

        event.channel.sendMessageEmbeds(embed.build()).queue()
    }
}
