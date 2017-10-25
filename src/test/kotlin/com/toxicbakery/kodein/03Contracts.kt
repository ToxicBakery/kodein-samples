package com.toxicbakery.kodein

import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import org.junit.Assert.assertEquals
import org.junit.Test

class Contracts {

    interface Manager {
        val name: String
    }

    class UserManager : Manager {
        override val name = "UserManager"
    }


    class DocumentManager : Manager {
        override val name = "DocumentManager"
    }

    /**
     * Another intentional fail to demonstrate kodein debugging. Stacktrace points to the 2nd binding of Manager.
     */
    @Test
    fun one() {
        val kodein = ConfigurableKodein()
        kodein.addConfig {
            bind<Manager>() with provider { UserManager() }
            bind<Manager>() with provider { DocumentManager() }
        }

        val manager: Manager = kodein.instance()
    }

    @Test
    fun two() {
        val kodein = ConfigurableKodein()
        kodein.addConfig {
            bind<Manager>("userManager") with provider { UserManager() }
            bind<Manager>("documentManager") with provider { DocumentManager() }
        }

        assertEquals("UserManager", kodein.instance<Manager>("userManager").name)
        assertEquals("DocumentManager", kodein.instance<Manager>("documentManager").name)
    }

}