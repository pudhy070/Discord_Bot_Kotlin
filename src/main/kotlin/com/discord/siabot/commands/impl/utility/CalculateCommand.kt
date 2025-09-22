package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import javax.script.ScriptEngineManager

class CalculateCommand : ICommand {
    override val name = "calc"
    override val description = "수식을 계산합니다."
    override val aliases = listOf("계산")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("사용법: `!calc <수식>` (예: `!calc 10 * (5+2)`)").queue()
            return
        }

        val expression = args.joinToString(" ")
        val engine = ScriptEngineManager().getEngineByName("nashorn")
        try {
            val result = engine.eval(expression)
            val embed = EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("🧮 계산 결과")
                .addField("수식", "```$expression```", false)
                .addField("결과", "```$result```", false)
                .build()
            event.channel.sendMessageEmbeds(embed).queue()
        } catch (e: Exception) {
            event.channel.sendMessage("❌ 잘못된 수식이거나 계산할 수 없습니다.").queue()
        }
    }
}
