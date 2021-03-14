package dao.tables

import org.jetbrains.exposed.sql.Table

object ProductsTable : Table() {
    val id = varchar("id", 128).primaryKey()
    val title = varchar("title", 255)
    val description = varchar("description", 255)
    val price = double("price")
    val creationDateTime = long("creationDateTime")
    val lastUpdateDateTime = long("lastUpdateDateTime")
}