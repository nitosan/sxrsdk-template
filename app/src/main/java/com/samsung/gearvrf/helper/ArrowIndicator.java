package com.samsung.gearvrf.helper;

import org.gearvrf.GVRContext;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.scene_objects.GVRModelSceneObject;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;

/**
 * Created by nite.luo on 1/30/2018.
 */

public class ArrowIndicator extends GVRSceneObject {

    GVRSceneObject mTarget;

    public ArrowIndicator(GVRContext context){
        super(context);

        try {

            GVRModelSceneObject arrow = context.getAssetLoader().loadModel("arrow.fbx");
            arrow.getTransform().setPosition(0,0,-1f);
            arrow.getTransform().setRotationByAxis(90, 1, 0, 0);
            arrow.getTransform().setScale(.5f, .5f, .5f);
            addChildObject(arrow);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTarget(GVRSceneObject target){
        mTarget = target;
    }

    public void update(){

        if(mTarget != null){
            //
            Vector3f eye = Gvr.getWorldPosition(this);
            Vector3f center = Gvr.getWorldPosition(mTarget);
            Vector3f up = new Vector3f(0,1,0);

            Vector4f pos = new Vector4f(getTransform().getPositionX(), getTransform().getPositionY(), getTransform().getPositionZ(), 1);

            Matrix4f mat = new Matrix4f();
            mat.lookAt(eye, center, up);


            Matrix4f matP = getParent().getTransform().getModelMatrix4f();
            matP.setColumn(3, new Vector4f(0,0,0,1));

            mat.mul(matP.invert());
            mat.setColumn(3, pos);

            getTransform().setModelMatrix(mat);
        }
    }
}
