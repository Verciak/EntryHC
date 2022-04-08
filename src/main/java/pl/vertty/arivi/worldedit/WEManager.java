package pl.vertty.arivi.worldedit;

import cn.nukkit.block.Block;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;

public class WEManager {

    public int set(Position pos1, Position pos2, Block b) {
        int minX = (int) Math.min(pos1.x, pos2.x);
        int minY = (int) Math.min(pos1.y, pos2.y);
        int minZ = (int) Math.min(pos1.z, pos2.z);
        int maxX = (int) Math.max(pos1.x, pos2.x);
        int maxY = (int) Math.max(pos1.y, pos2.y);
        int maxZ = (int) Math.max(pos1.z, pos2.z);

        Vector3 v = new Vector3();
        int blocks = 0;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    pos1.level.setBlock(v.setComponents(x, y, z), b, true, false);
                    blocks++;
                }
            }
        }

        return blocks;
    }

    public int walls(Position pos1, Position pos2, Block b) {
        int minX = (int) Math.min(pos1.x, pos2.x);
        int minY = (int) Math.min(pos1.y, pos2.y);
        int minZ = (int) Math.min(pos1.z, pos2.z);
        int maxX = (int) Math.max(pos1.x, pos2.x);
        int maxY = (int) Math.max(pos1.y, pos2.y);
        int maxZ = (int) Math.max(pos1.z, pos2.z);

        Vector3 v = new Vector3();
        int blocks = 0;

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                pos1.level.setBlock(v.setComponents(x, y, minZ), b, true, false);
                pos1.level.setBlock(v.setComponents(x, y, maxZ), b, true, false);
                blocks += 2;
            }

            for (int z = minZ; z <= maxZ; z++) {
                pos1.level.setBlock(v.setComponents(minX, y, z), b, true, false);
                pos1.level.setBlock(v.setComponents(maxX, y, z), b, true, false);
                blocks += 2;
            }
        }

        return blocks;
    }

    public int copy(Position pos1, Position pos2, Vector3 center, BlocksCopy copy) {
        int minX = (int) Math.min(pos1.x, pos2.x);
        int minY = (int) Math.min(pos1.y, pos2.y);
        int minZ = (int) Math.min(pos1.z, pos2.z);
        int maxX = (int) Math.max(pos1.x, pos2.x);
        int maxY = (int) Math.max(pos1.y, pos2.y);
        int maxZ = (int) Math.max(pos1.z, pos2.z);

        int blocks = 0;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    int id = pos1.level.getBlockIdAt(x, y, z);

                    if (id == 0) {
                        continue;
                    }

                    int damage = pos1.level.getBlockDataAt(x, y, z);

                    Block b = Block.get(id, damage);

                    if (b != null) {
                        b.setComponents(x - center.getFloorX(), y - center.getFloorY(), z - center.getFloorZ());
                        copy.blocks.add(b);
                        blocks++;
                    }
                }
            }
        }

        return blocks;
    }

    public int paste(Position center, BlocksCopy copy) {
        int blocks = 0;
        Vector3 v = new Vector3();

        for (Block b : copy.blocks) {
            center.getLevel().setBlock(v.setComponents(center.getFloorX() + b.x, center.getFloorY() + b.y, center.getFloorZ() + b.z), b, true, false);
            blocks++;
        }

        return blocks;
    }

    public int cyl(Position center, int radius, Block b) {
        Vector3 v = new Vector3();
        int blocks = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z <= radius * radius) {
                    center.level.setBlock(v.setComponents(center.x + x, center.y, center.z + z), b, true, false);
                    blocks++;
                }
            }
        }

        return blocks;
    }

    public int hcyl(Position center, int radius, Block b) {

        Vector3 v = new Vector3();
        int blocks = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z <= radius * radius) {
                    if (x * x + z * z > (radius - 1) * (radius - 1)) {
                        center.level.setBlock(v.setComponents(center.x + x, center.y, center.z + z), b, true, false);
                        blocks++;
                    }
                }
            }
        }

        return blocks;
    }

    public int sphere(Position center, int radius, Block b) {
        Vector3 v = new Vector3();
        int blocks = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        center.level.setBlock(v.setComponents(center.x + x, center.y + y, center.z + z), b, true, false);
                        blocks++;
                    }
                }
            }
        }

        return blocks;
    }

    public int hsphere(Position center, int radius, Block b) {
        Vector3 v = new Vector3();

        int blocks = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        if (x * x + y * y + z * z > (radius - 1) * (radius - 1)) {
                            center.level.setBlock(v.setComponents(center.x + x, center.y + y, center.z + z), b, true, false);
                            blocks++;
                        }
                    }
                }
            }
        }

        return blocks;
    }

    public int replace(Position pos1, Position pos2, Block b, Block replace) {
        int minX = (int) Math.min(pos1.x, pos2.x);
        int minY = (int) Math.min(pos1.y, pos2.y);
        int minZ = (int) Math.min(pos1.z, pos2.z);
        int maxX = (int) Math.max(pos1.x, pos2.x);
        int maxY = (int) Math.max(pos1.y, pos2.y);
        int maxZ = (int) Math.max(pos1.z, pos2.z);

        Vector3 v = new Vector3();
        int blocks = 0;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (pos1.level.getBlockIdAt(x, y, z) == b.getId() && pos1.level.getBlockDataAt(x, y, z) == b.getDamage()) {
                        pos1.level.setBlock(v.setComponents(x, y, z), replace, true, false);
                        blocks++;
                    }
                }
            }
        }

        return blocks;
    }
}
