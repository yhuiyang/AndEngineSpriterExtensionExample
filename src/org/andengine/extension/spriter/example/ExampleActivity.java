/*
 * Spriter Extension for AndEngine
 *
 * Copyright (c) 2012 Arturo Gutiérrez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package org.andengine.extension.spriter.example;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.spriter.SpriterEntity;
import org.andengine.extension.spriter.SpriterLoader;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class ExampleActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    private final float CAMERA_WIDTH = 480.0f;
    private final float CAMERA_HEIGHT = 800.0f;
    private Scene mMainScene;
    private SpriterEntity mSprite;
    private SpriterEntity mSprite2;
    private int animationId = 0;

    @Override
    public EngineOptions onCreateEngineOptions() {
        // Create camera
        Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        // Make camera center = origin, so the scene looks like canvas UI in Spriter
        camera.setCenter(0, 0);

        // Return engine options
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(camera.getWidth() / camera.getHeight()), camera);
    }

    @Override
    protected void onCreateResources() {
        // Create sprite
        mSprite = SpriterLoader.createSpriterFrom(this, "example.scml");
        // Load textures
        mSprite.loadResources(this, getTextureManager(), getVertexBufferObjectManager());

        // Create sprite
        mSprite2 = SpriterLoader.createSpriterFrom(this, "example.scml");
        // Load textures
        mSprite2.loadResources(this, getTextureManager(), getVertexBufferObjectManager());
    }

    @Override
    protected Scene onCreateScene() {
        // Create empty Scene
        mMainScene = new Scene();
        mMainScene.setOnSceneTouchListener(this);

        if (mSprite != null) {
            // There are two animation defined in the Spriter, choose anyone by index or name.
            // Animations: Idle(0), Posture(1)
            mSprite.setAnimation(0);
            // mSprite.setAnimation(1);
            // mSprite.setAnimation("idle");
            // mSprite.setAnimation("posture");

            // Attach sprite
            mMainScene.attachChild(mSprite);
        }

        if (mSprite2 != null) {
            mSprite2.setY(-400);
            // There are two animation defined in the Spriter, choose anyone by
            // index or name.
            // Animations: Idle(0), Posture(1)
            mSprite2.setAnimation(1);
            // mSprite.setAnimation(1);
            // mSprite.setAnimation("idle");
            // mSprite.setAnimation("posture");

            // Attach sprite
            mMainScene.attachChild(mSprite2);
        }

        if (BuildConfig.DEBUG)
            this.getEngine().registerUpdateHandler(new FPSLogger());

        return mMainScene;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown()) {
            animationId ^= 1;
            mSprite.setAnimation(animationId);
            mSprite2.setAnimation(animationId ^ 1);
        }
        return false;
    }

}
