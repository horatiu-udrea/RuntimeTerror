package ro.runtimeterror.cms.api

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.cookiesSession
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test
import ro.runtimeterror.cms.networking.module
import kotlin.test.assertEquals

fun String.removeWhitespaces(): String
{
    return this.replace("\\s".toRegex(), "")
}

class ConferenceTest {
    @Test
    fun testConference() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/conference").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val body = """{
  "name": "Oracle Java One",
  "startDate": "01/09/2020",
  "endDate": "03/09/2020",
  "submissionDeadline": "01/08/2020",
  "proposalDeadline": "15/08/2020",
  "biddingDeadline": "20/08/2020",
  "submitPaperEarly": true,
  "currentPhase": 3
}"""
                assertEquals(body.removeWhitespaces(), response.content!!.removeWhitespaces())
            }


            cookiesSession {
                handleRequest(HttpMethod.Post, "/authentication/login") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    val body = """{
  "username": "Jamil99",
  "password": "Jamil99"
}"""
                    setBody(body.removeWhitespaces())
                }.apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                }

                handleRequest(HttpMethod.Post, "/conference") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    val body = """{
  "name": "Some other name",
  "startDate": "01/09/2020",
  "endDate": "03/09/2020",
  "submissionDeadline": "01/08/2020",
  "proposalDeadline": "15/08/2020",
  "biddingDeadline": "20/08/2020",
  "submitPaperEarly": true,
  "currentPhase": 3
}"""
                    setBody(body.removeWhitespaces())
                }.apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                }

                handleRequest(HttpMethod.Get, "/conference").apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                    val body = """{
  "name": "Some other name",
  "startDate": "01/09/2020",
  "endDate": "03/09/2020",
  "submissionDeadline": "01/08/2020",
  "proposalDeadline": "15/08/2020",
  "biddingDeadline": "20/08/2020",
  "submitPaperEarly": true,
  "currentPhase": 3
}"""
                    assertEquals(body.removeWhitespaces(), response.content!!.removeWhitespaces())
                }
            }
        }
    }
}
