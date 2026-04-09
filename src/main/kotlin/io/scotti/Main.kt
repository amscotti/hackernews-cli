package io.scotti

import kotlinx.coroutines.runBlocking
import io.scotti.hackernews.HackerNewsClient
import io.scotti.hackernews.Story
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.util.concurrent.Callable
import kotlin.system.exitProcess

private val ansi = CommandLine.Help.Ansi.AUTO

private fun style(spec: String, text: String): String = ansi.string("@|$spec $text|@")

private fun Story.printStyling(showSourceUrl: Boolean): String {
    val hnUrl = "https://news.ycombinator.com/item?id=$id"

    return buildString {
        appendLine(style("bold,fg(214)", title))
        appendLine(style("fg(248)", "score: $score\tcomments: $descendants\tuser: $by"))
        appendLine("url: ${style("underline,fg(45)", hnUrl)}")
        if (showSourceUrl && url.isNotEmpty()) {
            appendLine(style("fg(169)", url))
        }
        appendLine()
    }
}

@Command(
    name = "hackernews-cli",
    mixinStandardHelpOptions = true,
    description = ["Fetch and display top stories from Hacker News"]
)
class HackerNewsCliCommand : Callable<Int> {

    @Option(
        names = ["-n"],
        defaultValue = "30",
        description = ["Specify the number of top stories to display."]
    )
    var number: Int = 30
        set(value) {
            require(value > 0) { "number of stories must be a positive integer" }
            field = value
        }

    @Option(
        names = ["-u"],
        description = ["Include the source URLs of the stories in the output."]
    )
    var showSourceUrls: Boolean = false

    override fun call(): Int {
        return runBlocking {
            try {
                val stories = HackerNewsClient().use { client ->
                    client.fetchTopStories(number)
                }

                for (story in stories) {
                    print(story.printStyling(showSourceUrls))
                }
                0
            } catch (e: Exception) {
                System.err.println("Error fetching top stories: ${e.message}")
                1
            }
        }
    }
}

fun main(args: Array<String>) {
    val exitCode = CommandLine(HackerNewsCliCommand()).execute(*args)
    if (exitCode != 0) {
        exitProcess(exitCode)
    }
}
