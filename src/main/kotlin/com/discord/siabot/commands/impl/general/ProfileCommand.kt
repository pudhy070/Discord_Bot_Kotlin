package com.discord.siabot.commands.impl.general

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.EconomyService
import com.discord.siabot.services.ExperienceService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.time.Instant

class ProfileCommand : ICommand {
    override val name = "profile"
    override val description = "사용자의 프로필을 보여줍니다."
    override val aliases = listOf("프로필")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val targetUser = event.message.mentions.users.firstOrNull() ?: event.author

        val level = ExperienceService.getLevel(targetUser.id)
        val exp = ExperienceService.getExp(targetUser.id)
        val requiredExp = ExperienceService.getRequiredExp(level)
        val balance = EconomyService.getBalance(targetUser.id)

        val embed = EmbedBuilder()
            .setColor(Color.MAGENTA)
            .setTitle("👤 ${targetUser.name}님의 프로필")
            .setThumbnail(targetUser.effectiveAvatarUrl)
            .addField("레벨", "`$level`", true)
            .addField("경험치", "`$exp / $requiredExp`", true)
            .addField("코인", "`$balance` 💰", true)
            .setTimestamp(Instant.now())
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
