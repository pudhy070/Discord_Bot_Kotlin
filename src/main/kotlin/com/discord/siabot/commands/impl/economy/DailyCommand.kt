package com.discord.siabot.commands.impl.economy

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.EconomyService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import kotlin.random.Random

class DailyCommand : ICommand {
    override val name = "daily"
    override val description = "일일 보상을 받습니다."
    override val aliases = listOf("출석")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (EconomyService.canClaimDaily(event.author.id)) {
            val reward = Random.nextInt(50, 201)
            val newBalance = EconomyService.claimDaily(event.author.id, reward)
            val embed = EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("🎁 일일 보상!")
                .setDescription("${event.author.asMention}님이 **$reward** 💰를 받았습니다!")
                .addField("현재 잔액", newBalance.toString(), false)
                .build()
            event.channel.sendMessageEmbeds(embed).queue()
        } else {
            val cooldown = EconomyService.getDailyCooldown(event.author.id)
            event.channel.sendMessage("⏰ 다음 보상은 <t:${cooldown / 1000}:R>에 받을 수 있습니다.").queue()
        }
    }
}
