package ebf.tim.blocks.rails;

import ebf.tim.models.rails.ModelRailSegment;
import ebf.tim.utility.RailUtility;
import net.minecraft.util.MathHelper;
import tmt.Vec3d;
import net.minecraft.world.World;
import tmt.Vec3f;

import java.util.List;

import static ebf.tim.blocks.rails.BlockRailCore.checkMultiblock;
import static ebf.tim.utility.RailUtility.radianF;

/**
 * @author Eternal Blue Flame
 */
public class RailVanillaShapes {


    public static List<?extends ModelRailSegment> vanillaZStraight(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        Vec3f[] coords = rotateYaw(0,
                0,0,0.5f,
                0,0, 0.25f,
                0,0, -0.25f,
                0,0, -0.5f
        );

        //X axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            coords[3].yCoord = 0.15f;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            coords[0].yCoord = -0.15f;
        }
        //Z axis slope
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            coords[0].yCoord = 0.15f;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            coords[3].yCoord = -0.15f;
        }

        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,1},{xCoord, yCoord,zCoord+2,0}})){
            coords[0]=rotateYaw(0,0,0,1);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,1},{xCoord, yCoord,zCoord-2,0}})){
            coords[3]=rotateYaw(0,0,0,-1);
        }

        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,7},{xCoord-1, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,6},{xCoord+1, yCoord,zCoord-1,8}})){
            coords[3]=rotateYaw(0,0,0,0);
            coords[2]=rotateYaw(0,0,0,0.25f);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,9}, {xCoord+1, yCoord,zCoord+1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,8},{xCoord-1, yCoord,zCoord+1,6}})){
            coords[0]=rotateYaw(0,0,0,0);
            coords[1]=rotateYaw(0,0,0,-0.25f);
        }else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,7 }}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,6}})){
            coords[0]=rotateYaw(0,0,0,1.5f);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,8}})) {
            coords[3]=rotateYaw(0,0,0,-1.5f);
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }

    public static List<?extends ModelRailSegment> vanillaXStraight(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        Vec3f[] coords = rotateYaw(0,
                -0.5f,0,0,
                -0.25f,0,0,
                0.25f,0,0,
                0.5f,0,0
        );

        //X axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            coords[0].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[3].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3},{xCoord-1, yCoord,zCoord,3}})){
            coords[1].yCoord = 0.05f;
            coords[2].yCoord = -0.05f;
        }
        //Z axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            coords[3].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}, {xCoord+1, yCoord,zCoord,2}})){
            coords[1].yCoord = -0.05f;
            coords[2].yCoord = 0.05f;
        }
        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,0},{xCoord-2, yCoord,zCoord,1}})){
            coords[0]=rotateYaw(0,-1,0,0);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,0},{xCoord+2, yCoord,zCoord,1}})){
            coords[3]=rotateYaw(0,1,0,0);
        }


        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,9},{xCoord-1, yCoord,zCoord-1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,6},{xCoord-1, yCoord,zCoord+1,8}})){

            coords[0]=rotateYaw(0,0,0,0);
            coords[1]=rotateYaw(0,0.25f,0,0);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,7}, {xCoord+1, yCoord,zCoord+1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,8},{xCoord+1, yCoord,zCoord-1,6}})){

            coords[3]=rotateYaw(0,0,0,0);
            coords[2]=rotateYaw(0,-0.25f,0,0);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,6}})){
            coords[3]=rotateYaw(0,1.5f,0,0);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,8}})){
            coords[0]=rotateYaw(0,1.5f,0,0);
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }


    public static List<?extends ModelRailSegment> vanillaCurve6(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        Vec3f[] coords = rotateYaw(0,
                0f,0,0.5f,
                0f,0, 0.25f,
                0.25f,0,0f,
                0.5f,0,0f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,8}})){
            coords[2]=rotateYaw(0,0.375f,0,0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                coords[0]=rotateYaw(0,0,0,1);
                coords[1]=rotateYaw(0,0,0,0.5f);
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,8}})){
            coords[1]=rotateYaw(0,0.125f,0,0.375f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,1}})){
                coords[3]=rotateYaw(0,1,0,0);
                coords[2]=rotateYaw(0,0.5f,0,0);
            }
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }

    public static List<?extends ModelRailSegment> vanillaCurve8(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        //the base shape
        Vec3f[] coords = rotateYaw(0,
                -0.5f,0, 0,
                -0.25f, 0,0,
                0,0, -0.25f,
                0,0, -0.5f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,6}})){
            //first half of the diagonal
            coords[1]=rotateYaw(0,-0.375f,0,-0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = rotateYaw(0, 0,0,-1.0625f);
                coords[2] = rotateYaw(0, 0,0, -0.5f);
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,6}})) {
            //second half of the diagonal
            coords[2] = rotateYaw(0, -0.375f,0, -0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                coords[0] = rotateYaw(0, -1,0, 0);
                coords[1] = rotateYaw(0, -0.6875f,0, 0 );
            }
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }


    public static List<?extends ModelRailSegment> vanillaCurve7(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        //the base shape
        Vec3f[] coords = rotateYaw(0,
                -0.5f, 0,0,
                -0.25f, 0,0,
                0,0, 0.25f,
                0,0, 0.5f);


        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,9}})){
            //first half of the diagonal
            coords[1] = rotateYaw(0, -0.375f,0, 0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = rotateYaw(0,0, 0,1);
                coords[2] = rotateYaw(0, 0,0, 0.5f);
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,9}})){
            //second half of the diagonal
            coords[2] = rotateYaw(0, -0.375f, 0,0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,1}})){
                //extension of one side to match up with a straight block
                coords[0] = rotateYaw(0, -1.05f, 0,0);
                coords[1] = rotateYaw(0, -0.75f , 0,0);
            }
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }

    public static List<?extends ModelRailSegment> vanillaCurve9(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        //the base shape
        Vec3f[] coords = rotateYaw(0,
                0.5f, 0,0,
                0.25f, 0,0,
                0,0, -0.25f,
                0,0, -0.5f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,7}})){
            //first half of the diagonal
            coords[1] = rotateYaw(0, 0.375f, 0,-0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = rotateYaw(0, 0,0,-1.0625f);
                coords[2] = rotateYaw(0, 0, 0,-0.5f);
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,7}})) {
            //second half of the diagonal
            coords[2] = rotateYaw(0, 0.375f, 0,-0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord + 1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                coords[0] = rotateYaw(0, 1.1f, 0,-0);
                coords[1] = rotateYaw(0, 0.6875f,0, 0 );
            }
        }


        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }





    public static List<?extends ModelRailSegment> vanillaSlopeZ5(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        Vec3f[] coords = rotateYaw(0,
                0,0.1f,-0.6f,
                0,0.25f,-0.25f,
                0,0.775f,0.25f,
                0,0.88f, 0.5f);
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1,5}})){
            coords[0].yCoord=-0.05f;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord+1,5}})){
            coords[3].yCoord=1;
            coords[2].yCoord=0.75f;
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }

    public static List<?extends ModelRailSegment> vanillaSlopeZ4(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        Vec3f[] coords = rotateYaw(0,
                0,0.13f,0.5f,
                0,0.2f,0.25f,
                0,0.75f,-0.25f,
                0,0.875f, -0.5f);
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1,4}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord-1,4}})){
            coords[3].yCoord=1.05f;
            coords[2].yCoord=0.75f;
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }

    public static List<?extends ModelRailSegment> vanillaSlopeX2(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        Vec3f[] coords = rotateYaw(0,
                -0.525f,0.13f,0,
                -0.25f, 0.25f, 0,
                0.25f, 0.8f, 0,
                0.525f,0.88f,0);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord+1,zCoord,2}})){
            coords[3].yCoord = 1.05f;
            coords[2].yCoord=0.75f;
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }

    public static List<?extends ModelRailSegment> vanillaSlopeX3(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        Vec3f[] coords = rotateYaw(0,
                0.525f,0.11f,0,
                0.25f, 0.225f, 0,
                -0.25f, 0.75f, 0,
                -0.525f,0.865f,0);
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord+1,zCoord,3}})){
            coords[3].yCoord=1.05f;
            coords[2].yCoord=0.75f;
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge, 1);
    }


    /**
     * rotates an already defined path
     * @param path already defined path
     * @param yaw yaw to rotate around, if 0 returns path
     * @return
     */
    public static Vec3f[] rotateYaw(Vec3f[] path, float yaw) {
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            float cos = MathHelper.cos(yaw);
            float sin = MathHelper.sin(yaw);
            float xCoord;
            float zCoord;
            for(Vec3f xyz: path) {
                xCoord =xyz.xCoord-0.5f;
                zCoord =xyz.zCoord-0.5f;
                xyz.xCoord = (xCoord * cos) - (zCoord * sin);
                xyz.zCoord = (xCoord * sin) + (zCoord * cos);
                xyz.xCoord+=0.5f;
                xyz.zCoord+=0.5f;
            }
        }
        return path;
    }

    /**
     * defines a path based on the provided x/y/z vectors.
     * @param yaw rotates the path if it's not 0
     * @return
     */
    public static Vec3f[] rotateYaw(float yaw, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            float cos = MathHelper.cos(yaw);
            float sin = MathHelper.sin(yaw);

            float xCoord =x1;
            float zCoord =z1;
            x1 = (xCoord * cos) - (zCoord * sin);
            z1 = (xCoord * sin) + (zCoord * cos);

            xCoord =x2;
            zCoord =z2;
            x2 = (xCoord * cos) - (zCoord * sin);
            z2 = (xCoord * sin) + (zCoord * cos);

            xCoord =x3;
            zCoord =z3;
            x3 = (xCoord * cos) - (zCoord * sin);
            z3 = (xCoord * sin) + (zCoord * cos);

            xCoord =x4;
            zCoord =z4;
            x4 = (xCoord * cos) - (zCoord * sin);
            z4 = (xCoord * sin) + (zCoord * cos);
        }
        return new Vec3f[]{new Vec3f(x1+0.5f, y1, z1+0.5f), new Vec3f(x2+0.5f, y2, z2+0.5f), new Vec3f(x3+0.5f, y3, z3+0.5f), new Vec3f(x4+0.5f, y4, z4+0.5f)};
    }

    /**
     * defines a path based on the provided x/y/z vectors. this one is aimed more-so at defining individual points to replace points of a path.
     * good for deforms or other weird edits.
     * @param yaw rotates the path if it's not 0
     * @return
     */
    public static Vec3f rotateYaw(float yaw, float x1, float y1, float z1) {
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            float cos = MathHelper.cos(yaw);
            float sin = MathHelper.sin(yaw);

            float xCoord =x1;
            float zCoord =z1;
            x1 = (xCoord * cos) - (zCoord * sin);
            z1 = (xCoord * sin) + (zCoord * cos);
        }
        return new Vec3f(x1+0.5f, y1, z1+0.5f);
    }

}
