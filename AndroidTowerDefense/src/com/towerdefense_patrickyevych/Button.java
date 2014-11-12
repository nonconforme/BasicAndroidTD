package com.towerdefense_patrickyevych;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Button extends AnimatedSprite {
	public boolean mPressed;
	
	
	public Button(float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
		this.setSize(MainGame.mAspectRatio.x * pTiledTextureRegion.getWidth(),
		MainGame.mAspectRatio.y * pTiledTextureRegion.getHeight()*2);
	}

	public void press() {
		mPressed = true;
		this.setCurrentTileIndex(1);
		
	}
	
	public void unpress() {
		mPressed = false;
		this.setCurrentTileIndex(0);
	}
	
	public boolean isPressed() {
		return mPressed;
	}
	
	public void togglePressed() {
		mPressed = !mPressed;
	}
	
}
