package com.discord.siabot.commands.impl.`fun`

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class QuoteCommand : ICommand {
    override val name = "quote"
    override val description = "명언을 보여줍니다."
    override val aliases = listOf("명언")

    private val quotes = listOf(
        "가장 큰 위험은 아무 위험도 감수하지 않으려는 것이다. - 마크 저커버그",
        "성공의 비결은 단 한 가지, 잘할 수 있는 일에 광적으로 집중하는 것이다. - 톰 모나건",
        "나는 내가 더 노력할수록 운이 더 좋아진다는 것을 발견했다. - 토마스 제퍼슨",
        "오늘 한 시간은 내일 두 시간보다 가치 있다. - 토마스 풀러",
        "미래를 예측하는 가장 좋은 방법은 미래를 창조하는 것이다. - 앨런 케이",
        "지금의 귀찮음을 이겨내고 진행을 하면 내일은 자신이 새로 태어난 날이다. - 시아"
    )

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val embed = EmbedBuilder()
            .setColor(Color.CYAN)
            .setTitle("💭 오늘의 명언")
            .setDescription("*${quotes.random()}*")
            .build()
        event.channel.sendMessageEmbeds(embed).queue()
    }
}
