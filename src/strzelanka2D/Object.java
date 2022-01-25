package strzelanka2D;

import javax.swing.ImageIcon;

/**
 * Ta klasa definiuje wspólne zmienne i metody dla klas Player, Shot i Enemy
 * @author Piotr Pieni¹¿ek
 */
public class Object
{
	private boolean visible;	// Widzialnoœæ obiektu
	private ImageIcon image;	// Obraz u¿ywany dla obiektu
	// Obecna pozycja obiektu
	protected double pos_x;
	protected double pos_y;
	// Wektor ruchu dla obiektu
	protected int mov_x;	
	protected int mov_y;
	protected int mov_speed;	// Predkosc obiektu
	
	/**
	 * Ta metoda zmienia widzialnoœæ obiektu
	 * @param vis Docelowa widzialnoœæ obiektu
	 */
	public void setVisible(boolean vis) { visible = vis; }
	
	/**
	 * Ta metoda odczytuje obecn¹ widzialnoœæ obiektu
	 * @return Widzialnoœæ obiektu.
	 */
	public boolean isVisible() { return visible; }
	
	/**
	 * Ta metoda ustawia obraz, który bêdzie reprezentowaæ ten obiekt na ekranie
	 * poprzez odczytywanie obrazu z podanej œcie¿ki.
	 * @param image Œcie¿ka do obrazu.
	 */
	public void setImageIcon(String image) { this.image = new ImageIcon(image); }
	
	/**
	 * Ta metoda zwraca obraz reprezentuj¹cy dany obiekt.
	 * @return Obraz (ImageIcon)
	 */
	public ImageIcon getImageIcon() { return image; }
	
	/**
	 * Ta metoda ustawia koordynat X dla tego obiektu.
	 * @param x Nowy koordynat X.
	 */
	public void setX(int x) { this.pos_x = x; }

	/**
	 * Ta metoda ustawia koordynat Y dla tego obiektu.
	 * @param y Nowy koordynat Y.
	 */
    public void setY(int y) { this.pos_y = y; }

    /**
     * Ta metoda odczytuje koordynat X dla tego obiektu.
     * @return Obecny koordynat X.
     */
    public int getX() { return (int) Math.round(pos_x); }
    
    /**
     * Ta metoda odczytuje koordynat Y dla tego obiektu.
     * @return Obecny koordynat Y.
     */
    public int getY() { return (int) Math.round(pos_y); }
}
