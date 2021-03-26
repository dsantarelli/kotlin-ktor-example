import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import config.DbConfig
import config.setup
import io.ktor.application.*
import io.ktor.server.testing.*

import org.koin.core.context.stopKoin
import java.util.*

private fun Application.testAppSetup() {
    setup(
        DbConfig.setup(
            jdbcUrl = "jdbc:h2:mem:db-${UUID.randomUUID()};DB_CLOSE_DELAY=-1;",
            username = "",
            password = "",
            driverClassName = "org.h2.Driver"
        )
    )
}

abstract class AppTestBase {

    protected val serializer = jacksonObjectMapper().setup()

    protected fun withTestApp(test: TestApplicationEngine.() -> Unit) = withTestApplication(Application::testAppSetup) {
        test()
        stopKoin()
    }
}
