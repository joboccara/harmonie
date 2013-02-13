import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Clavier extends JPanel{
	
	private static int interstice = 2;
	private static int contour = 2;
	private static int tailleBlanche = 65;
	private static int hauteurActivationBlanche = 140;
	private static double proportionHauteurNoire = 0.6;
	private static int nbOctaves = 2;
	private static int nbNotes = 12 * nbOctaves;
	public static int taille = 7 * tailleBlanche * nbOctaves;
	
	private boolean[] notesActives = new boolean[nbNotes];
	private int[] mappingBlanchesNotes = new int[7];
	private int[] mappingNoiresNotes = new int[5];
    private static final Map<String, Integer> mappingNomsNotes = new HashMap<String, Integer>();

	Clavier(){
		reset();
		
		mappingBlanchesNotes[0] = 0;
		mappingNoiresNotes[0]   = 1;
		mappingBlanchesNotes[1] = 2;
		mappingNoiresNotes[1]   = 3;
		mappingBlanchesNotes[2] = 4;
		mappingBlanchesNotes[3] = 5;
		mappingNoiresNotes[2]   = 6;
		mappingBlanchesNotes[4] = 7;
		mappingNoiresNotes[3]   = 8;
		mappingBlanchesNotes[5] = 9;
		mappingNoiresNotes[4]   = 10;
		mappingBlanchesNotes[6] = 11;
		
		mappingNomsNotes.put("Do", 0);
		mappingNomsNotes.put("Do#", 1);
		mappingNomsNotes.put("Reb", 1);
		mappingNomsNotes.put("Re", 2);
		mappingNomsNotes.put("Re#", 3);
		mappingNomsNotes.put("Mib", 3);
		mappingNomsNotes.put("Mi", 4);
		mappingNomsNotes.put("Fa", 5);
		mappingNomsNotes.put("Fa#", 6);
		mappingNomsNotes.put("Solb", 6);
		mappingNomsNotes.put("Sol", 7);
		mappingNomsNotes.put("Sol#", 8);
		mappingNomsNotes.put("Lab", 8);
		mappingNomsNotes.put("La", 9);
		mappingNomsNotes.put("La#", 10);
		mappingNomsNotes.put("Sib", 10);
		mappingNomsNotes.put("Si", 11);
	}

	public void reset(){
		for (int i = 0; i < 12 * nbOctaves; ++i){
			notesActives[i] = false;
		}
	}
	
	public void activate(ArrayList<String> noms){
		int notePrecedente = -1;
		for (int i = 0; i < noms.size(); ++i){
			int note = mappingNomsNotes.get(noms.get(i));
			while (note < notePrecedente)
				note += 12;
			notesActives[note] = true;
			notePrecedente = note;
		}
		this.repaint();
	}
	
	private enum COULEUR_TOUCHE{
		BLANCHE,
		NOIRE
	}
	
	private int indexNote(int octave, int touche, COULEUR_TOUCHE couleur){
		int note = 0;
		if (couleur == COULEUR_TOUCHE.BLANCHE){
			note = mappingBlanchesNotes[touche];
		} else if (couleur == COULEUR_TOUCHE.NOIRE){
			note = mappingNoiresNotes[touche];
		}
		return 12 * octave + note;
	}
	
	public void paintComponent(Graphics g){
		int tailleOctave = 7 * tailleBlanche;
		for (int iOctave = 0; iOctave < 2; ++iOctave){
			// touches blanches
			for (int i = 0; i < 7; ++i){
				g.setColor(Color.BLACK); // contour
				g.fillRect(iOctave * tailleOctave + interstice/2 + i * tailleBlanche, 0, tailleBlanche - interstice, this.getHeight());
				g.setColor(Color.WHITE); // touche
				g.fillRect(contour + iOctave * tailleOctave + interstice/2 + i * tailleBlanche, contour, tailleBlanche - interstice - 2*contour, this.getHeight() - 2*contour);
				if (notesActives[indexNote(iOctave, i, COULEUR_TOUCHE.BLANCHE)]){
					g.setColor(Color.RED); // touche active
					g.fillRect(contour + iOctave * tailleOctave + interstice/2 + i * tailleBlanche, contour + this.getHeight() - hauteurActivationBlanche, tailleBlanche - interstice - 2*contour, hauteurActivationBlanche - 2*contour);
				}
			}
			// touches noires
			for (int i = 0; i < 6; ++i){
				if (i != 2){
					g.setColor(Color.BLACK); // contour
					g.fillRect(iOctave * tailleOctave + tailleBlanche*3/4 + tailleBlanche * i, 0, tailleBlanche/2, (int) (this.getHeight() * proportionHauteurNoire));
					if (notesActives[indexNote(iOctave, i<2 ? i : i-1, COULEUR_TOUCHE.NOIRE)]){
						g.setColor(Color.RED); // touche active
					}
					g.fillRect(contour + iOctave * tailleOctave + tailleBlanche*3/4 + tailleBlanche * i, contour, tailleBlanche/2 - 2*contour, (int) (this.getHeight() * proportionHauteurNoire - 2*contour));
				}
			}
		}
	}
}
