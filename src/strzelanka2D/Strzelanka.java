package strzelanka2D;

import javax.swing.JFrame;

/**
 * Program Strzelanka zawiera aplikacjê wyœwietlaj¹c¹
 * grafikê i obliczaj¹c¹ logikê dla prostej strzelanki
 * dwuwymiarowej.
 * 
 * Zasady gry: Statek gracza pojawia siê na œrodku ekranu. Gracz mo¿e
 * sterowaæ statkiem strza³kami lub przyciskami W, S, A, D, strzelaæ w kierunku
 * wskazywanym przez myszkê wciskaj¹c LPM i detonowaæ bombê niszcz¹c¹ wrogów
 * wciskaj¹c PPM. Wrogowie pojawiaj¹ siê w losowych miejscach oraz zaczynaj¹ strzelaæ
 * i ruszaæ siê w kierunku statku gracza. Jeœli graczowi skoñcz¹ siê bomby, nastêpny wróg
 * bêdzie specjalny - ustrzelenie go przywróci pocz¹tkowy zapas bomb. Celem gry
 * jest zestrzelenie jak najwiêkszej iloœci wrogów. Gra koñczy siê kiedy gracz zostaje trafiony. 
 * 
 * @author Piotr Pieni¹¿ek
 * @version 1.1
 * @since 13.06.2018
 */

public class Strzelanka extends JFrame
{
	private static final long serialVersionUID = 1L;

	/**
	 * Ten konstruktor tworzy obiekt klasy Board, gdzie bêd¹
	 * wyœwietlane obiekty na ekranie i bêdzie przeprowadzana
	 * wiêkszoœæ obliczeñ.
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
	 * Od tej metody zaczyna siê program. Tworzy
	 * i pokazuje nowe okno.
	 * @param args Nieu¿ywany
	 */
	public static void main(String[] args)
	{
		Strzelanka game = new Strzelanka();
		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}

}
