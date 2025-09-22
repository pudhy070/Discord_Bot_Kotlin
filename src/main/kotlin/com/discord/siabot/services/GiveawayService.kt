package com.discord.siabot.services

import com.discord.siabot.config.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import java.awt.Color
import java.time.Instant

object GiveawayService {
    const val REACTION_EMOJI = "🎉"

    fun startGiveaway(guild: Guild, channelId: String, messageId: String, prize: String, endTime: Long) {
        CoroutineScope(Dispatchers.Default).launch {
            val duration = endTime - System.currentTimeMillis()
            if (duration > 0) {
                delay(duration)
            }

            val channel = guild.getTextChannelById(channelId)
            if (channel == null) {
                println("Giveaway Error: Channel $channelId not found.")
                return@launch
            }

            channel.retrieveMessageById(messageId).queue { message ->
                val reaction = message.reactions.find { it.emoji.name == REACTION_EMOJI }
                val users = reaction?.retrieveUsers()?.complete()?.filter { !it.isBot } ?: emptyList()

                val winner = if (users.isNotEmpty()) users.random() else null

                val endEmbed = EmbedBuilder(message.embeds.first())
                    .setTitle("🎉 추첨 종료!")
                    .clearFields()
                    .addField("상품", prize, false)

                if (winner != null) {
                    endEmbed.setDescription("**당첨자:** ${winner.asMention}")
                    channel.sendMessage("축하합니다, ${winner.asMention}! **$prize**에 당첨되셨습니다!").queue()
                } else {
                    endEmbed.setDescription("참여자가 없어 당첨자가 없습니다.")
                }
                message.editMessageEmbeds(endEmbed.build()).queue()
                message.clearReactions().queue()
            }
        }
    }
}
