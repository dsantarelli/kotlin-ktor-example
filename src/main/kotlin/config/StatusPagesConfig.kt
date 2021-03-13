package config

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun StatusPages.Configuration.setup() {
    exception<MissingKotlinParameterException> {
        call.respond(HttpStatusCode.BadRequest)
    }
}