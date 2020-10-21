package edu.lewisu.cs.hdondiego;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class TravelByMap extends ApplicationAdapter {
	SpriteBatch batch;
	Texture txMap, txRental, txRestaurant, txMart;	// created four textures to store the images of the map,
													// water rental equipment store, restaurant, and mart
	OrthographicCamera cam;	// orthographic camera object to maneuver the camera view
	int WIDTH;	// the width of the game window
	int HEIGHT;	// the height of the game window
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		// retrieving the images to use for the game
		txMap = new Texture("map.png");
		txMart = new Texture("small_mart.png");
		txRental = new Texture("small_rental.png");
		txRestaurant = new Texture("small_restaurant.png");
		
		// getting the values for the width and the height
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		// setting up the camera
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}
	
	// this function sets a limit to how close the user can zoom in
	public void zoomIn() {
		// the closest the user can get is 0.5 (after the calculation)
		if (cam.zoom >= 0.6) {
			cam.zoom -= 0.1;
		}
	}
	
	public void handleInput() {
		boolean shiftHeld = false;
		boolean cameraNeedsUpdating = false;
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
			shiftHeld = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			if (shiftHeld) {
				//zooming out
				cam.zoom += 0.1;
			} else {
				cam.translate(0,1);
			}
			
			cameraNeedsUpdating = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			if (shiftHeld) {
				//zooming in by calling the function
				zoomIn();
			} else {
				cam.translate(0,-1);
			}
			
			cameraNeedsUpdating = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (shiftHeld) {
				//rotate
				cam.rotate(1);
			} else {
				cam.translate(-1,0);
			}
			
			cameraNeedsUpdating = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (shiftHeld) {
				//rotate
				cam.rotate(-1);
			} else {
				cam.translate(1,0);
			}
			
			cameraNeedsUpdating = true;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (cameraNeedsUpdating) {
			updateCamera();
		}
	}
	
	public void updateCamera() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}
	
	// responsible for changing the order of textures that get drawn in the screen
	// the last one drawn is the primary image that the user can see
	public void updateMap() {
		// checking to see if the camera is zoomed out - "regular" view
		if (cam.zoom > 0.6) {
			// zoomed out
			// can only see the map
			batch.draw(txRestaurant, 0, 0);
			batch.draw(txMart, 0, 0);
			batch.draw(txRental, 0, 0);
			batch.draw(txMap, 0, 0);
		} else {
			// zoomed in
			// cannot see the map
			// all the textures are placed in the locations matching to the map and can be seen when zoomed in close enough
			batch.draw(txMap, 0, 0, 0, 0, 0, 0, 0, 0);
			batch.draw(txMart, 70, 70);
			batch.draw(txRental, 340, 540);
			batch.draw(txRestaurant, 770, 70);
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleInput();
		batch.begin();
		updateMap(); // reflecting changes needed to be made on the screen based on the camera's zoom value
		batch.end();
	}
	
	@Override
	public void dispose () {
		// disposing, or clearing, all the textures when the game is prompted to quit
		batch.dispose();
		txMap.dispose();
		txMart.dispose();
		txRental.dispose();
		txRestaurant.dispose();
	}
}
