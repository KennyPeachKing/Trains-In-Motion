package trains.models;

import com.sun.javafx.geom.Vec2f;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import trains.entities.GenericRailTransport;
import trains.utility.ClientProxy;
import trains.utility.RailUtility;

import javax.annotation.Nullable;

/**
 * <h2> .Java Entity Rendering</h2>
 * to be used for rendering all trains and rollingstock, along with their particle effects.
 * for the variables fed to this class:
 * @see ClientProxy#register()
 */
public class RenderEntity extends Render {

    private ModelBase model;
    private ModelBase bogieModel;
    private ResourceLocation texture;
    private ResourceLocation bogieTexture;
    private Vec2f wheelConnectorPos = null;
    /*
     * hitboxBase is used for the bottom and top, side is used for the two long sides
     * hitboxFront is used for front and back
     */
    private float wheelPitch=0;
    private char smokeType = 'n';
    /**
     * <h3>class constructor</h3>
     * @param modelLoad the model class to render.
     * @param textureLoad the texture ResourceLocation to apply to the model.
     * @param modelBogie the model to render for the bogies, if null then no bogies will not be rendered.
     * @param bogieTexture the texture to apply to the bogie, can be null if the bogie model is null.
     * @param particleEffect the type of particle effect to render.
     *                       n - null
     *                       s - Smoke (steam)
     *                       d - Smoke (diesel)
     *                       r - Steam (nuclear steam)
     */
    public RenderEntity(ModelBase modelLoad, ResourceLocation textureLoad, @Nullable ModelBase modelBogie, @Nullable ResourceLocation bogieTexture, char particleEffect) {
        model = modelLoad;
        texture = textureLoad;
        bogieModel = modelBogie;
        this.bogieTexture = bogieTexture;
        smokeType = particleEffect;
    }

    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }

    /**
     * <h3>base render extension</h3>
     * acts as a redirect for the base render function to our own class.
     * This is just to do a more efficient type cast.
     */
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick){
        if (entity instanceof GenericRailTransport){
            doRender((GenericRailTransport) entity,x,y,z,yaw,partialTick);
        }
    }

    /**
     * <h3>Actually render</h3>
     *
     * here we define position, rotation, pitch, bind the texture, render the model, and then manage smoke.
     *
     * Most all of this is pretty self-explanatory by function name, except for:
     *      glPushMatrix - this basically allocates room that we want to do something, like the GL equivalent of a starting bracket.
     *      glPopMatrix - this basically tells GL to do what we just allocated, like the GL equivalent of an ending bracket.
     *
     * @param entity the entity to render.
     * @param x the x position of the entity with offset for the camera position.
     * @param y the y position of the entity with offset for the camera position.
     * @param z the z position of the entity with offset for the camera position.
     * @param yaw is used to rotate the train's yaw, its exactly the same as entity.rotationYaw.
     * @param partialTick not used.
     *
     * TODO: texture recolor similar to game maker's colorize partial so we can change the color without effecting grayscale, or lighting like on rivets.
     *                    May be able to use multiple instances of this to have multiple recolorable things on the train.
     *
     */
    public void doRender(GenericRailTransport entity, double x, double y, double z, float yaw, float partialTick){

    	GL11.glPushMatrix();
        //set the render position
        GL11.glTranslated(x, y + 2.4d, z);
        //rotate the model.
            GL11.glRotatef((-yaw) - 90, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(entity.rotationPitch - 180f, 0.0f, 0.0f, 1.0f);

            if (entity.bogie.size() > 0) {
                wheelPitch += 1f;//(float) (((GenericRailTransport) entity).bogie.get(0).motionX + ((GenericRailTransport) entity).bogie.get(0).motionZ) * 0.05f;
                if (wheelPitch > 360) {
                    wheelPitch = 0;
                }
            }

            //Bind the texture and render the model
            bindTexture(texture);

            ModelRenderer boxRender;
            for (Object box : model.boxList) {
                if (box instanceof ModelRenderer) {
                    boxRender = (ModelRenderer) box;
                    if (boxRender.boxName != null) {
                        if (boxRender.boxName.equals("wheel")) {
                            boxRender.rotateAngleX = wheelPitch;
                        } else if (boxRender.boxName.equals("wheelconnector")) {
                            if (wheelConnectorPos == null) {
                                wheelConnectorPos = new Vec2f(boxRender.rotationPointZ, boxRender.rotationPointY);
                            }
                            boxRender.rotationPointY = wheelConnectorPos.y - (entity.getPistonOffset() * MathHelper.cos(-wheelPitch * RailUtility.radianF));
                            boxRender.rotationPointZ = wheelConnectorPos.x - (entity.getPistonOffset() * MathHelper.sin(-wheelPitch * RailUtility.radianF));
                        }
                    }
                }
            }

        if (model instanceof trains.models.tmt.ModelBase){
            ((trains.models.tmt.ModelBase) model).wheelRotation = wheelPitch;
        }


        model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.065f);
        //hitbox is special since it is initialized in the draw method, if it isn't it may be too big or too small

        GL11.glPushMatrix();



        //clear the cache, one pop for every push.
        GL11.glPopMatrix();
        GL11.glPopMatrix();



        /**
         * <h2>Animation</h2>
         * while unimplemented, animation _should_ work by having the cube be initialized as
         * 		Elem18 = new ModelRenderer(this, "wheel");
         *      Elem18.setTextureOffset(0,0);
         * rather than
         * 		Elem18 = new ModelRenderer(this, 0,0);
         * this same practice can be used for other features like pistons, the render only needs to check if the name of the cube matches and pragmatically apply the positioning.
         */

        /**
         * <h4> render bogies</h4>
         * in TiM here we render the bogies.
         * we get the entity and do an instanceof check, see if it's a train or a rollingstock, then do a for loop on the bogies, and render them the same way we do trains and rollingstock.
         */

        /**
         * <h4>Smoke management</h4>
         * some trains, and even rollingstock will have smoke,
         */
        switch (smokeType){
            case 'n':{break;}
            //TODO
        }

    }

}