package com.discord.siabot.listeners

import com.discord.siabot.config.Config
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ReadyListener : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        println("✅ Bot is ready! Logged in as ${event.jda.selfUser.name}")
        println("✅ Guilds: ${event.jda.guilds.size}")
        event.jda.presence.activity = Activity.playing("${Config.PREFIX}help | Serving servers")
    }
}
