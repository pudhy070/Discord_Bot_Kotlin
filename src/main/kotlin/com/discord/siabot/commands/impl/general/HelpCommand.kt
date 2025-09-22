package com.discord.siabot.commands.impl.general


import com.discord.siabot.commands.CommandManager
import com.discord.siabot.commands.ICommand
import com.discord.siabot.config.Config
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color
import java.time.Instant

class HelpCommand(private val manager: CommandManager) : ICommand {
    override val name = "help"
    override val description = "봇의 모든 명령어를 보여줍니다."
    override val aliases = listOf("도움말", "명령어")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        val embed = EmbedBuilder()
            .setTitle("🤖 SiaBot 도움말")
            .setDescription("사용 가능한 모든 명령어 목록입니다. `${Config.PREFIX}명령어` 형식으로 사용하세요.")
            .setColor(Color.CYAN)
            .setTimestamp(Instant.now())
            .setFooter("SiaBot v2.0 | Developed by Sia")

        val commandsByCategory = manager.getCommands().groupBy {
            val packageName = it.javaClass.`package`.name
            when {
                packageName.contains("general") -> "📊 일반"
                packageName.contains("economy") -> "💰 경제"
                packageName.contains("fun") -> "🎉 재미"
                packageName.contains("moderation") -> "🛡️ 관리"
                packageName.contains("utility") -> "🛠️ 유틸리티"
                packageName.contains("music") -> "🎵 음악(불안정)"
                else -> "기타"
            }
        }

        commandsByCategory.toSortedMap().forEach { (category, commands) ->
            val commandList = commands.joinToString("\n") { "`${it.name}`" }
            embed.addField(category, commandList, true)
        }

        event.channel.sendMessageEmbeds(embed.build()).queue()
    }
}
