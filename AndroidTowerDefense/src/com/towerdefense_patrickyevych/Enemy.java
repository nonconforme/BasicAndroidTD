package com.towerdefense_patrickyevych;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.PointF;

public class Enemy extends AnimatedSprite {

	protected PointF mLocation;
	boolean mEnemyActive;
	protected float mHealth;
	protected PointF mEnemySpeed;
	protected float mStrength;
	protected float mSize;
	protected float mColor;
	protected float mBounty;

	public Enemy(float pX, float pY, TiledTextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		mEnemyActive = true;
		mHealth = 100;
		mEnemySpeed= new PointF(-0.5f, 0);
		mLocation = new PointF(pX, pY);
		mStrength = 1;
		mBounty = 16;
		this.setSize(
				MainGame.mAspectRatio.x * MainGame.mZombieTexture.getWidth()/4,
				MainGame.mAspectRatio.y * MainGame.mZombieTexture.getHeight());
		MainGame.mEnemies.add(this);

		// TODO Auto-generated constructor stub
	}

	public void move() {
		mLocation.x = mLocation.x + mEnemySpeed.x;
		mLocation.y = mLocation.y + mEnemySpeed.y;
		this.setPosition(mLocation.x, mLocation.y);
	}

	public float getEnemyHealth() {
		return mHealth;
	}

	public PointF getEnemySpeed() {
		return (PointF) mEnemySpeed;
	}

	public float getEnemyStrength() {
		return mStrength;
	}

	public float getEnemySize() {
		return mSize;
	}

	public void setEnemyHealth(float pHealth) {
		mHealth = pHealth;

	}

	public void setEnemySpeed(PointF pEnemySpeed) {
		mEnemySpeed = pEnemySpeed;

	}

	public void setEnemyStrength(float pStrength) {
		mStrength = pStrength;
	}

	public void setEnemySize(float pSize) {
		mSize = pSize;
	}

	public boolean isEnemyActive() {
		return mEnemyActive;
	}

	public void toggleEnemyActive() {
		mEnemyActive = !mEnemyActive;
	}

	public void TakeDamage(float pDamage) {
		double critchance = Math.random();
		if (critchance < 0.01) {
			mHealth = 0;
		} else {
			mHealth -= pDamage;
		}

		if (mHealth <= 0) {
			toggleEnemyActive();
			MainGame.mGold += mBounty;
			MainGame.mGoldLabel.setText("Gold: " + MainGame.mGold);
			MainGame.mMainScene.detachChild(this);

		}
	}

}
