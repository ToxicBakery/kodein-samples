package com.toxicbakery.kodein

import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import org.junit.Assert
import org.junit.Test

class Basics {

    /**
     * Sample provider injection.
     */
    @Test
    fun one() {
        val kodein = ConfigurableKodein()
        kodein.addConfig {
            bind<String>() with provider { "one" }
        }

        val injectedString: String = kodein.instance()
        Assert.assertEquals("one", injectedString)
    }

    /**
     * Provider injection but now we are using tagging.
     * Tagging is useful when you need to bind multiple of the same type
     */
    @Test
    fun two() {
        val kodein = ConfigurableKodein()
        kodein.addConfig {
            bind<String>("tag") with provider { "two" }
        }

        val injectedString: String = kodein.instance("tag")
        Assert.assertEquals("two", injectedString)
    }

    /**
     * This test intentionally fails to demonstrate how kodein aids in debugging binding issues.
     */
    @Test
    fun three() {
        val kodein = ConfigurableKodein()
        kodein.addConfig {
            bind<String>("tag") with provider { "some value" }
            bind<String>() with provider { "another value" }
        }

        val injectedString: String = kodein.instance("incorrectTag")
        Assert.assertEquals("", injectedString)
    }

}