package ro.runtimeterror.cms

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import ro.runtimeterror.cms.networking.module

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
}
