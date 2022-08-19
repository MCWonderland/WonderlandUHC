package org.mcwonderland.uhc

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import com.google.common.io.Resources
import io.mockk.*
import org.bukkit.Sound
import org.bukkit.WorldCreator
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mcwonderland.uhc.settings.AutoLoadStaticConfig
import org.mcwonderland.uhc.util.Extra
import org.mineacademy.fo.Common
import org.mineacademy.fo.FileUtil
import org.mineacademy.fo.MinecraftVersion
import org.mineacademy.fo.plugin.AutoRegisterScanner
import org.mineacademy.fo.plugin.SimplePlugin
import org.mineacademy.fo.remain.CompSound
import org.mineacademy.fo.settings.YamlStaticConfig
import java.io.File
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
        server.addSimpleWorld("world")

        mockFoundation()
        mockPluginThings()
        mockBukkitAPI()

        plugin = MockBukkit.load(WonderlandUHC::class.java)
    }

    private fun mockBukkitAPI() {
        mockkObject(server)

        every { server.createWorld(ofType()) } answers {
            server.addSimpleWorld((it.invocation.args[0] as WorldCreator).name())
        }
    }

    private fun mockPluginThings() {
        mockkStatic(NmsLoader::class)
        every { NmsLoader.initNms(any()) } just runs

        mockkStatic(Extra::class)
        every { Extra.createHead() } just runs
        every { Extra.deleteWorld(any()) } just runs

    }

    private fun mockFoundation() {
        mockkStatic(SimplePlugin::class)
        mockkStatic(FileUtil::class)

        mockkStatic(AutoRegisterScanner::class)
        mockkStatic(CompSound::class)
        mockkStatic(YamlStaticConfig::class)
        mockkStatic(Common::class)
        mockkStatic(DependencyChecker::class)
        mockkStatic(MinecraftVersion::class)

        every { MinecraftVersion.getCurrent() } answers { MinecraftVersion.V.v1_16 }

        mockkStatic(org.mineacademy.fo.remain.Remain::class)

        org.mineacademy.fo.remain.Remain.getOnlinePlayers()

        every { org.mineacademy.fo.remain.Remain.getCommandMap() } answers { server.commandMap }

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

        every { DependencyChecker.checkDepends() } just runs
        every { FileUtil.extract(any(), any()) } answers { File("./build/resources/main/${it.invocation.args[0]}") }
    }

    @Test
    fun test() {
        val scenarioManager = plugin.scenarioManager

        Assert.assertEquals(scenarioManager.scenarios.size, 1)
    }
}