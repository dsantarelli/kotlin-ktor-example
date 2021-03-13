package config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object DbConfig {
    fun setup(jdbcUrl: String, username: String, password: String) =
        Database.connect(HikariDataSource(HikariConfig().also {
            it.jdbcUrl = jdbcUrl
            it.username = username
            it.password = password
        }))
}