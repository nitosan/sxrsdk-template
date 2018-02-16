package com.samsung.gearvrf.helper;


import com.example.org.gvrfapplication.R;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRCursorController;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRPhongShader;
import org.gearvrf.GVRPicker;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;
import org.gearvrf.io.GVRInputManager;
import org.gearvrf.scene_objects.GVRCubeSceneObject;
import org.gearvrf.utility.Log;

public class Gvr {
    private static GVRContext s_Context = null;
    private static String TAG = "GVR Helper";
    private static GVRPicker s_Picker;

    public static void init(GVRContext context){
        s_Context = context;

        initControllers();
    }

    /***************************************
     * Scene Objects
     ***************************************/

    public static GVRCubeSceneObject createCube(){
        if (s_Context == null) {
            Log.e(TAG, "GVRContext is not initialized");
            return null;
        }

        GVRCubeSceneObject cube = new GVRCubeSceneObject(s_Context);
        cube.getRenderData().setShaderTemplate(GVRPhongShader.class);
        return cube;
    }

    public static GVRSceneObject createMesh(int meshID, int textureID) {
        if (s_Context == null) {
            Log.e(TAG, "GVRContext is not initialized");
            return null;
        }

        GVRMesh mesh = s_Context.getAssetLoader().loadMesh(new GVRAndroidResource(s_Context, meshID));
        GVRTexture texture = s_Context.getAssetLoader().loadTexture(new GVRAndroidResource(s_Context, textureID));
        GVRSceneObject sceneObject = new GVRSceneObject(s_Context, mesh, texture);

        return sceneObject;
    }

    public static GVRSceneObject createQuad(float width, float height, int textureID){
        GVRSceneObject quad = new GVRSceneObject(s_Context,
                s_Context.createQuad(width, height),
                s_Context.getAssetLoader().loadTexture(new GVRAndroidResource(s_Context, textureID)));

        return quad;
    }

    /***************************************
     * Controllers
     ***************************************/

    private static void initControllers() {
        s_Picker = new GVRPicker(s_Context, s_Context.getMainScene());

        GVRInputManager input = s_Context.getInputManager();

        input.selectController(new GVRInputManager.ICursorControllerSelectListener() {
            @Override
            public void onCursorControllerSelected(GVRCursorController newController, GVRCursorController oldController) {
                if (oldController != null)
                {
//                    oldController.removePickEventListener(mPickHandler);
                }

//                newController.addPickEventListener(mPickHandler);
                GVRSceneObject cursor = new GVRSceneObject(
                        s_Context,
                        s_Context.createQuad(1f, 1f),
                        s_Context.getAssetLoader().loadTexture(new GVRAndroidResource(s_Context, R.raw.cursor)));

                cursor.getRenderData().setDepthTest(false);
                cursor.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.OVERLAY);
                newController.setCursor(cursor);
                newController.setCursorDepth(-7);
                newController.setCursorControl(GVRCursorController.CursorControl.PROJECT_CURSOR_ON_SURFACE);
            }
        });
    }
}
