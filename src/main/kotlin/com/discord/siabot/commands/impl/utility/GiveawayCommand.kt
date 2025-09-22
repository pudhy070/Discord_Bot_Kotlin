package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import com.discord.siabot.services.GiveawayService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.time.Instant

class GiveawayCommand : ICommand {
    override val name = "giveaway"
    override val description = "추첨 이벤트를 시작합니다."
    override val aliases = listOf("추첨")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (event.member?.hasPermission(Permission.MANAGE_SERVER) != true) {
            event.channel.sendMessage("❌ 이 명령어를 사용할 권한이 없습니다. (서버 관리 권한 필요)").queue()
            return
        }

        if (args.size < 2) {
            event.channel.sendMessage("사용법: `!giveaway <시간(분)> <상품>`").queue()
            return
        }

        val durationMinutes = args[0].toLongOrNull()
        if (durationMinutes == null || durationMinutes <= 0) {
            event.channel.sendMessage("❌ 유효한 시간을 분 단위로 입력해주세요.").queue()
            return
        }

        val prize = args.drop(1).joinToString(" ")
        val endTime = System.currentTimeMillis() + (durationMinutes * 60 * 1000)

        val embed = EmbedBuilder()
            .setColor(Color.MAGENTA)
            .setTitle("🎉 추첨 이벤트!")
            .setDescription("반응을 눌러 참여하세요!")
            .addField("상품", prize, false)
            .addField("종료 시간", "<t:${endTime / 1000}:R>", false)
            .setFooter("주최자: ${event.author.name}")
            .setTimestamp(Instant.now())
            .build()

        event.channel.sendMessageEmbeds(embed).queue { message ->
            message.addReaction(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode(GiveawayService.REACTION_EMOJI)).queue()
            GiveawayService.startGiveaway(event.guild, event.channel.id, message.id, prize, endTime)
        }
    }
}
