package com.discord.siabot.commands

import com.discord.siabot.commands.impl.economy.*
import com.discord.siabot.commands.impl.`fun`.*
import com.discord.siabot.commands.impl.general.*
import com.discord.siabot.commands.impl.moderation.*
import com.discord.siabot.commands.impl.music.*
import com.discord.siabot.commands.impl.utility.*
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object CommandManager {
    private val commands = mutableMapOf<String, ICommand>()

    init {
        // General Commands
        addCommand(HelpCommand(this))
        addCommand(PingCommand())
        addCommand(ServerInfoCommand())
        addCommand(UserInfoCommand())
        addCommand(AvatarCommand())
        addCommand(UptimeCommand())
        addCommand(VersionCommand())
        addCommand(ProfileCommand())

        // Utility Commands
        addCommand(PollCommand())
        addCommand(GiveawayCommand())
        addCommand(WeatherCommand())
        addCommand(TranslateCommand())
        addCommand(QrCommand())
        addCommand(CalculateCommand())
        addCommand(RemindCommand())
        addCommand(Base64Command())
        addCommand(HashCommand())
        addCommand(PasswordCommand())

        // Fun Commands
        addCommand(CoinflipCommand())
        addCommand(DiceCommand())
        addCommand(JokeCommand())
        addCommand(MemeCommand())
        addCommand(QuoteCommand())
        addCommand(RpsCommand())
        addCommand(AsciiCommand())

        // Economy Commands
        addCommand(BalanceCommand())
        addCommand(DailyCommand())
        addCommand(LeaderboardCommand())
        addCommand(GiveCommand())
        addCommand(ShopCommand())
        addCommand(BuyCommand())

        // Moderation Commands
        addCommand(ClearCommand())
        addCommand(KickCommand())
        addCommand(BanCommand())
        addCommand(MuteCommand())
        addCommand(SlowmodeCommand())
        addCommand(AnnounceCommand())
        addCommand(WarnCommand())

        // Music Commands
        addCommand(PlayCommand())
        addCommand(StopCommand())
        addCommand(SkipCommand())
        addCommand(QueueCommand())
        addCommand(NowPlayingCommand())
        addCommand(VolumeCommand())
        addCommand(LeaveCommand())
        addCommand(ClearQueueCommand())
    }

    private fun addCommand(command: ICommand) {
        if (!commands.containsKey(command.name)) {
            commands[command.name] = command
        }
        command.aliases.forEach { alias ->
            if (!commands.containsKey(alias)) {
                commands[alias] = command
            }
        }
    }

    fun getCommands(): List<ICommand> = commands.values.distinct()

    fun handle(event: MessageReceivedEvent, prefix: String) {
        val parts = event.message.contentRaw.removePrefix(prefix).split(Regex("\\s+"))
        val commandName = parts[0].lowercase()
        val args = parts.drop(1)

        val command = commands[commandName]
        command?.execute(event, args)
    }
}
