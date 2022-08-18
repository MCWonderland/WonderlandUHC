package org.mcwonderland.uhc

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import com.google.common.io.Resources
import io.mockk.*
import org.bukkit.Bukkit
import org.junit.Before
import org.junit.Test
import org.mineacademy.fo.FileUtil
import org.mineacademy.fo.plugin.AutoRegisterScanner
import org.mineacademy.fo.plugin.SimplePlugin
import org.mineacademy.fo.remain.Remain

class TestingTest {
    private lateinit var server: ServerMock
    private lateinit var plugin: WonderlandUHC

    @Before
    fun setUp() {
        server = MockBukkit.mock()

        val bukkitPackage = Bukkit.getServer()::class.java.`package`
        mockkObject(bukkitPackage)
        mockkStatic(SimplePlugin::class)
        mockkStatic(FileUtil::class)
        mockkStatic(Remain::class)
        mockkStatic(AutoRegisterScanner::class)


        every { SimplePlugin.getInstance() } answers {
            val simplePlugin = server.pluginManager.getPlugin("WonderlandUHC") as SimplePlugin
            val field = SimplePlugin::class.java.getDeclaredField("instance")
            field.isAccessible = true
            field.set(null, simplePlugin)

            simplePlugin
        }


        every { FileUtil.getInternalFileContent(any()) } answers {
            val fileName = it.invocation.args[0] as String
            Resources.getResource(fileName).readText().split("\n").toList()
        }

        every { Remain.injectServerName() } just runs
        every { AutoRegisterScanner.scanAndRegister() } just runs

        plugin = MockBukkit.load(WonderlandUHC::class.java)
    }

    @Test
    fun test() {
//        val player = server.addPlayer()
//        Assert.assertEquals(1, 1)

//        println(Resources.getResource("plugin.yml").readText().split("\n").toList())
    }
}