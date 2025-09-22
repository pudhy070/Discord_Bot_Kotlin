package com.discord.siabot.commands.impl.`fun`

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import kotlin.random.Random

class EightBallCommand : ICommand {
    override val name = "8ball"
    override val description = "마법의 8번 공에게 질문합니다."
    override val aliases = listOf("8볼", "에잇볼")

    private val responses = listOf(
        "확실해요.", "바로 그거에요.", "의심의 여지가 없어요.", "네, 당연하죠.",
        "제 생각엔 그래요.", "아마도요.", "전망이 좋아 보여요.", "네.",
        "알쏭달쏭하네요, 다시 물어보세요.", "나중에 다시 물어봐주세요.", "지금 말해줄 수 없어요.",
        "예측할 수 없어요.", "집중하고 다시 물어보세요.", "그다지 좋지 않아요.",
        "제 대답은 아니요.", "정보에 따르면 아니래요.", "전망이 별로 좋지 않아요.", "매우 의심스럽네요."
    )

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("사용법: `!8ball <질문 내용>`").queue()
            return
        }

        val question = args.joinToString(" ")
        val answer = responses.random()

        val embed = EmbedBuilder()
            .setColor(Color.BLACK)
            .setTitle("🎱 마법의 8번 공")
            .addField("질문", question, false)
            .addField("대답", answer, false)
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
