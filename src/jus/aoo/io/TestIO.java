
/*
 * TestIO.java
 *
 * Created on 8 mars 2006, 13:37
 *
 */

package jus.aoo.io;

import javax.swing.*;

import java.beans.PropertyVetoException;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import jus.util.Counter;

/**
 * Ce que vous devez compléter dans ce TP
 * 
 * @author Sebastien Laborie & P.Morat
 */
public class TestIO extends GUI implements Serializable {
    private static final long serialVersionUID = -2287614976419552511L;

    // La date de la sauvegarde
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar time_creation;
    // La liste de points
    private PointCartesienList listeDePoints = new PointCartesienList();
    // la taille du Buffer
    protected static int TAILLEBUFFER = 256 * 1024;
    // le booléen pour inhiber l'affichage
    protected boolean affiche = true;

    // une méthode d'affichage pouvant être inhibée
    protected void warning(String s) {
        if (affiche)
            JOptionPane.showMessageDialog(this, s);
    }

    // un compteur pour quantifier le temps pris par une opération. l'unité sera
    // le dixième de seconde.
    protected Counter counter = new Counter();

    /**
     * Renvoie le texte contenu dans la zone de texte
     * 
     * @return String le contenu de la zone de texte
     */
    protected String getZoneDeTexte() {
        return zoneDeTexte.getText();
    }

    /**
     * Met à jour la zone de texte
     * 
     * @param String
     *            le nouveau contenu à afficher dans la zone de texte
     */
    protected void setZoneDeTexte(String s) {
        zoneDeTexte.setText(s);
    }

    /**
     * Renvoie vrai si il faut bufferiser au chargement
     * 
     * @return boolean vrai si l'option bufferiser est selectionner
     */
    protected boolean bufferiser() {
        return item_Bufferiser.isSelected();
    }

    /**
     * Renvoie vrai si il faut bufferiser les string au chargement
     * 
     * @return boolean vrai si l'option bufferiser est selectionner
     */
    protected boolean stringBufferiser() {
        return item_StringBufferiser.isSelected();
    }

    /**
     * Fixe l'item de bufferisation à la valeur de v
     * 
     * @param v
     *            vrai s'il faut bufferiser, faux sinon
     */
    protected void setBufferiser(boolean v) {
        item_Bufferiser.setSelected(v);
    }

    /**
     * Positionne le sélecteur en fonction de la valeur de b
     * 
     * @param v
     *            le booléen indiquant le bufferisation.
     */
    protected void setStringBufferiser(boolean v) {
        item_StringBufferiser.setSelected(v);
    }

    /**
     * Creates a new instance of TestIO
     */

    public TestIO() {
        super();
        setVisible(true);
        // A COMPLETER
    }

    /**
     * affiche la liste des points cartesiens
     */
    protected void afficherLesPointsCartesiens() {
        StringBuffer str = new StringBuffer(listeDePoints.size() * 60);
        for (int i = 0; i < listeDePoints.size(); i++) {
            str.append("Numero " + (i + 1) + " : " + listeDePoints.get(i) + "\n" + "Date création:"
                    + dateFormat.format(time_creation.getTime()) + "\n");
        }
        setZoneDeTexte(str.toString());
    }

    /**
     * affiche la liste des coordonnées des points
     */
    protected void afficherLesCoordonnees() {
        StringBuffer str = new StringBuffer(listeDePoints.size() * 30);
        for (int i = 0; i < listeDePoints.size(); i++) {
            str.append(listeDePoints.get(i).abscisse() + " " + listeDePoints.get(i).ordonnee() + "\n" + "Date création:"
                    + dateFormat.format(time_creation.getTime()) + "\n");
        }
        setZoneDeTexte(str.toString());
    }

    /**
     * génère une liste de points cartesiens
     * 
     * @param int
     *            le nombre de points cartesiens à générer
     */
    protected void genererListeDePoints(int n) {
        listeDePoints.clear();
        for (int i = 0; i < n; i++) {
            PointCartesien p = new PointCartesien(Math.random() * n, Math.random() * n);
            listeDePoints.add(p);
        }
        time_creation = Calendar.getInstance();
        afficherLesPointsCartesiens();
        warning(listeDePoints.size() + " Points Créés.");
    }

    /**
     * sauve la liste des points cartesiens en format texte
     * 
     * @param File
     *            le fichier de sauvegarde
     */
    protected void sauverFormatTexte(File f) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            fw.write(getZoneDeTexte());
            time_creation = Calendar.getInstance();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * sauve la liste des points cartesiens en format primitif
     * 
     * @param File
     *            le fichier de sauvegarde
     */
    protected void sauverFormatPrimitif(File f) {
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);
            time_creation = Calendar.getInstance();
            for (int i = 0; i < listeDePoints.size(); i++) {
                dos.writeDouble(listeDePoints.get(i).abscisse());
                dos.writeDouble(listeDePoints.get(i).ordonnee());

                System.out.println("Le point de coordonné " + listeDePoints.get(i).abscisse() + " "
                        + listeDePoints.get(i).ordonnee() + " a été ajouté");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            warning("Erreur d'execution : " + e.getMessage());
        }

    }

    /**
     * sauve la liste des points cartesiens en format Objet
     * 
     * @param File
     *            le fichier de sauvegarde
     */
    protected void sauverFormatObjet(File f) {
        try {
            time_creation = Calendar.getInstance();
            ObjectOutputStream stream;
            stream = new ObjectOutputStream(new FileOutputStream(f));
            stream.writeObject(listeDePoints.toString());
            stream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * charge une liste de points cartesiens en format texte
     * 
     * @param File
     *            le fichier à charger
     */
    protected void chargerFormatTexte(File f) {
        Scanner lireDouble = null;
        InputStream Fich;
        try {
            Fich = new FileInputStream(f);
            lireDouble = new Scanner(Fich);
            lireDouble.useLocale(Locale.US);
            int i = 0;
            double tmp = 0.0;
            double tmp2 = 0.0;

            BufferedInputStream BufInStr = new BufferedInputStream(Fich);
            if (bufferiser()) {
                BufInStr.read();
                while (lireDouble.hasNextDouble()) {
                    if (i % 2 == 1 && i != 0) {
                        tmp2 = lireDouble.nextDouble();
                        PointCartesien p = new PointCartesien(tmp, tmp2);
                        listeDePoints.add(p);
                    } else
                        tmp = lireDouble.nextDouble();
                    i++;
                }
            } else {
                while (lireDouble.hasNextDouble()) {
                    if (i % 2 == 1 && i != 0) {
                        tmp2 = lireDouble.nextDouble();
                        PointCartesien p = new PointCartesien(tmp, tmp2);
                        listeDePoints.add(p);
                    } else
                        tmp = lireDouble.nextDouble();
                    i++;
                }
                afficherLesPointsCartesiens();
            }
            Fich.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /**
     * charge une liste de points cartesiens en format primitif
     * 
     * @param File
     *            le fichier à charger
     */
    protected void chargerFormatPrimitif(File f) {
        listeDePoints.clear();
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(f));
            while (true) {
                try {
                    double x = dis.readDouble();
                    double y = dis.readDouble();
                    listeDePoints.add(new PointCartesien(x, y));
                } catch (EOFException ex1) {
                    afficherLesPointsCartesiens();
                    break;
                } catch (IOException ex1) {
                    // TODO Auto-generated catch block
                    ex1.printStackTrace();
                    warning("Erreur de lecteur : " + ex1.getMessage());
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            warning("Erreur de lecteur : " + e.getMessage());
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                warning("Erreur de lecteur : " + e.getMessage());
            }
        }
    }

    /**
     * charge une liste de points cartesiens en format Objet
     * 
     * @param File
     *            le fichier à charger
     */
    protected void chargerFormatObjet(File f) {
        FileInputStream fis = null;
        try {
            String to_display = "";
            fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            to_display.concat(ois.readObject().toString());
            setZoneDeTexte(to_display);
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Charge le contenu du fichier dans la zone de texte
     * 
     * @param File
     *            le fichier à afficher
     */
    protected void charger(File f) {
        try {
            counter.setUnity(100);
        } catch (PropertyVetoException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        counter.start(0);
        String ch1 = "";
        try {
            FileInputStream Fich = new FileInputStream(f);
            if (stringBufferiser()) {
                StringBuffer ch = new StringBuffer();
                for (int i = 0; i < (int) f.length(); i++) {
                    ch.append((char) Fich.read());
                }
                setZoneDeTexte(ch.toString());
            } else {
                for (int i = 0; i < (int) f.length(); i++) {
                    ch1 += (char) Fich.read();
                }
                setZoneDeTexte(ch1);
            }
            counter.stop();
            this.warning("Temps mis : " + counter.getCounter() + " ds\n" + "Taille : " + (int) f.length() + " bytes");
            Fich.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Sauve le contenu de la zone de texte dans le fichier
     * 
     * @param File
     *            le fichier à sauvegarder
     */
    protected void sauver(File f) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            fw.write(getZoneDeTexte());
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Copie le contenu d'un fichier dans un autre fichier et affiche ce contenu
     * dans la zone de texte
     * 
     * @param File
     *            f1 - le fichier d'origine
     * @param File
     *            f2 - le fichier destination
     */
    protected void copier(File f1, File f2) {
        try {
            FileReader file1 = new FileReader(f1);
            FileWriter file2 = new FileWriter(f2);
            char[] buf = new char[(int) f1.length()];
            file1.read(buf);
            file2.write(buf);
            file1.close();
            file2.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new TestIO();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
