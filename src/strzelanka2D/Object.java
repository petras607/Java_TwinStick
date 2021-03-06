package strzelanka2D;

import javax.swing.ImageIcon;

/**
 * Ta klasa definiuje wspólne zmienne i metody dla klas Player, Shot i Enemy
 * @author Piotr Pieniążek
 */
public class Object
{
	private ImageIcon image;	// Obraz używany dla obiektu
	// Obecna pozycja obiektu
	protected double pos_x;
	protected double pos_y;
	// Wektor ruchu dla obiektu
	protected double mov_x;	
	protected double mov_y;
	protected int mov_speed;	// Predkosc obiektu
	
	/**
	 * Ta metoda zmienia widzialność obiektu
	 * @param vis Docelowa widzialność obiektu
	 */

	public void setImageIcon(String image) { this.image = new ImageIcon(image); }
	
	/**
	 * Ta metoda zwraca obraz reprezentujący dany obiekt.
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
