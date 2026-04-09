package io.scotti.hackernews

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

private const val STORIES_URL = "https://hacker-news.firebaseio.com/v0/topstories.json"
private const val ITEM_URL_BASE = "https://hacker-news.firebaseio.com/v0/item"

class HackerNewsClient : AutoCloseable {

    private val client = HttpClient(Java) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private suspend fun fetchItemIds(): List<Int> = client.get(STORIES_URL).body()

    private suspend fun fetchStory(index: Int, storyId: Int): Story =
        client
            .get("$ITEM_URL_BASE/$storyId.json")
            .body<Story>()
            .copy(index = index)

    suspend fun fetchTopStories(number: Int): List<Story> = coroutineScope {
        val ids = fetchItemIds()
        val count = minOf(ids.size, number)
        val stories = arrayOfNulls<Story>(count)

        ids.take(count).mapIndexed { index, id ->
            async {
                stories[index] = fetchStory(index, id)
            }
        }.awaitAll()

        stories.mapNotNull { it }
    }

    override fun close() {
        client.close()
    }
}
