package strzelanka2D;

import java.awt.event.KeyEvent;

/**
 * Ta klasa reprezentuje statek gracza. U¿ytkownik mo¿e sterowaæ nim klawiatur¹.
 * @author Piotr Pieni¹¿ek
 */
public class Player extends Object implements Commons
{
	private int diameter;	// Œrednica obrazu gracza
	// Pozycja centrum dla wyznaczania kolizji
	private double center_x;
	private double center_y;
	private boolean dying;	// Czy gracz zgin¹³.
	
	/**
	 * Ten konstruktor inicjalizuje podstawowe parametry dla obiektu gracza.
	 * Odczytuje obraz i bierze z niego œrednicê (zak³adamy, ¿e wczytany obraz jest ko³em).
	 */
	public Player()
	{
		setImageIcon(PLAYER_IMG);	// Wczytujemy sprite
		diameter = getImageIcon().getImage().getWidth(null);	// Pobieramy szerokoœæ sprite'a
		mov_speed = 4;
		this.setVisible(false);
	}
	
	/**
	 * Ta metoda ustawia gracza na œrodku pola gry i w³¹cza flagê widzialnoœci.
	 */
	public void spawn()
	{
		// Gracz pojawia sie na srodku pola gry
		pos_x = BOARD_WIDTH / 2;
		pos_y = BOARD_HEIGHT / 2;
		center_x = pos_x + diameter / 2;
		center_y = pos_y + diameter / 2;
		dying = false;
		this.setVisible(true);
	}
	
	/**
	 * Ta metoda jest wywo³ywana, gdy obiekt gracza zostaje trafiony.
	 * Deklaruje obiekt gracza jako martwy i rozpoczyna proces koñca gry.
	 */
	public void die() { dying = true; }
	
	/**
	 * Ta metoda mówi czy obiekt gracza zgin¹³.
	 * @return true, jeœli gracz zgin¹³.
	 */
	public boolean isDead() { return dying; }
	
	/**
	 * Ta metoda odczytuje œrednicê obiektu.
	 * @return Œrednica
	 */
	public int getDiameter() { return diameter; }
	
	/**
	 * Ta metoda odczytuje koordynat X œrodka obiektu.
	 * @return Koordynat X œrodka obiektu.
	 */
	public int getCenterX() { return (int) Math.round(center_x);}
	
	/**
	 * Ta metoda odczytuje koordynat Y œrodka obiektu.
	 * @return Koordynat Y œrodka obiektu.
	 */
	public int getCenterY() { return (int) Math.round(center_y);}
	
	/**
	 * Ta metoda zmienia pozycjê obiektu zgodnie z kierunkiem ustalanym przez metody keyPressed i keyReleased.
	 */
	public void move()
	{
		// Wspolrzedna x
		pos_x += mov_x;
		
		if (pos_x < 0)
			pos_x = 0;
		
		
		if (pos_x > BOARD_WIDTH - diameter)
			pos_x = BOARD_WIDTH - diameter;
		
		center_x = pos_x + diameter / 2;
		
		// Wspolrzedna y
		pos_y += mov_y;
		
		if (pos_y < 0)
			pos_y = 0;
		
		if (pos_y > BOARD_HEIGHT - diameter)
			pos_y = BOARD_HEIGHT - diameter;
		
		center_y = pos_y + diameter / 2;
	}
	
	/**
	 * Ta metoda ustala czy obiekt koliduje siê z ko³em podanym jako parametry.
	 * @param x Pozycja X œrodka drugiego ko³a.
	 * @param y Pozycja Y œrodka drugiego ko³a.
	 * @param diameter Œrednica drugiego ko³a.
	 * @return true, jeœli dystans pomiêdzy œrodkami kó³ jest krótszy od sumy promieni kó³.
	 */
	public boolean isCollided(int x, int y, int diameter)
	{
		// Jeœli odleg³oœæ pomiêdzy œrodkami ko³a jest krótsza od sumy promieni, ko³a siê koliduj¹
		if ((center_x - x) * (center_x - x) + (center_y - y) * (center_y - y) < (this.diameter / 2 + diameter / 2) * (this.diameter / 2 + diameter / 2))
			return true;
		else
			return false;
	}
	
	/**
	 * Ta metoda odczytuje wciœniêty klawisz i ustala kierunek ruchu zgodnie z wciœniêtym klawiszem.
	 * <p>
	 * A i lewa strza³ka rusza obiektem w lewo, D i prawa strza³ka rusza obiektem w prawo,
	 * W i górna strza³ka rusza obiektem w górê, S i dolna strza³ka rusza obiektem w dó³.
	 * @param e Obiekt klasy KeyEvent, z którego jest odczytywany wciœniêty klawisz.
	 */
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		// Lewo
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			mov_x = -mov_speed;
		
		// Prawo
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			mov_x = mov_speed;
		
		// Góra
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			mov_y = -mov_speed;
		
		// Dó³
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
			mov_y = mov_speed;
	}
	
	/**
	 * Ta metoda odczytuje puszczony klawisz.
	 * <p>
	 * A i lewa strza³ka koñczy ruch w lewo, D i prawa strza³ka koñczy ruch w prawo,
	 * W i górna strza³ka koñczy ruch w górê, S i dolna strza³ka koñczy ruch w dó³.
	 * @param e Obiekt klasy KeyEvent, z którego jest odczytywany puszczony klawisz.
	 */
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		// Lewo
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			mov_x = 0;
		
		// Prawo
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			mov_x = 0;
		
		// Góra
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			mov_y = 0;
		
		// Dó³
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
			mov_y = 0;
	}
}
