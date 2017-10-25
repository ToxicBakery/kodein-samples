package com.toxicbakery.kodein

import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import org.junit.Assert.assertNotNull
import org.junit.Test

class TransitiveDependencies {

    class Database

    class Manager(val database: Database)

    /**
     * Simple chaining of dependencies
     */
    @Test
    fun one() {
        val kodein = ConfigurableKodein()
        kodein.addConfig {
            bind<Database>() with singleton { Database() }
            bind<Manager>() with provider { Manager(instance()) }
        }

        val injectedManager: Manager = kodein.instance()
        assertNotNull(injectedManager.database)
    }

}