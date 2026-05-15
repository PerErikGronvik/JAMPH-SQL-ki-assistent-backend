package no.jamph.ragumami.passthrough

import com.google.gson.Gson
import no.jamph.ragumami.core.llm.OllamaClient
import org.slf4j.LoggerFactory

data class PassthroughRequest(
    val prompt: String,
    val data: Any? = null
)

data class PassthroughResponse(
    val response: String,
    val model: String
)

class PassthroughService(
    private val ollamaBaseUrl: String,
    private val defaultModel: String,
    private val defaultClient: OllamaClient
) {
    private val logger = LoggerFactory.getLogger(PassthroughService::class.java)

    companion object {
        private val gson = Gson()
    }

    suspend fun generate(request: PassthroughRequest): PassthroughResponse {
        if (request.prompt.isBlank()) {
            throw IllegalArgumentException("Prompt kan ikke vaere tom")
        }

        val fullPrompt = if (request.data != null) {
            "${request.prompt}\n\nData:\n${gson.toJson(request.data)}"
        } else {
            request.prompt
        }

        logger.info("PASSTHROUGH: model={}, promptLength={}", defaultModel, fullPrompt.length)

        val response = defaultClient.generate(fullPrompt)

        logger.info("PASSTHROUGH: responseLength={}", response.length)
        return PassthroughResponse(response = response, model = defaultModel)
    }
}