package config

import handlers.RequestHandlerFactory
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import org.slf4j.event.Level

fun Application.setup(db: Database) {
    startKoin { modules(config.setup(db)) }
    install(DefaultHeaders)
    install(Locations)
    install(CallLogging) { level = Level.INFO }
    install(ContentNegotiation) { jackson() }
    install(StatusPages) { setup() }
    install(Routing) { setup(inject(RequestHandlerFactory::class.java).value) }
}

fun setupServer() = embeddedServer(Netty, System.getenv("PORT")?.toInt() ?: 8080) {
    setup(DbConfig.setup("jdbc:h2:mem:applicationDB;DB_CLOSE_DELAY=-1;", "", ""))
}