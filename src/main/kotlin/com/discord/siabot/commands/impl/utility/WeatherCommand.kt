package com.discord.siabot.commands.impl.utility

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import kotlin.random.Random

class WeatherCommand : ICommand {
    override val name = "weather"
    override val description = "날씨 정보를 보여줍니다."
    override val aliases = listOf("날씨")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("사용법: `!weather <도시 이름>`").queue()
            return
        }
        val city = args.joinToString(" ")

        val temp = Random.nextInt(-10, 35)
        val humidity = Random.nextInt(30, 90)
        val weatherCondition = listOf("맑음 ☀️", "구름 많음 ☁️", "비 🌧️", "눈 ❄️", "천둥번개 ⛈️").random()

        val embed = EmbedBuilder()
            .setColor(Color.CYAN)
            .setTitle("🌤️ $city 날씨 정보")
            .addField("상태", weatherCondition, true)
            .addField("온도", "${temp}°C", true)
            .addField("습도", "${humidity}%", true)
            .setFooter("날씨 정보는 시뮬레이션 데이터입니다.")
            .build()

        event.channel.sendMessageEmbeds(embed).queue()
    }
}
