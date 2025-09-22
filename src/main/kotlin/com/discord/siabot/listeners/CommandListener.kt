package com.discord.siabot.listeners

import com.discord.siabot.commands.CommandManager
import com.discord.siabot.config.Config
import com.discord.siabot.services.ExperienceService
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot || !event.isFromGuild) return

        val leveledUp = ExperienceService.addExperience(event.author.id)
        if (leveledUp) {
            val newLevel = ExperienceService.getLevel(event.author.id)
            event.channel.sendMessage("🎉 ${event.author.asMention}, congratulations on reaching **Level ${newLevel}**!").queue()
        }

        // --- Command Handling ---
        val message = event.message.contentRaw
        if (message.startsWith(Config.PREFIX)) {
            CommandManager.handle(event, Config.PREFIX)
        }
    }
}
