package games.aztecPyramids;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

public class World extends BasicGameState {

	public final static String DIRECTORY_SOUNDS="/sounds/aztecPyramids/";
	public final static String DIRECTORY_MUSICS="/musics/aztecPyramids/";
	public final static String DIRECTORY_IMAGES="/images/aztecPyramids/";

	private static Image aztecPyramid;
	private static Image aztecCalendar;
	private static Image aztecSnake;
	private static Image aztecHead1;
	private static Image aztecHead2;
	private static Image aztecHead3;
	private static Image aztecHead4;
	private static Audio soundMusicBackground;

	private float soundMusicBackgroundPos;

	static {
		aztecPyramid = AppLoader.loadPicture(DIRECTORY_IMAGES+"aztec.jpg");
		aztecCalendar = AppLoader.loadPicture(DIRECTORY_IMAGES+"aztec-calendar.jpg");
		aztecSnake = AppLoader.loadPicture(DIRECTORY_IMAGES+"snake.jpg");
		aztecHead1 = AppLoader.loadPicture(DIRECTORY_IMAGES+"bluehead.png");
		aztecHead2 = AppLoader.loadPicture(DIRECTORY_IMAGES+"pinkhead.png");
		aztecHead3 = AppLoader.loadPicture(DIRECTORY_IMAGES+"yellowhead.png");
		aztecHead4 = AppLoader.loadPicture(DIRECTORY_IMAGES+"greenhead.png");
		soundMusicBackground=AppLoader.loadAudio(DIRECTORY_MUSICS+"Kinski-Song.ogg");
	}

	private int ID;
	private int state;

	private int compteur;
	private Player tabPlay[];

	public World(int ID) {
		this.ID = ID;
		this.state = 0;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game){
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play(container, game);
		} else if (this.state == 2) {
			this.resume(container, game);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause(container, game);
		} else if (this.state == 3) {
			this.stop(container, game);
			this.state = 0; // TODO: remove
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		compteur += delta;

		if (compteur == 5000) {
			for (int j=0;j<this.tabPlay.length;j++){
				this.tabPlay[j].keyEnabled();
			}
		}
		if (compteur == 16500) {
			for (int j=0;j<this.tabPlay.length;j++){
				this.tabPlay[j].keyDisabled();
			}
		}

		if (compteur >= 20000) {
			for (int j=0;j<this.tabPlay.length;j++){
				this.tabPlay[j].valueChange();
				this.tabPlay[j].afterChange();
			}
			for (int j=0;j<this.tabPlay.length;j++){
				for (int i=0;i<this.tabPlay.length;i++){
					if (i!=j){
						this.tabPlay[j].isEqual(this.tabPlay[i]);
					}
			}

		}
			for (int j=0;j<this.tabPlay.length;j++){
				this.tabPlay[j].finalChange();
			}
			for (int j=0;j<this.tabPlay.length;j++){
				this.tabPlay[j].climb();
				compteur =0;
			}

		}
		if (compteur == 1) {
		for (int j=0;j<this.tabPlay.length;j++){
			this.tabPlay[j].isWinner();
			}
		}
		}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		/* Méthode exécutée environ 60 fois par seconde */
		g.drawImage(aztecPyramid,0,0);
		g.drawImage(aztecCalendar,1080,0);
		g.drawImage(aztecSnake,1080,610);
		g.drawString("AZTEC PYRAMIDS",1120,220);
		g.drawString("Score :",1150,250);
		g.drawImage(aztecHead1,500,600-this.tabPlay[0].getFloor()*40);
		g.drawImage(aztecHead2,480,600-this.tabPlay[1].getFloor()*40);
		g.drawString(Integer.toString(this.tabPlay[0].getFloor()),1150,450);
		g.drawString(Integer.toString(compteur),1150,350);
		if (compteur >= 3000 && compteur <= 3500) {
			g.drawString("3",500,250);
		}
		if (compteur >= 3500 && compteur <= 4000) {
			g.drawString("2",500,250);
		}
		if (compteur >= 4000 && compteur <= 4500) {
			g.drawString("1",500,250);
		}
		if (compteur >= 4500 && compteur <= 5000) {
			g.drawString("Choisissez",500,250);
		}
		if (compteur >= 14000 && compteur <= 14500) {
			g.drawString("3",500,250);
		}
		if (compteur >= 14500 && compteur <= 15000) {
			g.drawString("2",500,250);
		}
		if (compteur >= 15000 && compteur <= 15500) {
			g.drawString("1",500,250);
		}
		if (compteur >= 15500 && compteur <= 16000) {
			g.drawString("Terminé",500,250);
		}

	}

	public void play(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		this.compteur = 0;
		this.tabPlay = new Player [] {new Player(this, Input.KEY_LEFT,Input.KEY_UP,Input.KEY_RIGHT),new Player(this, Input.KEY_A,Input.KEY_Z,Input.KEY_E)};
		soundMusicBackground.playAsMusic(1f, 1f, true);
	}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		soundMusicBackgroundPos = soundMusicBackground.getPosition();
		soundMusicBackground.stop();
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		soundMusicBackground.playAsMusic(1, 1f, true);
		soundMusicBackground.setPosition(soundMusicBackgroundPos);
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
		soundMusicBackground.stop();
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	@Override
	public void keyPressed(int key, char c) {
		for (int j=0;j<this.tabPlay.length;j++){
			this.tabPlay[j].keyPressed(key,c);
		}
	}

}
