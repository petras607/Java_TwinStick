package strzelanka2D;

import javax.swing.JFrame;

/**
 * Program Strzelanka zawiera aplikacj� wy�wietlaj�c�
 * grafik� i obliczaj�c� logik� dla prostej strzelanki
 * dwuwymiarowej.
 * 
 * Zasady gry: Statek gracza pojawia si� na �rodku ekranu. Gracz mo�e
 * sterowa� statkiem strza�kami lub przyciskami W, S, A, D, strzela� w kierunku
 * wskazywanym przez myszk� wciskaj�c LPM i detonowa� bomb� niszcz�c� wrog�w
 * wciskaj�c PPM. Wrogowie pojawiaj� si� w losowych miejscach oraz zaczynaj� strzela�
 * i rusza� si� w kierunku statku gracza. Je�li graczowi sko�cz� si� bomby, nast�pny wr�g
 * b�dzie specjalny - ustrzelenie go przywr�ci pocz�tkowy zapas bomb. Celem gry
 * jest zestrzelenie jak najwi�kszej ilo�ci wrog�w. Gra ko�czy si� kiedy gracz zostaje trafiony. 
 * 
 * @author Piotr Pieni��ek
 * @version 1.1
 * @since 13.06.2018
 */

public class Strzelanka extends JFrame
{
	private static final long serialVersionUID = 1L;

	/**
	 * Ten konstruktor tworzy obiekt klasy Board, gdzie b�d�
	 * wy�wietlane obiekty na ekranie i b�dzie przeprowadzana
	 * wi�kszo�� oblicze�.
	 */
	public Strzelanka()
	{
		Board board = new Board();
		add(board);
		setResizable(false);
		pack();
		board.start();
		
		setTitle("Strzelanka dwuwymiarowa");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Od tej metody zaczyna si� program. Tworzy
	 * i pokazuje nowe okno.
	 * @param args Nieu�ywany
	 */
	public static void main(String[] args)
	{
		Strzelanka game = new Strzelanka();
		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}

}
