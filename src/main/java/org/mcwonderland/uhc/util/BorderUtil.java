package org.mcwonderland.uhc.util;

import lombok.AllArgsConstructor;
import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.border.BorderType;
import org.mcwonderland.uhc.game.settings.sub.UHCBorderSettings;
import org.mcwonderland.uhc.settings.Settings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 2019-12-11 下午 07:37
 */
public class BorderUtil {
    public static HashMap<World, Boolean> preBlocksPlaced = new HashMap<>();
    public static HashMap<World, Integer> preBlocksNumber = new HashMap<>();
    public static List<Location> preBorderBlocks = new ArrayList<>();

    public static void generateBorder(World world, int size) {
        int radius = getRadius(size);

        if (!preBlocksPlaced.containsKey(world))
            preBlocksPlaced.put(world, false);
        if (!preBlocksNumber.containsKey(world))
            preBlocksNumber.put(world, 0);

        preBlocksPlaced.put(world, false);
        preBlocksNumber.put(world, 0);

        if (preBlocksPlaced.get(world) == false) {

            /**
             * Minecraft的邊界系統頗奇怪
             * 輸入 /tp 25 90 25 會傳送到 25.5 90 25.5
             * 然而輸入 /tp -25 90 -25 則會傳送到 -24.5 90 -24.5
             * 或許是因為座標會自動「+0.5」的關係吧
             *
             * 因為這個原因，放基岩方塊時，可能要讓「負值」的方塊「再減一」，這樣才能真正放置到想要的位置
             * (例如: 想要放到 -25.5，則座標要為 -26.0)
             */

            int bedrockRadius = radius;
            int bedrockRadiusNative = -radius - 1;
            new BukkitRunnable() {
                boolean northLeftToRight = false;
                boolean southLeftToRight = false;
                boolean eastTopToDown = false;
                boolean westTopToDown = false;

                @Override
                public void run() {
                    if (!northLeftToRight) { // -x,-z 到 +x,-z
                        setFirstBedrocks(new CoordsData(bedrockRadiusNative, bedrockRadiusNative, bedrockRadius, bedrockRadiusNative));
                        northLeftToRight = true;
                        return;
                    }
                    if (!southLeftToRight) {// -x,+z 到 +x,+z
                        setFirstBedrocks(new CoordsData(bedrockRadiusNative, bedrockRadius, bedrockRadius, bedrockRadius));
                        southLeftToRight = true;
                        return;
                    }

                    if (!eastTopToDown) { // -x,-z+1 到 -x,+z-1
                        setFirstBedrocks(new CoordsData(bedrockRadiusNative, bedrockRadiusNative + 1, bedrockRadiusNative, bedrockRadius - 1));
                        eastTopToDown = true;
                        return;
                    }
                    if (!westTopToDown) {// +x,-z+1 到 +x,+z-1
                        setFirstBedrocks(new CoordsData(bedrockRadius, bedrockRadiusNative + 1, bedrockRadius, bedrockRadius - 1));
                        westTopToDown = true;
                        return;
                    }
                    preBlocksPlaced.put(world, true);
                    this.cancel();
                    return;
                }

                private void setFirstBedrocks(CoordsData coordsData) {
                    for (int x = coordsData.xFrom; x <= coordsData.xTo; x++) {
                        for (int z = coordsData.zFrom; z <= coordsData.zTo; z++) {
                            Block block = world.getHighestBlockAt(x, z);
                            for (int a = 0; a < 30; a++) {//debug 最多嘗試30次
                                if (isNeedToGoDown(block))
                                    block = block.getRelative(BlockFace.DOWN);
                                else
                                    break;
                            }
                            block = block.getRelative(BlockFace.UP);
                            DaTouNMS.getWorldNMS().setBlockSuperFast(block.getLocation(), CompMaterial.BEDROCK.getMaterial(), (byte) 0, false);
                            preBorderBlocks.add(block.getLocation());
                        }
                    }
                }

                private boolean isNeedToGoDown(Block block) {
                    return isContainAbleMaterials(block.getType()) || isNetherTopBedrock(block);
                }

                private boolean isNetherTopBedrock(Block block) {
                    String blockWorldName = block.getWorld().getName();
                    return blockWorldName.equalsIgnoreCase(world + "_nether")
                            && block.getType() == CompMaterial.BEDROCK.getMaterial();
                }

                private boolean isContainAbleMaterials(Material material) {
                    return CompMaterial.isAir(material)
                            || CompMaterial.isLongGrass(material)
                            || material.toString().contains("LEAVES")
                            || material.toString().contains("LOG")
                            || material.toString().contains("GRASS")
                            || material == CompMaterial.SUGAR_CANE.getMaterial()
                            || material == CompMaterial.SNOW.getMaterial()
                            || material == CompMaterial.WATER.getMaterial(); // prevent nether border gen in the top
                }

                @AllArgsConstructor
                class CoordsData {
                    final int xFrom;
                    final int zFrom;
                    final int xTo;
                    final int zTo;
                }
            }.runTaskTimer(WonderlandUHC.getInstance(), 0, 5L);
        } else {
            preBlocksNumber.put(world, preBlocksNumber.get(world) + 1);
            for (Location loc : preBorderBlocks) {
                DaTouNMS.getWorldNMS().setBlockSuperFast(loc.clone().add(0, preBlocksNumber.get(world), 0), CompMaterial.BEDROCK.getMaterial(), (byte) 0, false);
            }
        }
    }

    public static void clearBorderCaches() {
        preBlocksNumber.clear();
        preBorderBlocks.clear();
        preBlocksPlaced.clear();
    }

    public static boolean isInBorder(Location location) {
        World world = location.getWorld();
        int size;

        if (world.getEnvironment() == World.Environment.NETHER)
            size = GameUtils.getCurrentNetherBorder();
        else
            size = Game.getGame().getCurrentBorder();

        return isInBorder(location, size);
    }

    public static boolean isInBorder(Location loc, int borderSize) {
        int radius = getRadius(borderSize) + 1;

        return Math.abs(loc.getX()) < radius && Math.abs(loc.getZ()) < radius;
    }

    private static void set1_8Border(World world, double size) {
        if (Game.getSettings().getBorderSettings().getBorderType() == BorderType.TIMER
                && !Settings.Border.INCLUDE_18_BORDER)
            return;

        if (Game.getSettings().isUsingNether() && world.equals(UHCWorldUtils.getNether())) {
            WorldBorder wb = UHCWorldUtils.getNether().getWorldBorder();
            wb.setCenter(0, 0);
            wb.setSize((int) size + 2);
        } else {
            WorldBorder wb = world.getWorldBorder();
            wb.setCenter(0, 0);
            wb.setSize((int) size + 2);
        }
    }

    public static void moverBorder18(double time) {
        WorldBorder wb = UHCWorldUtils.getWorld().getWorldBorder();
        wb.setCenter(0, 0);
        wb.setWarningDistance(-2);
        wb.setSize((int) Game.getSettings().getBorderSettings().getFinalSizeOfShrinkModeBorder(), (int) time);
    }

    public static double getShrinkSpeedPerSecond(int time) {
        UHCBorderSettings settings = Game.getSettings().getBorderSettings();
        return getShrinkSpeedPerSecond(settings.getInitialBorder(), settings.getFinalSizeOfShrinkModeBorder(), time);
    }

    public static double getShrinkSpeedPerSecond(int from, int to, int seconds) {
        return MathUtil.formatFiveDigitsD((from - to) / (seconds * 2D));
    }

    public static int getShrinkSecondsCost() {
        UHCBorderSettings settings = Game.getSettings().getBorderSettings();
        return getShrinkSecondsCost(settings.getInitialBorder(),
                settings.getFinalSizeOfShrinkModeBorder(),
                settings.getBorderShrinkSpeed());
    }

    public static int getShrinkSecondsCost(int from, int to, double shrinkBlocksPerSecond) {
        return (int) ((from - to) / (shrinkBlocksPerSecond * 2));
    }

    public static void removeUHCWorldWBBorders() {
        for (String worldName : UHCWorldUtils.getUhcWorldNames())
            removeWBBorder(worldName);
    }

    public static void removeWBBorder(World world) {
        removeWBBorder(world.getName());
    }

    public static void removeWBBorder(String worldName) {
        Common.dispatchCommand(null, "wb " + worldName + " clear");
    }

    public static void setInitialBorders() {
        for (World uhcWorld : UHCWorldUtils.getUhcWorlds()) {
            uhcWorld.getWorldBorder().setWarningTime(5);
            uhcWorld.getWorldBorder().setWarningDistance(5);
        }

        UHCBorderSettings borderSettings = Game.getSettings().getBorderSettings();
        setBorders(UHCWorldUtils.getWorld(), borderSettings.getInitialBorder());
        setBorders(UHCWorldUtils.getNether(), borderSettings.getInitialNetherBorder());
    }

    public static void setBorders(World world, int size) {
        set1_8Border(world, size);
        setWBBorder(world, size);
    }

    private static void setWBBorder(World world, int size) {
        int radius = getRadius(size) + 1;

        String worldName = world.getName();
        Common.dispatchCommand(null, "wb " + worldName + " set " + radius + " " + radius + " 0 0");
        Common.dispatchCommand(null, "wb wshape " + worldName + " square");
    }

    public static int getRadius(int size) {
        return (int) MathUtil.ceiling(size / 2);
    }


    public static int getMoveBorder(World world) {
        return (int) world.getWorldBorder().getSize();
    }
}
