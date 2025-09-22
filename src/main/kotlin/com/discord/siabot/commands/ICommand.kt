package com.discord.siabot.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

interface ICommand {
    val name: String

    val description: String

    val aliases: List<String> get() = listOf()

    fun execute(event: MessageReceivedEvent, args: List<String>)
}
