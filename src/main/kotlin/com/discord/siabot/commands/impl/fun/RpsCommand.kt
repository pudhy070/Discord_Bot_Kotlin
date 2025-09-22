package com.discord.siabot.commands.impl.`fun`

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class RpsCommand : ICommand {
    override val name = "rps"
    override val description = "봇과 가위바위보를 합니다."
    override val aliases = listOf("가위바위보")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("사용법: `!rps <가위|바위|보>`").queue()
            return
        }

        val userChoice = when (args[0].lowercase()) {
            "가위", "scissor" -> "가위"
            "바위", "rock" -> "바위"
            "보", "paper" -> "보"
            else -> {
                event.channel.sendMessage("❌ `가위`, `바위`, `보` 중에서 하나를 내주세요.").queue()
                return
            }
        }

        val botChoice = listOf("가위", "바위", "보").random()

        val result = when {
            userChoice == botChoice -> "비겼습니다!"
            (userChoice == "가위" && botChoice == "보") ||
                    (userChoice == "바위" && botChoice == "가위") ||
                    (userChoice == "보" && botChoice == "바위") -> "당신이 이겼습니다! 🎉"
            else -> "제가 이겼네요! 😢"
        }

        val color = when {
            result.contains("이겼습니다") -> Color.GREEN
            result.contains("졌습니다") -> Color.RED
            else -> Color.YELLOW
        }

        val embed = EmbedBuilder()
            .setColor(color)
            .setTitle("✂️ 가위바위보 🗿")
            .addField("당신의 선택", userChoice, true)
            .addField("봇의 선택", botChoice, true)
            .setDescription("**$result**")
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
