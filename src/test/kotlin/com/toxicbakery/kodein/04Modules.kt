package com.toxicbakery.kodein

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import org.junit.Assert.assertNotNull
import org.junit.Test

class Modules {

    class Database

    class Api

    class UserManager(database: Database, api: Api)

    val userManagerModule = Kodein.Module {
        bind<Api>() with provider { Api() }
        bind<UserManager>() with provider { UserManager(instance(), instance()) }
    }

    /**
     * Demonstrate use of modules to separate dependencies into logical groups.
     * While a database may be used application wide, an API may only be relevant to a
     * specific manager.
     */
    @Test
    fun one() {
        val kodein = ConfigurableKodein()
        kodein.addConfig {
            bind<Database>() with singleton { Database() }
        }
        kodein.addImport(userManagerModule)

        assertNotNull(kodein.instance<UserManager>())
    }

}