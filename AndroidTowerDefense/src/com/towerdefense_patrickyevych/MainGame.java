package com.towerdefense_patrickyevych;

import java.util.ArrayList;
import java.util.Timer;

import org.anddev.andengine.engine.*;
import org.anddev.andengine.engine.camera.*;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.*;
import org.anddev.andengine.engine.options.EngineOptions.*;
import org.anddev.andengine.engine.options.resolutionpolicy.*;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.scene.*;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.*;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.*;
import org.anddev.andengine.opengl.texture.atlas.bitmap.*;
import org.anddev.andengine.opengl.texture.region.*;
import org.anddev.andengine.ui.activity.*;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;

public class MainGame extends BaseGameActivity implements IOnSceneTouchListener {
	public Button mCannonButton1;
	public boolean mGameOver;
	public static int mGold;
	public static ChangeableText mGoldLabel;
	public static int mHealth;
	public static ChangeableText mHealthLabel;
	public Font mGoldFont;
	public Font mHealthFont;
	public Font mPauseFont;
	public BitmapTextureAtlas mGoldFontTextureAtlas;
	public BitmapTextureAtlas mPauseFontTextureAtlas;
	public BitmapTextureAtlas mHealthFontTextureAtlas;
	public static ChangeableText mPauseLabel;
	private int mNumEnemies;
	private int mSpawnCounter;
	private int mSpawnDelay;
	private int mEnemySpawn;
	private int mWaveCounter;
	BitmapTextureAtlas mBitmapTextureAtlas;
	Camera mCamera;
	static PointF mAspectRatio;
	public static Scene mMainScene;
	Display mDisplay;
	int mCameraWidth;
	int mCameraHeight;
	Sprite mBackground;
	Sprite mTower;
	Sprite mPauseMenu;
	public static ArrayList<Enemy> mEnemies;
	public static ArrayList<Defense> mDefenses;
	public static ArrayList<Bullet> mBullets;
	static TextureRegion mMortarBulletTexture;
	static TextureRegion mBulletTexture;
	static TextureRegion mFrostBulletTexture;
	TextureRegion mBackgroundTexture;
	TextureRegion mCannonTexture;
	static TiledTextureRegion mZombieTexture;
	public TiledTextureRegion mCannonButton1Texture;
	TextureRegion mTowerTexture;
	TextureRegion mFrostCannonTexture;
	TextureRegion mMortarTexture;
	TextureRegion mAngryZombieTexture;
	TextureRegion mArtileryBatteryTexture;
	TextureRegion mPauseMenuTexture;
	Defense mCannon;
	public final int CANNONCOST = 100;

	TimerHandler mGameTimer;
	final float TIMERDELAY = 0.033f;
	boolean mPause;

	@Override
	public void onLoadComplete() {

		mEngine.getTextureManager().reloadTextures();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Engine onLoadEngine() {
		mDisplay = getWindowManager().getDefaultDisplay();
		mCameraHeight = mDisplay.getHeight();
		mCameraWidth = mDisplay.getWidth();
		mCamera = new Camera(0, 0, mCameraWidth, mCameraHeight);

		return new Engine(
				new EngineOptions(true, ScreenOrientation.LANDSCAPE,
						new RatioResolutionPolicy(mCameraWidth, mCameraHeight),
						mCamera).setNeedsSound(true).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {

		mBitmapTextureAtlas = new BitmapTextureAtlas(2048, 2048,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBackgroundTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this,
						"Grassybackground.png", 0, 0);
		mAspectRatio = new PointF(mCameraWidth
				/ (float) (mBackgroundTexture.getWidth()), mCameraHeight
				/ ((float) mBackgroundTexture.getHeight()));
		mCannonTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "Cannon.png", 0,
						600);
		mZombieTexture = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mBitmapTextureAtlas, this, "Zombies.png",
						0, 650, 4, 1);
		mTowerTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mBitmapTextureAtlas, this, "Tower.png", 0, 700);

		mFrostCannonTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "frostCannon.png",
						0, 750);

		mMortarTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "mortar.png", 0,
						800);
		mArtileryBatteryTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this,
						"artillerybattery.png", 0, 850);
		mBulletTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "Bullet.png", 0,
						900);

		mFrostBulletTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "frostBullet.png",
						0, 950);
		mMortarBulletTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "MortarBullet.png",
						0, 1000);

		mPauseMenuTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "pausemenu.png", 0,
						1300);
		mCannonButton1Texture = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mBitmapTextureAtlas, this, "Button.png",
						0, 1500, 2, 1);

		mEngine.getTextureManager().loadTexture(mBitmapTextureAtlas);

		mGoldFontTextureAtlas = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		int pTempGoldSize = (int) (40 * mAspectRatio.y);
		mGoldFont = new Font(mGoldFontTextureAtlas, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), pTempGoldSize, true,
				Color.YELLOW);

		mEngine.getTextureManager().loadTexture(mGoldFontTextureAtlas);
		mEngine.getFontManager().loadFont(mGoldFont);

		mHealthFontTextureAtlas = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		int pTempHealthSize = (int) (40 * mAspectRatio.y);
		mHealthFont = new Font(mHealthFontTextureAtlas, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), pTempHealthSize, true,
				Color.RED);
		mEngine.getTextureManager().loadTexture(mHealthFontTextureAtlas);
		mEngine.getFontManager().loadFont(mHealthFont);
		mPauseFontTextureAtlas = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		int pTempPauseSize = (int) (40 * mAspectRatio.y);
		mPauseFont = new Font(mPauseFontTextureAtlas, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), pTempPauseSize, true,
				Color.GREEN);
		mEngine.getTextureManager().loadTexture(mPauseFontTextureAtlas);
		mEngine.getFontManager().loadFont(mPauseFont);

	}

	@Override
	public Scene onLoadScene() {
		mGameOver = false;
		mHealth = 20;
		mGold = 250;
		mPause = false;
		mWaveCounter = 1;
		mNumEnemies = 100;
		mEnemySpawn = 0;
		mSpawnDelay = 100;
		mSpawnCounter = 0;
		mDefenses = new ArrayList<Defense>();
		mEnemies = new ArrayList<Enemy>();
		mBullets = new ArrayList<Bullet>();
		mMainScene = new Scene();
		mMainScene.setOnSceneTouchListener(this);

		mBackground = new Sprite(0, 0, mBackgroundTexture);
		mBackground.setSize(mAspectRatio.x * mBackgroundTexture.getWidth(),
				mAspectRatio.y * mBackgroundTexture.getHeight());
		mMainScene.attachChild(mBackground);
		mCannonButton1 = new Button((float) (mCameraWidth / 20 * 5),
				(float) (0),
				mCannonButton1Texture.deepCopy());
		
		mMainScene.attachChild(mCannonButton1);
		mGoldLabel = new ChangeableText(0, 0, mGoldFont, "Gold: " + mGold);
		mMainScene.attachChild(mGoldLabel);
		mHealthLabel = new ChangeableText(0, mCameraHeight / 12 * 1,
				mHealthFont, "Health: " + mHealth);
		mPauseLabel = new ChangeableText(0, 0, mPauseFont, "Game Paused");
		mPauseLabel.setPosition(mCameraWidth / 2 - mPauseLabel.getWidthScaled()
				/ 2, mCameraHeight / 2 - mPauseLabel.getHeightScaled() / 2);
		mMainScene.attachChild(mHealthLabel);
		mTower = new Sprite(0, (float) (mCameraHeight / 12 * 6.5),
				mTowerTexture);
		mTower.setSize(mAspectRatio.x * mTowerTexture.getWidth(),
				mAspectRatio.y * mTowerTexture.getHeight());
		mMainScene.attachChild(mTower);

		mPauseMenu = new Sprite(mCameraWidth / 2
				- (mPauseMenuTexture.getWidth() / 2 * mAspectRatio.x),
				mCameraHeight / 2
						- (mPauseMenuTexture.getHeight() / 2 * mAspectRatio.y),
				mPauseMenuTexture);
		mPauseMenu.setSize(mAspectRatio.x * mPauseMenuTexture.getWidth(),
				mAspectRatio.y * mPauseMenuTexture.getHeight());

		timer();

		return mMainScene;
	}

	private void timer() {
		mGameTimer = new TimerHandler(TIMERDELAY, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {

				if (!isPaused() && !isGameOver()) {
					SpawnEnemies();
					RemoveSprites();
					UpdateSprites();
				}

				else {

				}
			}

		});
		getEngine().registerUpdateHandler(mGameTimer);
	}

	private boolean isGameOver() {

		return mGameOver;
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		if (arg1.getAction() == TouchEvent.ACTION_DOWN) {
			float tempX = arg1.getX();
			float tempY = arg1.getY();
			Log.v("MainGame", "TouchListener received input.");

			if (!isPaused() && !isGameOver()) {
				BuyCannon(tempX, tempY);
			} else {

			}

			return true;
		}
		return false;
	}

	private void UpdateSprites() {

		for (int i = 0; i < mEnemies.size(); i++) {
			if (mEnemies.get(i).isEnemyActive()) {
				mEnemies.get(i).move();
			}
		}

		for (int i = 0; i < mDefenses.size(); i++) {

			Enemy e = getTargetFromInRange(FindInRange(mDefenses.get(i)));
			if (e != null) {
				float pTempAngle = getAngle(e, mDefenses.get(i));
				mDefenses.get(i).Rotate(pTempAngle + (float) Math.PI / 2);
				mDefenses.get(i).Fire(pTempAngle);
			}
		}

		for (int i = 0; i < mBullets.size(); i++) {
			if (mBullets.get(i).isBulletActive()) {
				mBullets.get(i).move();

			}
			for (int j = 0; j < mEnemies.size(); j++) {
				if (mBullets.get(i).collidesWith(mEnemies.get(j))
						&& mEnemies.get(j).isEnemyActive()
						&& mBullets.get(i).isBulletActive()) {
					mEnemies.get(j).TakeDamage(
							mBullets.get(i).getDefenseDamageOutput());
					mBullets.get(i).hit();

				}
			}
		}

	}

	private void RemoveSprites() {
		for (int i = 0; i < mEnemies.size(); i++) {
			if (mEnemies.get(i).getX() < mCameraWidth / 20 * 1) {
				if (mEnemies.get(i).isEnemyActive()) {

					this.LoseTowerHealth(mEnemies.get(i).getEnemyStrength());
					mEnemies.get(i).toggleEnemyActive();
					mMainScene.detachChild(mEnemies.get(i));
				}

			}

		}
		for (int i = 0; i < mBullets.size(); i++) {
			if (!mBullets.get(i).isBulletActive()) {

				mMainScene.detachChild(mBullets.get(i));

			}
		}
	}

	private void LoseTowerHealth(float enemyStrength) {
		mHealth -= enemyStrength;
		if (mHealth <= 0) {
			mHealth = 0;
		}
		mHealthLabel.setText("Health: " + mHealth);
		if (mHealth == 0) {
			toggleGameOver();
		}

	}

	private void toggleGameOver() {
		mGameOver = true;
		mPauseLabel.setText("GAME OVER");
		mMainScene.attachChild(mPauseMenu);
		mMainScene.attachChild(mPauseLabel);

	}

	public float getAngle(Enemy pEnemy, Sprite pSprite) {
		float pX = pEnemy.getX() + pEnemy.getWidth() / 2 - pSprite.getX()
				+ pSprite.getWidth() / 2;
		float pY = pEnemy.getY() + pEnemy.getHeight() / 2 - pSprite.getY()
				+ pSprite.getHeight() / 2;
		float pH = (float) Math.acos(pX / getDistance(pEnemy, pSprite));

		if (pY > 0) {
			return pH;
		} else {
			return -pH;

		}

	}

	public float getDistance(Enemy pEnemy, Sprite pSprite) {

		float pX = pEnemy.getX() + pEnemy.getWidth() / 2 - pSprite.getX()
				+ pSprite.getWidth() / 2;
		float pY = pEnemy.getY() + pEnemy.getHeight() / 2 - pSprite.getY()
				+ pSprite.getHeight() / 2;
		float pH = (float) Math.sqrt(pX * pX + pY * pY);
		return pH;

	}

	private void SpawnEnemies() {
		if (mSpawnCounter > mSpawnDelay && mEnemySpawn < mNumEnemies) {
			Enemy e = new Zombie((float) (mCameraWidth / 20 * 19.5),
					(float) (mCameraHeight / 12 * 6.5),
					mZombieTexture.deepCopy());
			e.animate(300);
			mMainScene.attachChild(e);

			mSpawnCounter = 0;
			mEnemySpawn++;
		}
		mSpawnCounter++;
	}

	private boolean isPaused() {
		return mPause;

	}

	private void togglePause() {
		if (!isGameOver()) {
			if (!isPaused()) {
				mMainScene.attachChild(mPauseMenu);
				mMainScene.attachChild(mPauseLabel);

			} else {
				mMainScene.detachChild(mPauseMenu);
				mMainScene.detachChild(mPauseLabel);
			}

			mPause = !mPause;
		}
	}

	@Override
	public void onBackPressed() {
		togglePause();
	}

	private Enemy getTargetFromInRange(ArrayList<Enemy> pInRange) {
		int pClosestIndex = -1;
		for (int i = 0; i < pInRange.size(); i++) {
			if (pClosestIndex == -1) {
				pClosestIndex = i;
			}

			else if (mEnemies.get(i).getX() < mEnemies.get(pClosestIndex)
					.getX()) {
				pClosestIndex = i;
			}
		}
		if (pClosestIndex == -1) {
			return null;
		} else {
			return pInRange.get(pClosestIndex);
		}
	}

	private ArrayList<Enemy> FindInRange(Defense pDefense) {
		float pRange = pDefense.getDefenseRange();
		ArrayList<Enemy> pInRange = new ArrayList<Enemy>();
		for (int i = 0; i < mEnemies.size(); i++) {
			if (mEnemies.get(i).isEnemyActive()) {
				if (getDistance(mEnemies.get(i), pDefense) < pRange) {
					pInRange.add(mEnemies.get(i));
				}
			}
		}
		return pInRange;

	}
	
	public void BuyCannon(float pX, float pY) {
		if(mCannonButton1.isPressed()) {
			GridBuild(pX, pY);
			mCannonButton1.unpress();
			
				
		}
		
		else {
			if(mCannonButton1.contains(pX, pY) && mGold >= CANNONCOST) {
				mCannonButton1.press();
				mGold -= CANNONCOST;
				mGoldLabel.setText("Gold: " + mGold); 
					
				
			}
		}
	}
	
	private void GridBuild(float pX, float pY) {
		int ptempX = (int) (pX / 20);
		int ptempY = (int) (pY / 12);
		Defense d = new Cannon(ptempX * 20, ptempY * 12, mCannonTexture);
		
		mMainScene.attachChild(d);
	}

}
