package com.discord.siabot.config

import java.util.Properties

object Config {
    private val properties = Properties()

    init {
        val loader = Thread.currentThread().contextClassLoader
        loader.getResourceAsStream("config.properties").use { resourceStream ->
            if (resourceStream == null) {
                throw IllegalStateException("config.properties not found in resources")
            }
            properties.load(resourceStream)
        }
    }

    val TOKEN: String = properties.getProperty("BOT_TOKEN") ?: throw IllegalStateException("Bot token is missing")
    val PREFIX: String = properties.getProperty("BOT_PREFIX") ?: "!"
    val LOG_CHANNEL_ID: String? = properties.getProperty("LOG_CHANNEL_ID")
}
