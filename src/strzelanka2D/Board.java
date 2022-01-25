package strzelanka2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Ta klasa wy�wietla obiekty w swoim JPanelu, animuje obiekty w osobnym w�tku,
 * odczytuje wci�ni�ty klawisz na klawiaturze, pozycj� myszki i wci�ni�ty przycisk myszki
 * i co okre�lon� ilo�� czasu uruchamia krok logiki gry.
 */
public class Board extends JPanel implements Runnable, MouseListener, MouseMotionListener, Commons
{
	private static final long serialVersionUID = 1L;
	private boolean is_started;	// Czy rozgrywka si� zacz�a
	private boolean is_paused;	// Czy zapauzowano gr�
	private boolean is_finished;
	private boolean is_mouse_pressed = false;
	private int score = 0;	// Punktacja
	// Pozycja myszy
	private int mouse_pos_x;
	private int mouse_pos_y;
	// Obiekty u�ywanych klas
	private Player player;
	private Enemy[] enemy;
	private Shot[] player_shot;
	private Thread animator;	// W�tek dla animacji
	private boolean is_player_shot_created;	// Czy stworzono strza� gracza
	private int shot_delay;	// Op�znienie po strzale
	private boolean is_player_just_spawned;
	private int spawn_delay;
	private boolean is_bomb_detonated;
	private int bombs;	// Zapas bomb
	private boolean spawn_special = false;	// Czy nast�pny wr�g do zrobienia jest specjalnym wrogiem
	
	/**
	 * Ten konstruktor konfiguruje okno, w��cza odczyt klawiatury i myszy
	 * dla tego JPanelu oraz uruchamia metod� rozpocz�cia gry.
	 */
	public Board()
	{
		setFocusable(true);
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setBackground(Color.white);
		
		// W��czamy odczyt
		addKeyListener(new KeyboardControl());
		addMouseListener(this);
		addMouseMotionListener(this);
		
		start();
		setDoubleBuffered(true);
	}

	/**
	 * Ta metoda ustawia wszystkie zmienne i obiekty niezb�dne
	 * do uruchomienia gry oraz tworzy w�tek dla obliczania logiki.
	 */
	public void start()
	{
		// Tworzymy gracza
		player = new Player();
		
		// Tworzymy wrog�w i ich strza�y
		enemy = new Enemy[ENEMY_COUNT];
		
		for (int i = 0; i < ENEMY_COUNT; i++)
		{
			enemy[i] = new Enemy();
			enemy[i].setVisible(false);
		}
		
		// Tworzymy strza�y gracza
		player_shot = new Shot[PLAYER_SHOT_COUNT];
		
		for (int i = 0; i < PLAYER_SHOT_COUNT; i++)
		{
			player_shot[i] = new Shot();
			player_shot[i].setVisible(false);
		}
		
		// Zmienne i flagi do implementacji op�znie�
		is_player_shot_created = false;
		shot_delay = 0;
		is_player_just_spawned = false;
		spawn_delay = 0;
		is_bomb_detonated = false;
		bombs = START_BOMBS;
		
		score = 0;
		
		is_started = false;
		is_paused = false;
		is_finished = false;
		
		// Tworzymy w�tek je�li nie istnieje
		if (animator == null)
		{
			animator = new Thread(this);
			animator.start();
		}
	}
	
	/**
	 * Ta metoda wy�wietla obiekt gracza na ekranie
	 * @param g - Obiekt klasy Graphics, na kt�rym mamy wy�wietli� dany obiekt.
	 */
	public void drawPlayer(Graphics g)
	{
		if (player.isVisible())
			player.getImageIcon().paintIcon(this, g, player.getX(), player.getY());
		
	}
	
	/**
	 * Ta metoda wy�wietla obiekty wrog�w na ekranie
	 * @param g - Obiekt klasy Graphics, na kt�rym mamy wy�wietli� dany obiekt.
	 */
	public void drawEnemies(Graphics g)
	{
		for (int i = 0; i < ENEMY_COUNT; i++)
		{
			if (enemy[i].isVisible())
				enemy[i].getImageIcon().paintIcon(this, g, enemy[i].getX(), enemy[i].getY());
			
		}
	}
	
	/**
	 * Ta metoda obraca i wy�wietla obiekty strza��w gracza na ekranie
	 * @param g - Obiekt klasy Graphics, na kt�rym mamy wy�wietli� dany obiekt.
	 */
	public void drawPlayerShots(Graphics2D g)
	{
		for (int i = 0; i < PLAYER_SHOT_COUNT; i++)
		{
			if (player_shot[i].isVisible())
			{
				AffineTransform old = g.getTransform();
				
				g.rotate(player_shot[i].getRotation(), player_shot[i].getX() + player_shot[i].getDiameter() / 2, player_shot[i].getY() + player_shot[i].getDiameter() / 2);
				player_shot[i].getImageIcon().paintIcon(this, g, player_shot[i].getX(), player_shot[i].getY());
				
				g.setTransform(old);
			}	
		}	
	}
	
	/**
	 * Ta metoda obraca i wy�wietla obiekty strza��w wrog�w na ekranie
	 * @param g - Obiekt klasy Graphics, na kt�rym mamy wy�wietli� dany obiekt.
	 */
	public void drawEnemyShots(Graphics2D g)
	{
		for (int i = 0; i < ENEMY_COUNT; i++)
		{
			if (enemy[i].isVisible() && enemy[i].shot.isVisible())
			{
				AffineTransform old = g.getTransform();
				
				g.rotate(enemy[i].shot.getRotation(), enemy[i].shot.getX() + enemy[i].shot.getDiameter() / 2, enemy[i].shot.getY() + enemy[i].shot.getDiameter() / 2);
				enemy[i].shot.getImageIcon().paintIcon(this, g, enemy[i].shot.getX(), enemy[i].shot.getY());
				
				g.setTransform(old);
			}
		}
	}
	
	/**
	 * Ta metoda wy�wietla interfejs gry na ekranie
	 * @param g - Obiekt klasy Graphics, na kt�rym mamy wy�wietli� dany obiekt.
	 */
	public void drawUI(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(20.0f));
		g.drawString("Bombs: " + bombs + ", Score: " + score, 20, 20);
		
		if (is_paused)
		{
			g.setColor(Color.RED);
			g.setFont(g.getFont().deriveFont(20.0f));
			g.drawString("PAUSED", BOARD_WIDTH / 2, 20);
		}
	}
	
	/**
	 * Ta metoda wy�wietla ekran pocz�tkowy programu
	 * @param g - Obiekt klasy Graphics, na kt�rym mamy wy�wietli� dany obiekt.
	 */
	public void titleScreen(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(40.0f));
		g.drawString("PRESS ANY BUTTON TO START", 190 , BOARD_HEIGHT / 2);
	}
	
	/**
	 * Ta metoda wy�wietla ekran ko�ca gry
	 * @param g - Obiekt klasy Graphics, na kt�rym mamy wy�wietli� dany obiekt.
	 */
	public void gameOver(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(40.0f));
		g.drawString("GAME OVER", 190 , BOARD_HEIGHT / 2);
		g.drawString("Final score: " + score, 190, BOARD_HEIGHT / 2 + 50);
		g.setFont(g.getFont().deriveFont(30.0f));
		g.drawString("Press any key to restart", 190, BOARD_HEIGHT / 2 + 100);
	}
	
	/**
	 * Ta metoda wy�wietla wymagane obiekty na ekranie
	 * @param g - Obiekt klasy Graphics, na kt�rym mamy wy�wietli� dany obiekt.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if (!is_started)
			titleScreen(g);
		else if (!is_finished)
		{
			drawPlayer(g);
			drawEnemies(g);
			drawPlayerShots(g2d);
			drawEnemyShots(g2d);
			drawUI(g);
		}
		else
			gameOver(g);
	}
	
	/**
	 * Ta metoda oblicza ca�y krok logiki gry.
	 * <p>
	 * Najpierw rozpoczyna dzia�anie obiektu gracza w razie potrzeby i odczytuje wci�ni�ty klawisz, �eby nim ruszy�.
	 * Je�li koliduje z wrogiem lub jego strza�em, obiekt gracza ginie i gra si� ko�czy
	 * <p>
	 * Potem gracz, je�li mo�e i ma wci�ni�ty LPM, wykonuje strza�. Je�li ten stra� koliduje si� z wrogiem,
	 * niszczymy wroga i jego strza�, a gracz zdobywa punkty. Je�li ustrzelony wr�g jest specjalny, gracz otrzymuje pocz�tkowy
	 * zapas bomb. Je�li gracz wcisn�� PPM, detonuje jedn� z posiadanych bomb, kt�ra niszczy wszystkich wrog�w
	 * i ich strza�y, zdobywaj�c punkty.
	 * <p>
	 * Wr�g mo�e si� pojawi� je�li ilo�� wrog�w nie przekracza limitu. Je�li gracz nie ma bomb, ustaw nast�pnego wroga jako
	 * specjalnego. Je�li wr�g istnieje, mo�e wykona� strza�.
	 */
	public void gameCycle()
	{
		if (!player.isVisible())
		{
			player.spawn();
			is_player_just_spawned = true;
		}
		
		if (is_player_just_spawned)
		{
			if (spawn_delay < MERCY_DELAY)
				spawn_delay += 1;
			else
			{
				is_player_just_spawned = false;
				spawn_delay = 0;
			}
		}
		
		// Gracz
		player.move();
		
		// Je�li gracz koliduje z wrogiem albo jego strza�em, gracz ginie
		for (int i = 0; i < ENEMY_COUNT; i++)
		{
			if (enemy[i].isVisible())
			{
				if (enemy[i].isCollided(player.getCenterX(), player.getCenterY(), player.getDiameter()))
					player.die();
			}
			
			if (enemy[i].shot.isVisible())
			{
				if (player.isCollided(enemy[i].shot.getCenterX(), enemy[i].shot.getCenterY(), enemy[i].shot.getDiameter()))
					player.die();
			}
		}
		
		// Strza� gracza		
		for (int i = 0; i < PLAYER_SHOT_COUNT; i++)
		{
			// Tworzymy je�li mo�emy
			if (!player_shot[i].isVisible() && is_mouse_pressed == true && is_player_shot_created == false)
			{
				player_shot[i] = new Shot(player.getX() + player.getDiameter() / 2, player.getY() + player.getDiameter() / 2, mouse_pos_x, mouse_pos_y, PLAYER_SHOT_SPEED, PLAYER_SHOT_IMG);
				is_player_shot_created = true;
			}
			
			if (player_shot[i].isVisible())
			{
				// Je�li przekracza granice okna, niszczymy
				if (player_shot[i].getX() < 0 || player_shot[i].getX() > BOARD_WIDTH || player_shot[i].getY() < 0 || player_shot[i].getY() > BOARD_HEIGHT)
					player_shot[i].stop();
				
				
				// Je�li koliduje si� z wrogiem, niszczymy strza�y i wroga oraz dodajemy punkt.
				for (int j = 0; j < ENEMY_COUNT; j++)
				{
					if (enemy[j].isVisible())
					{
						if (enemy[j].isCollided(player_shot[i].getCenterX(), player_shot[i].getCenterY(), player_shot[i].getDiameter()))
						{
							enemy[j].setVisible(false);
							player_shot[i].stop();
							enemy[j].shot.stop();
							score += 10;
							
							// Je�li ten wr�g jest specjalny, przywr�� pocz�tkowy zapas bomb.
							if (enemy[j].getSpecial())
							{
								bombs = START_BOMBS;
								enemy[j].setSpecial(false);
							}
						}
					}
				}
				
				// Ruszamy
				player_shot[i].move();
			}
		}
		
		// Je�li wystrzelono, zaczynamy op�znienie. Kiedy ko�czymy op�znienie, mozna znowu strzela�.
		if (is_player_shot_created)
		{
			if (shot_delay < PLAYER_SHOT_DELAY)
				shot_delay += 1;
			else
			{
				is_player_shot_created = false;
				shot_delay = 0;
			}
		}
		
		// Wrogowie, detonacja bomby
		for (int i = 0; i < ENEMY_COUNT; i++)
		{
			// Je�li wr�g w danym slocie nie istnieje, jest szansa, �e si� pojawi
			if (!enemy[i].isVisible() && !is_player_just_spawned && !is_bomb_detonated)
			{
				Random generator = new Random();
				
				if (generator.nextInt(100) <= ENEMY_SPAWN_CHANCE)
				{
					enemy[i].spawn(player.getCenterX(), player.getCenterY());
					
					if (spawn_special)
					{
						enemy[i].setSpecial(true);
						spawn_special = false;
					}
					
					is_player_just_spawned = true;
				}
			}
			
			if (enemy[i].isVisible())
			{
				// Je�li zdetonowano bomb�, zniszcz wszystkich wrog�w i ich strza�y oraz przyznaj punkty.
				if (is_bomb_detonated)
				{
					enemy[i].setVisible(false);
					enemy[i].shot.stop();
					score += 10;
				}
				else
					// Wr�g si� rusza.
					enemy[i].move(player.getCenterX(), player.getCenterY());
			}
		}
		
		// Strza�y wroga
		for (int i = 0; i < ENEMY_COUNT; i++)
		{
			if (!enemy[i].shot.isVisible() && enemy[i].isVisible())
				enemy[i].shoot(player.getCenterX(), player.getCenterY());

			if (enemy[i].shot.isVisible())
			{
				// Je�li przekracza granice okna, niszczymy.
				if (enemy[i].shot.getX() < 0 || enemy[i].shot.getX() > BOARD_WIDTH || enemy[i].shot.getY() < 0 || enemy[i].shot.getY() > BOARD_HEIGHT)
					enemy[i].shot.stop();
				
				// Ruch strza�u wroga.
				enemy[i].shot.move();
			}
		}
		
		// Je�li zdetonowano bomb�, wy��cz flag�.
		is_bomb_detonated = false;
		
		// Je�li gracz ginie, koniec gry.
		if (player.isDead())
		{
			for (int i = 0; i < ENEMY_COUNT; i++)
				enemy[i] = new Enemy();
			
			for (int i = 0; i < PLAYER_SHOT_COUNT; i++)
				player_shot[i].setVisible(false);
			
			player.setVisible(false);
			is_finished = true;
		}
	}	
	
	/**
	 * Ta metoda jest wywo�ywana dla w�tku stworzonego podczas uruchamiania gry.
	 * Wywo�uje metody rysowania obiekt�w na ekranie oraz metod� kroku logiki gry.
	 */
	@Override
	public void run()
	{
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();
		
		while (true)
		{
			repaint();
			
			if (is_started)
			{
				if (!is_paused)
					gameCycle();
			}
			
	        timeDiff = System.currentTimeMillis() - beforeTime;
	        sleep = DELAY - timeDiff;

	        if (sleep < 0)
	        	sleep = 2;

	        try
	        {       	
	        	Thread.sleep(sleep);
	        }
	        catch (InterruptedException e)
	        {
	        	System.out.println("interrupted");
	        }
	            
	        beforeTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * Ta metoda jest wywo�ywana po wci�ni�ciu przycisku myszy.
	 * Rozpoczyna gr� je�li nie jest rozpocz�ta i zaczyna tworzenie strza��w w kierunku
	 * wskazywanym przez kursor po wci�ni�ciu LPM lub detonuje bomb� po wci�ni�ciu PPM.
	 * @param e Obiekt klasy MouseEvent, z kt�rego odczytujemy wci�ni�ty przycisk i pozycj� kursora.
	 */	
	public void mousePressed(MouseEvent e)
	{
		if (!is_started)
			is_started = true;
		
		if (is_finished)
			start();
		
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			mouse_pos_x = e.getX();
			mouse_pos_y = e.getY();
			is_mouse_pressed = true;
		}
		else if (e.getButton() == MouseEvent.BUTTON3)
		{
			if (bombs > 0)
			{
				is_bomb_detonated = true;
				bombs -= 1;
			}
			
			if (bombs == 0)
				spawn_special = true;
		}
	}
	
	/**
	 * Ta metoda jest wywo�ywana, gdy u�ytkownik rusza myszk� jednocze�nie wciskaj�c przycisk myszy.
	 * Aktualizuje pozycj� myszy podczas ci�g�ego strzelania.
	 * @param e Obiekt klasy MouseEvent, z kt�rego odczytujemy pozycj� kursora.
	 */
	public void mouseDragged(MouseEvent e)
	{
		mouse_pos_x = e.getX();
		mouse_pos_y = e.getY();
	}
	
	/**
	 * Ta metoda jest wywo�ywana po puszczeniu przycisku myszy.
	 * Ko�czy tworzenie strza��w.
	 * @param e Obiekt klasy MouseEvent, z kt�rego odczytujemy wci�ni�ty przycisk.
	 */
	public void mouseReleased(MouseEvent e)	{ is_mouse_pressed = false; }
	
	/**
	 * Ta metoda nie robi nic w tym programie. Jej deklaracja jest wymagana przez �rodowisko.
	 * @param e Obiekt klasy MouseEvent
	 */
	public void mouseClicked(MouseEvent e) { }
	
	/**
	 * Ta metoda nie robi nic w tym programie. Jej deklaracja jest wymagana przez �rodowisko.
	 * @param e Obiekt klasy MouseEvent
	 */
	public void mouseEntered(MouseEvent e) { }
	
	/**
	 * Ta metoda nie robi nic w tym programie. Jej deklaracja jest wymagana przez �rodowisko.
	 * @param e Obiekt klasy MouseEvent
	 */
	public void mouseExited(MouseEvent e) { }
	
	/**
	 * Ta metoda nie robi nic w tym programie. Jej deklaracja jest wymagana przez �rodowisko.
	 * @param e Obiekt klasy MouseEvent
	 */
	public void mouseMoved(MouseEvent e) { }
	
	/**
	 * Ta klasa s�u�y do odczytywania wci�ni�tych klawiszy z klawiatury
	 * i wywo�ywania odpowiednich metod oraz ustawiania zmiennych obiektu klasy Board.
	 * @author Piotr Pieni��ek
	 */
	class KeyboardControl extends KeyAdapter
	{
		/**
		 * Ta metoda jest wywo�ywana po wci�ni�ciu klawisza.
		 * Rozpoczyna gr� je�li nie jest rozpocz�ta, resetuje je�li gra si� sko�czy�a,
		 * wywo�uje implementacj� tej metody dla obiektu klasy Player
		 * oraz pauzuje gr� je�li u�ytkownik wcisn�� klawisz P.
		 * @param e Obiekt klasy KeyEvent, z kt�rego odczytywany jest klawisz.
		 */
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (!is_started)
				is_started = true;
			if (is_finished)
				start();
			
			player.keyPressed(e);
			
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_P)
				is_paused = !is_paused;
		}
		
		/**
		 * Ta metoda jest wywo�ywana po puszczeniu klawisza.
		 * Wywo�uje implementacj� tej metody dla obiektu klasy Player.
		 * 
		 * @param e Obiekt klasy KeyEvent, z kt�rego odczytywany jest klawisz.
		 */
		@Override
		public void keyReleased(KeyEvent e) { player.keyReleased(e); }
	}
}
