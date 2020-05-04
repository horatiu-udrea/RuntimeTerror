package ro.runtimeterror.cms.networking

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import ro.runtimeterror.cms.exceptions.ProgramException
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.OutputStream

const val directory: String = "/files"

suspend fun PipelineContext<Unit, ApplicationCall>.uploadFile(): String
{
    val multipart = call.receiveMultipart()
    val userSession: UserSession =
        call.sessions.get<UserSession>() ?: throw UnauthorizedException("User not logged in!")
    var savedFile: File? = null
    multipart.forEachPart { part ->
        when (part)
        {
            is PartData.FileItem ->
            {
                val ext = File(part.originalFileName!!).extension
                val file = File(directory, "upload-${System.currentTimeMillis()}-${userSession.id.hashCode()}.$ext")
                part.streamProvider()
                    .use { input -> file.outputStream().buffered().use { output -> input.copyToSuspend(output) } }
                savedFile = file
            }
        }

        part.dispose()
    }
    val file = savedFile ?: throw FileNotFoundException()
    return file.path
}


suspend fun InputStream.copyToSuspend(
    out: OutputStream,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    yieldSize: Int = 4 * 1024 * 1024,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): Long
{
    return withContext(dispatcher) {
        val buffer = ByteArray(bufferSize)
        var bytesCopied = 0L
        var bytesAfterYield = 0L
        while (true)
        {
            val bytes = read(buffer).takeIf { it >= 0 } ?: break
            out.write(buffer, 0, bytes)
            if (bytesAfterYield >= yieldSize)
            {
                yield()
                bytesAfterYield %= yieldSize
            }
            bytesCopied += bytes
            bytesAfterYield += bytes
        }
        return@withContext bytesCopied
    }
}