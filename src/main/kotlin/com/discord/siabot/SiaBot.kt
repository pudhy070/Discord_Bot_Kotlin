package com.discord.siabot

import com.discord.siabot.config.Config
import com.discord.siabot.listeners.AuditLogListener
import com.discord.siabot.listeners.CommandListener
import com.discord.siabot.listeners.ReadyListener
import com.discord.siabot.listeners.WelcomeListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag

fun main() {
    SiaBot().start()
}

class SiaBot {
    fun start() {
        JDABuilder.createDefault(Config.TOKEN)
            .enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS
            )
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .enableCache(CacheFlag.VOICE_STATE)
            .addEventListeners(
                ReadyListener(),
                WelcomeListener(),
                CommandListener(),
                AuditLogListener()
            )
            .build()
    }
}
