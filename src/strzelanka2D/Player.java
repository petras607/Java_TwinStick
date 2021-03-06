package strzelanka2D;

import java.awt.event.KeyEvent;

/**
 * Ta klasa reprezentuje statek gracza. U?ytkownik mo?e sterowa? nim klawiatur?.
 * @author Piotr Pieni??ek
 */
public class Player extends Object implements Commons
{
	private int diameter;	// ?rednica obrazu gracza
	// Pozycja centrum dla wyznaczania kolizji
	//private double center_x;
	//private double center_y;
	private boolean dying;	// Czy gracz zgin??.
	private boolean[] move_direction = {false, false, false, false};	// Lewo, g?ra, prawo, d??
	
	/**
	 * Ten konstruktor inicjalizuje podstawowe parametry dla obiektu gracza.
	 * Odczytuje obraz i bierze z niego ?rednic? (zak?adamy, ?e wczytany obraz jest ko?em).
	 */
	public Player()
	{
		setImageIcon(PLAYER_IMG);	// Wczytujemy sprite
		diameter = getImageIcon().getImage().getWidth(null);	// Pobieramy szeroko?? sprite'a
		mov_speed = 4;
		
		// Gracz pojawia sie na srodku pola gry
		pos_x = BOARD_WIDTH / 2;
		pos_y = BOARD_HEIGHT / 2;
		dying = false;
	}
	
	/**
	 * Ta metoda jest wywo?ywana, gdy obiekt gracza zostaje trafiony.
	 * Deklaruje obiekt gracza jako martwy i rozpoczyna proces ko?ca gry.
	 */
	public void die() { dying = true; }
	
	/**
	 * Ta metoda m?wi czy obiekt gracza zgin??.
	 * @return true, je?li gracz zgin??.
	 */
	public boolean isDead() { return dying; }
	
	/**
	 * Ta metoda odczytuje ?rednic? obiektu.
	 * @return ?rednica
	 */
	public int getDiameter() { return diameter; }
	
	/**
	 * Ta metoda odczytuje koordynat X ?rodka obiektu.
	 * @return Koordynat X ?rodka obiektu.
	 */
	public int getCenterX() { return (int) Math.round(pos_x + diameter / 2);}
	
	/**
	 * Ta metoda odczytuje koordynat Y ?rodka obiektu.
	 * @return Koordynat Y ?rodka obiektu.
	 */
	public int getCenterY() { return (int) Math.round(pos_y + diameter / 2);}
	
	/**
	 * Ta metoda zmienia pozycj? obiektu zgodnie z kierunkiem ustalanym przez metody keyPressed i keyReleased.
	 */
	public void move()
	{
		// Ustawianie ruchu
		if (move_direction[0])
			mov_x = -mov_speed;
		else if (move_direction[2])
			mov_x = mov_speed;
		else
			mov_x = 0;
		
		if (move_direction[1])
			mov_y = -mov_speed;
		else if (move_direction[3])
			mov_y = mov_speed;
		else
			mov_y = 0;
		
		// Wspolrzedna x
		pos_x += mov_x;
		
		if (pos_x < 0)
			pos_x = 0;
		
		
		if (pos_x > BOARD_WIDTH - diameter)
			pos_x = BOARD_WIDTH - diameter;
		
		// Wspolrzedna y
		pos_y += mov_y;
		
		if (pos_y < 0)
			pos_y = 0;
		
		if (pos_y > BOARD_HEIGHT - diameter)
			pos_y = BOARD_HEIGHT - diameter;
	}
	
	/**
	 * Ta metoda ustala czy obiekt koliduje si? z ko?em podanym jako parametry.
	 * @param x Pozycja X ?rodka drugiego ko?a.
	 * @param y Pozycja Y ?rodka drugiego ko?a.
	 * @param diameter ?rednica drugiego ko?a.
	 * @return true, je?li dystans pomi?dzy ?rodkami k?? jest kr?tszy od sumy promieni k??.
	 */
	public boolean isCollided(int x, int y, int diameter)
	{
		double center_x = pos_x + diameter / 2;
		double center_y = pos_y + diameter / 2;
		
		// Je?li odleg?o?? pomi?dzy ?rodkami ko?a jest kr?tsza od sumy promieni, ko?a si? koliduj?
		if ((center_x - x) * (center_x - x) + (center_y - y) * (center_y - y) < (this.diameter / 2 + diameter / 2) * (this.diameter / 2 + diameter / 2))
			return true;
		else
			return false;
	}
	
	/**
	 * Ta metoda odczytuje wci?ni?ty klawisz i ustala kierunek ruchu zgodnie z wci?ni?tym klawiszem.
	 * <p>
	 * A i lewa strza?ka rusza obiektem w lewo, D i prawa strza?ka rusza obiektem w prawo,
	 * W i g?rna strza?ka rusza obiektem w g?r?, S i dolna strza?ka rusza obiektem w d??.
	 * @param e Obiekt klasy KeyEvent, z kt?rego jest odczytywany wci?ni?ty klawisz.
	 */
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		// Lewo
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			move_direction[0] = true;
		
		// Prawo
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			move_direction[2] = true;
		
		// G?ra
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			move_direction[1] = true;
		
		// D??
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
			move_direction[3] = true;
	}
	
	/**
	 * Ta metoda odczytuje puszczony klawisz.
	 * <p>
	 * A i lewa strza?ka ko?czy ruch w lewo, D i prawa strza?ka ko?czy ruch w prawo,
	 * W i g?rna strza?ka ko?czy ruch w g?r?, S i dolna strza?ka ko?czy ruch w d??.
	 * @param e Obiekt klasy KeyEvent, z kt?rego jest odczytywany puszczony klawisz.
	 */
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		// Lewo
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			move_direction[0] = false;
		
		// Prawo
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			move_direction[2] = false;
		
		// G?ra
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			move_direction[1] = false;
		
		// D??
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
			move_direction[3] = false;
	}
}
