package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class TranslateCommand : ICommand {
    override val name = "translate"
    override val description = "텍스트를 번역합니다."
    override val aliases = listOf("번역")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.size < 3) {
            event.channel.sendMessage("사용법: `!translate <원본언어> <대상언어> <텍스트>`\n예시: `!translate ko en 안녕하세요`").queue()
            return
        }

        val sourceLang = args[0]
        val targetLang = args[1]
        val text = args.drop(2).joinToString(" ")

        val translatedText = "[$text] 를 [$sourceLang] 에서 [$targetLang] (으)로 번역한 결과입니다."
        // -----------------------------------------

        val embed = EmbedBuilder()
            .setColor(Color.BLUE)
            .setTitle("🌐 번역 결과")
            .addField("원본 ($sourceLang)", "```$text```", false)
            .addField("결과 ($targetLang)", "```$translatedText```", false)
            .setFooter("번역 기능은 실제 API 연동이 필요합니다.")
            .build()
        event.channel.sendMessageEmbeds(embed).queue()
    }
}
