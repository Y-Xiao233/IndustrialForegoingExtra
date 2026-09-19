package net.yxiao233.industrialforegoingextra.api.shapes;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility methods for creating {@link VoxelShape}s that extend beyond a single block position.
 */
public final class BigBlockShapes {

    private static final AABB FROM_ORIGIN = new AABB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5);

    private BigBlockShapes() {
    }

    /**
     * Creates a single box shape using block local pixel coordinates (16 pixels = one block, values may go outside
     * the 0-16 range to extend into neighboring blocks).
     */
    public static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Block.box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Combines multiple shapes into one simplified shape.
     */
    public static VoxelShape combine(VoxelShape... shapes) {
        return batchCombine(Shapes.empty(), shapes);
    }

    /**
     * Combines multiple shapes into one simplified shape.
     */
    public static VoxelShape combine(Collection<VoxelShape> shapes) {
        return batchCombine(Shapes.empty(), shapes);
    }

    private static VoxelShape batchCombine(VoxelShape initial, Collection<VoxelShape> shapes) {
        VoxelShape combinedShape = initial;
        for (VoxelShape shape : shapes) {
            combinedShape = Shapes.joinUnoptimized(combinedShape, shape, BooleanOp.OR);
        }
        return combinedShape.optimize();
    }

    private static VoxelShape batchCombine(VoxelShape initial, VoxelShape... shapes) {
        VoxelShape combinedShape = initial;
        for (VoxelShape shape : shapes) {
            combinedShape = Shapes.joinUnoptimized(combinedShape, shape, BooleanOp.OR);
        }
        return combinedShape.optimize();
    }

    /**
     * Rotates a shape around the vertical axis, similar to how block models get rotated.
     */
    public static VoxelShape rotate(VoxelShape shape, Rotation rotation) {
        if (rotation == Rotation.NONE) {
            return shape;
        }
        List<VoxelShape> rotatedPieces = new ArrayList<>();
        //Explode the voxel shape into bounding boxes, rotate each one, and convert them back into voxel shapes
        for (AABB sourceBoundingBox : shape.toAabbs()) {
            //Make the bounding box be centered around the origin, and then move it back after rotating
            rotatedPieces.add(Shapes.create(rotate(sourceBoundingBox.move(FROM_ORIGIN.minX, FROM_ORIGIN.minY, FROM_ORIGIN.minZ), rotation)
                  .move(-FROM_ORIGIN.minX, -FROM_ORIGIN.minY, -FROM_ORIGIN.minZ)));
        }
        //Return the recombined rotated voxel shape
        return batchCombine(Shapes.empty(), rotatedPieces);
    }

    private static AABB rotate(AABB box, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_90 -> new AABB(-box.minZ, box.minY, box.minX, -box.maxZ, box.maxY, box.maxX);
            case CLOCKWISE_180 -> new AABB(-box.minX, box.minY, -box.minZ, -box.maxX, box.maxY, -box.maxZ);
            case COUNTERCLOCKWISE_90 -> new AABB(box.minZ, box.minY, -box.minX, box.maxZ, box.maxY, -box.maxX);
            default -> box;
        };
    }

    /**
     * Creates the four horizontal rotations of a shape in the order {@code [NORTH, SOUTH, WEST, EAST]}.
     * Use {@link #selectByDirection(VoxelShape[], Direction)} to select the shape for a facing.
     */
    public static VoxelShape[] horizontalRotations(VoxelShape shape) {
        return new VoxelShape[]{
              shape,                                        //NORTH
              rotate(shape, Rotation.CLOCKWISE_180),        //SOUTH
              rotate(shape, Rotation.COUNTERCLOCKWISE_90),  //WEST
              rotate(shape, Rotation.CLOCKWISE_90)          //EAST
        };
    }

    /**
     * Selects the shape that corresponds to the given horizontal facing.
     *
     * @param rotations shapes created via {@link #horizontalRotations(VoxelShape)}
     */
    public static VoxelShape selectByDirection(VoxelShape[] rotations, Direction direction) {
        return switch (direction) {
            case NORTH -> rotations[0];
            case SOUTH -> rotations[1];
            case WEST -> rotations[2];
            case EAST -> rotations[3];
            default -> rotations[0];
        };
    }
}
