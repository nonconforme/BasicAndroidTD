package com.towerdefense_patrickyevych;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.PointF;

public class Defense extends Sprite {

	protected PointF mLocation;
	protected int mRateOfFire;
	protected float mAreaOfEffect;
	protected float mRange;
	protected float mFacingDirection;
	protected int mCounter;

	public Defense(float pX, float pY, TextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		// TODO Auto-generated constructor stub
		mRateOfFire = 50;
		mCounter = 0;
		mFacingDirection=0;
		mRange = 400;
		MainGame.mDefenses.add(this);
	}

	public void Fire(float pAngle) {
		
		mCounter++;

		if (mCounter > mRateOfFire) {
			Bullet B = new Bullet(pAngle, 5f, this.getX()+ this.getWidth()/2,
					this.getY()+ this.getHeight()/2, MainGame.mBulletTexture);
			MainGame.mMainScene.attachChild(B);
			mCounter = 0;
		}
	}

	public float getDefenseRateOfFire() {
		return mRateOfFire;
	}

	public float getDefenseAreaOfEffect() {
		return mAreaOfEffect;
	}

	public float getDefenseRange() {
		return mRange;
	}

	public float getDefenseFacingDirection() {
		return mFacingDirection;
	}

	public float getDefenseCounter() {
		return mCounter;
	}

	public void setDefenseRateOfFire(int pRateOfFire) {
		mRateOfFire = pRateOfFire;
	}

	public void setDefenseAreaOfEffect(float pAreaOfEffect) {
		mAreaOfEffect = pAreaOfEffect;

	}

	public void setDefenseRange(float pRange) {
		mRange = pRange;
	}

	public void setDefenseFacingDirection(float pFacingDirection) {
		mFacingDirection = pFacingDirection;
	}

	public void setDefenseCounter(int pCounter) {

		mCounter = pCounter;
	}

	public void Rotate(float angle) {
		
		this.setRotationCenter(this.getWidth()/2, this.getHeight()/2);
		this.setRotation((float) Math.toDegrees(angle));
	}

}
