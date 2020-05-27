package ro.runtimeterror.cms.networking

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.util.pipeline.PipelineContext
import ro.runtimeterror.cms.exceptions.ProgramException
import java.io.File

const val directory: String = "files"

suspend fun PipelineContext<Unit, ApplicationCall>.uploadFile(userId: Int): String
{
    val multipart = call.receiveMultipart()
    var savedFile: String? = null
    multipart.forEachPart { part ->
        if (part is PartData.FileItem)
        {
            val originalFileName = part.originalFileName ?: throw ProgramException("File not uploaded!")
            val ext = File(originalFileName).extension
            val filename = "upload-${userId.hashCode()}-${System.currentTimeMillis()}.$ext"
            part.streamProvider().use { input ->
                File("$directory/$filename").outputStream().buffered().use { output ->
                    input.copyTo(output)
                }
            }
            savedFile = filename
        }
        part.dispose()
    }
    return savedFile ?: throw ProgramException("File not uploaded!")
}