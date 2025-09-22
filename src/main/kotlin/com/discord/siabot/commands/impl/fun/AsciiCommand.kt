package com.discord.siabot.commands.impl.`fun`

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class AsciiCommand : ICommand {
    override val name = "ascii"
    override val description = "텍스트로 ASCII 아트를 만듭니다."
    override val aliases = listOf("아스키")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("사용법: `!ascii <텍스트>`").queue()
            return
        }

        val text = args.joinToString(" ").take(15)
        val border = "═".repeat(text.length + 2)

        val asciiArt = """
        ```
        ╔$border╗
        ║ $text ║
        ╚$border╝
        ```
        """.trimIndent()

        event.channel.sendMessage(asciiArt).queue()
    }
}
