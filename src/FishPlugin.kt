package fish

import arc.util.*
import arc.util.serialization.*
import mindustry.*
import mindustry.gen.*
import mindustry.mod.*
import mindustry.net.*
import mindustry.net.Administration.*
import java.io.*

@Suppress("unused")
class FishPlugin : Plugin() {
    private val version by lazy { Vars.mods.getMod(this::class.java).meta.version }

    /** Called after command creation */
    override fun init() {
        Log.info("Initialized Fish Plugin v$version")
    }

    /** Console commands */
    override fun registerServerCommands(handler: CommandHandler) {

    }

    /** In game commands */
    override fun registerClientCommands(handler: CommandHandler) {
        handler.register<Player>("alts", "<target>", "View a player's alts") { args, player ->
            if (!player.admin) {
                player.sendMessage("[scarlet]No.")
                return@register
            }

            val target = Groups.player.getByID(Strings.parseInt(args[0])) ?: Groups.player.minBy { Strings.levenshtein(Strings.stripColors(it.name), args[0]) }
            player.sendMessage(buildString {
                append("[accent]Player ${Strings.stripColors(target.name)} has played on these IPs: ")
                target.info.ips.joinTo(this)
                append("\nWith these names: ")
                target.info.names.joinTo(this, transform = Strings::stripColors)
            })
        }
    }
}