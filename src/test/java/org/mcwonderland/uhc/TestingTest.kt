package org.mcwonderland.uhc

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import com.google.common.io.Resources
import io.mockk.*
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.junit.Before
import org.junit.Test
import org.mcwonderland.uhc.settings.AutoLoadStaticConfig
import org.mineacademy.fo.FileUtil
import org.mineacademy.fo.plugin.AutoRegisterScanner
import org.mineacademy.fo.plugin.SimplePlugin
import org.mineacademy.fo.remain.CompSound
import org.mineacademy.fo.remain.Remain
import org.mineacademy.fo.settings.YamlStaticConfig
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.name
import kotlin.streams.toList

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
        mockkStatic(CompSound::class)
        mockkStatic(YamlStaticConfig::class)

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

        every { CompSound.getFallback() } returns Sound.ENTITY_PLAYER_LEVELUP

        every { Remain.injectServerName() } just runs

        every { YamlStaticConfig.load(AutoLoadStaticConfig::class.java) } just runs

        every { AutoRegisterScanner.findValidClasses() } answers {
            val classLoader = ClassLoader.getSystemClassLoader()
            val rootPath = "./build/classes/java/main/"

            Files.walk(Path(rootPath))
                .filter(Files::isRegularFile)
                .filter { !it.name.contains("$") }
                .map { it.toString().replace(rootPath, "") }
                .map { it.replace("/", ".") }
                .map { it.replace(".class", "") }
                .map { classLoader.loadClass(it) }
                .toList()
        }

        plugin = MockBukkit.load(WonderlandUHC::class.java)
    }

    @Test
    fun test() {
//        val player = server.addPlayer()
//        Assert.assertEquals(1, 1)


    }
}