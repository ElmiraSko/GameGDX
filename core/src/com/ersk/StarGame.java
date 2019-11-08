package com.ersk;
import com.badlogic.gdx.Game;

import com.ersk.screen.MenuScreen;

public class StarGame extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
