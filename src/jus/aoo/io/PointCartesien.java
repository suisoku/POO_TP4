package jus.aoo.io;

import java.io.Serializable;

/**
 * Modélise la notion de coordonnée avec une représentation cart�sienne
 * @author P.Morat ou http://imag.fr/Philippe.Morat
 * @version 1.0 date 1/10/00
 * @motcle point, coordonnée, espace, cartésien
 * @see <a href="PointCartesien.java">PointCartesien</a>
 */
public class PointCartesien implements Serializable {
		private static final long serialVersionUID = -4160888760755374930L;
		/** La représentation physique des coordonnées */
    protected double x,y;
    /**
     * Restitue l'abscisse du point
     * @return l'abscisse du point
     */
    public double abscisse() { return x;}
    /**
     * Restitue l'ordonnée du point
     * @return l'ordonnée du point
     */
    public double ordonnee() { return y;}
    /**
     * Construction d'un point à l'origine.
     * @ensure PointASaPlace : abscisse()==0 && ordonnee()==0
     */
    public PointCartesien(){positionner(0.0,0.0);}
    /**
     * Construction d'un point
     * @param abscisse la première coordonnée (abscisse)
     * @param ordonnee la seconde coordonnée (ordonnée)
     * @ensure PointASaPlace : abscisse()==x && ordonnee()==y
     */
    public PointCartesien(double abscisse, double ordonnee) {positionner(abscisse,ordonnee);}
    /**
     * Construction d'un point par clonage
     * @param p le point à cloner
     * @require p existe : p!=null
     * @ensure PointASaPlace : abscisse()==p.abscisse() && ordonnee()==p.ordonnee()
     */
    public PointCartesien(PointCartesien p) {this(p.abscisse(),p.ordonnee());}
    /**
     * Restitue la représentation textuelle du point
     * @return la chaine
     */
    public String toString() {return"PointCartesien{"+x+","+y+"}";}
    /**
     * Positionne le point à la coordonnée cartésienne {x,y}
     * @param abscisse l'abscisse
     * @param ordonnee l'ordonnée
     * @ensure PointASaPlace : abscisse()==x && ordonnee()==y
     */
    public void positionner(double abscisse, double ordonnee) {this.x=abscisse; this.y=ordonnee;}
}