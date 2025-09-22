package com.discord.siabot.commands.impl.moderation

import com.discord.siabot.commands.ICommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class SlowmodeCommand : ICommand {
    override val name = "slowmode"
    override val description = "채널에 슬로우 모드를 설정합니다."
    override val aliases = listOf("슬로우")

    override fun execute(event: MessageReceivedEvent, args: List<String>) {
        if (event.member?.hasPermission(Permission.MANAGE_CHANNEL) != true) {
            event.channel.sendMessage("❌ 이 명령어를 사용할 권한이 없습니다. (채널 관리 권한 필요)").queue()
            return
        }

        val seconds = args.firstOrNull()?.toIntOrNull()
        if (seconds == null) {
            event.channel.sendMessage("사용법: `!slowmode <초>` (끄려면 0)").queue()
            return
        }

        if (seconds !in 0..21600) {
            event.channel.sendMessage("❌ 슬로우 모드는 0초에서 21600초(6시간) 사이로 설정해야 합니다.").queue()
            return
        }

        event.channel.asTextChannel().manager.setSlowmode(seconds).queue {
            if (seconds > 0) {
                event.channel.sendMessage("✅ 이 채널의 슬로우 모드를 **${seconds}초**로 설정했습니다.").queue()
            } else {
                event.channel.sendMessage("✅ 이 채널의 슬로우 모드를 해제했습니다.").queue()
            }
        }
    }
}

