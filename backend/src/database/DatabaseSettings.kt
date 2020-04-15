package ro.runtimeterror.cms.database

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import org.jetbrains.exposed.sql.Database

object DatabaseSettings
{
    val config = ConfigurationProperties.fromResource("database.properties")
    val url = Key("database.url", stringType)

    //val driver = Key("database.driver", stringType)
    val username = Key("database.username", stringType)
    val password = Key("database.password", stringType)

    val connection by lazy {
        Database.connect(url = config[url], user = config[username], password = config[password])
    }
}