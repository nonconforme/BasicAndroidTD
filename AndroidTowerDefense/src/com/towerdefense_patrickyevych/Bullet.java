package com.towerdefense_patrickyevych;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.PointF;

public class Bullet extends Sprite {
	protected float mBulletSpeed;
	protected float mFacingDirection;
	protected int mCounter;
	protected float mLifeTime;
	protected float mAngle;
	protected boolean mActive;
	protected float mDamageOutput;
	protected PointF mLocation;
	protected float mSize;
	protected float mColor;


	public Bullet(float pA, float pBS, float pX, float pY, TextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		mActive = true;
		mAngle = pA;
		mBulletSpeed = pBS;
		mCounter = 0;
		mLifeTime = 150;
		mDamageOutput = 40;
		mLocation = new PointF(pX, pY);
		this.setSize(MainGame.mAspectRatio.x*MainGame.mBulletTexture.getWidth(), MainGame.mAspectRatio.y * MainGame.mBulletTexture.getHeight());
		MainGame.mBullets.add(this);
		
		
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		mLocation.x = (float) (mLocation.x + Math.cos(mAngle)*mBulletSpeed);
		mLocation.y = (float) (mLocation.y + Math.sin(mAngle)*mBulletSpeed);
		this.setPosition(mLocation.x, mLocation.y);
		mCounter++;
		
		if(mCounter > mLifeTime) {
			mActive = false;
		
		}
		
	}

	public int getCounter() {
		return mCounter;
	}
	
	public float getBulletSpeed() {
		return mBulletSpeed;
	}

	public float getBulletAngle() {
		return mAngle;
	}
	
	public PointF getBulletLocation() {
		return mLocation;
	}
	
	public boolean isBulletActive() {
		return mActive;
	}

	public float getDefenseDamageOutput() {
		return mDamageOutput;
	}

	public void setDefenseDamageOutput(float pDamageOutput) {
		mDamageOutput = pDamageOutput;
	}
	
	public void setBulletSpeed(float pSpeed) {
		mBulletSpeed = pSpeed;
	}
	
	public void setBulletAngle(float pAngle) {
		mAngle = pAngle;
	}
	
	public void setBulletLocation(PointF pLocation) {
		mLocation = pLocation;
	}
	public void toggleBulletActive() {
		mActive = !mActive;
	}
	
	
	
	public void hit() {
		this.toggleBulletActive();
		MainGame.mMainScene.detachChild(this);
		
	}
	
}
