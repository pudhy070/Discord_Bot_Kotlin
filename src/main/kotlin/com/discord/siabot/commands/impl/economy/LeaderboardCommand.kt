package com.discord.siabot.commands.impl.economy

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.EconomyService
import com.discord.siabot.services.ExperienceService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class LeaderboardCommand : ICommand {
    override val name = "leaderboard"
    override val description = "레벨 또는 코인 순위를 보여줍니다."
    override val aliases = listOf("lb", "순위")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val type = args.firstOrNull() ?: "level"

        val embed = EmbedBuilder().setColor(Color.ORANGE)

        when (type.lowercase()) {
            "level", "레벨" -> {
                embed.setTitle("🏆 레벨 리더보드")
                val leaderboard = ExperienceService.getLevelLeaderboard(10)
                if (leaderboard.isEmpty()) {
                    embed.setDescription("아직 순위가 없습니다.")
                } else {
                    val description = leaderboard.mapIndexed { index, (userId, level) ->
                        val exp = ExperienceService.getExp(userId)
                        val requiredExp = ExperienceService.getRequiredExp(level)
                        "`${index + 1}.` <@$userId> - **레벨 $level** ($exp / $requiredExp EXP)"
                    }.joinToString("\n")
                    embed.setDescription(description)
                }
            }
            "money", "coin", "돈", "코인" -> {
                embed.setTitle("💰 코인 리더보드")
                val leaderboard = EconomyService.getCoinLeaderboard(10)
                if (leaderboard.isEmpty()) {
                    embed.setDescription("아직 순위가 없습니다.")
                } else {
                    val description = leaderboard.mapIndexed { index, (userId, balance) ->
                        "`${index + 1}.` <@$userId> - **$balance** 💰"
                    }.joinToString("\n")
                    embed.setDescription(description)
                }
            }
            else -> {
                event.channel.sendMessage("사용법: `!leaderboard [level | money]`").queue()
                return
            }
        }
        event.channel.sendMessageEmbeds(embed.build()).queue()
    }
}
