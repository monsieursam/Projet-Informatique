package interfaceGraphique;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.metal.MetalFileChooserUI;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import Messagerie.*; 
import traitementImage.*;

public class Fenetre {
	//La fenetre
	class win extends JFrame implements Observer {
		private static final long serialVersionUID = 1L;
		

		
		BarreMenu menue = new BarreMenu();
		//Le bouton d'importation
		ImporterBouton a;
		 
		//L'affichage de l'image
		JLabel c;
		//L'enregistrement de l'image
		BufferedImage bi;
		//L'enregistrement de l'image de base 
		BufferedImage biBase;
		File file;
		paneGauche pan2 = new paneGauche();
		JPanel pan = new JPanel();
		
		JSplitPane splitPane = new JSplitPane(1, pan, pan2);
		//Le constructeur de la fenetre
		win(){
			//Le JPanel
			
			
			//Pose le bouton pour importer l'image
			this.a = new ImporterBouton();
			//Creer l'affichage de l'image
			this.c = new JLabel();
	
	
			BoutonFiltre envoye = new BoutonFiltre(9,"Webcam");
			BoutonFiltre recevoir = new BoutonFiltre(10,"Envoyer");
			
			this.setTitle("Filtre");
		    this.setSize(900, 750);
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    this.setLocationRelativeTo(null);
		    pan.setBackground(Color.white);
		    BoutonFiltre recharger = new BoutonFiltre(18, "Recharger");
		   
		    //Ajout ddes boutons a notre JPanel et du JLabel (Image)
		    pan.add(recharger);
		    pan.add(this.a);
		    pan.add(envoye);
		    pan.add(recevoir);
		    pan.add(this.c);
			
			Color pixelcolor= new Color(4, 6, 45);
			pan2.setBackground(pixelcolor);
			splitPane.setDividerLocation(600);
			splitPane.setOneTouchExpandable(false);
			this.getContentPane().add(splitPane);
		    
		    //rendre visible le frame 
		    this.setVisible(true);
		}
		
		
		//class du menu 
		class BarreMenu extends JMenuBar implements ActionListener{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			private JMenuBar menuBar = new JMenuBar();
			
			private JMenu fichierMenu = new JMenu("File");
			private JMenu editMenu = new JMenu("Edit"); 
			private JMenu sousMenuZoom = new JMenu("Zoom"); 
			private JMenu filtreMenu = new JMenu("Filtres");
			private JMenu sousMenuBlur = new JMenu("BLur");
			private JMenu sousMenuAjust = new JMenu("Adjustment");
			private JMenu sousMenuColor = new JMenu("Color");
			private JMenu aboutMenu = new JMenu("About");

			
			private JMenuItem ouvrirFile = new JMenuItem("Ouvrir");
			private JMenuItem fermerFile = new JMenuItem("Fermer");
			private JMenuItem saveFile = new JMenuItem("Save");
			private JMenuItem saveAsFile = new JMenuItem("Save As");
			private JMenuItem quitFile = new JMenuItem("Quit");
			private JMenuItem blurMoy = new JMenuItem("Blur Moyen"),
				    blurGaussin = new JMenuItem("Gaussin Blur"),
				    bLurLight = new JMenuItem("Blur light"); 
		    private JMenuItem inverse = new JMenuItem("Inverse Image 180°"),
		    		miroir = new JMenuItem("Inverse Image Miror"),
				    rotR = new JMenuItem("Rotation Right"),		
				    rotL = new JMenuItem("Rotation Left"),
				    reduce = new JMenuItem("Reduce Image's Size"),
				    extend = new JMenuItem("Extend Image's Size");
		    private JMenuItem rouge = new JMenuItem("Red"),
		    		vert = new JMenuItem("Green"),
		    		bleue = new JMenuItem("Blue"),
		    		inverseColor= new JMenuItem("Inverse Color") ;
		    private JMenuItem miroirCouper = new JMenuItem("Miror");
		    private JMenuItem itemAbout = new JMenuItem("?");
		    private JMenuItem zoomAEdit = new JMenuItem("+");
		    private JMenuItem zoomREdit = new JMenuItem("-");
		    private JMenuItem resetEdit = new JMenuItem("Reset"); 
		    
			public BarreMenu() { // constructure du menu
				
				setJMenuBar(menuBar);	// creer le menu 
			
				menuBar.add(fichierMenu); // creer le sous menu fichier dans le menubar
				fichierMenu.setMnemonic('F');

				fichierMenu.add(ouvrirFile); // ajouter les action a faire ds fichier oucrir enregistrer quiter rafrechir ..
				ouvrirFile.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent evt){
								//Creation de la fenetre pour choisir l'image
								final JFrame frame = new JFrame();
								JFileChooser filechooser= new JFileChooser();
							    filechooser.setDialogTitle("Choisis ton image");
							    filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
							    int option =filechooser.showOpenDialog(frame); 
							    if(option==JFileChooser.APPROVE_OPTION)
							    {
							         file = filechooser.getSelectedFile();    
							       //enregistre l'image en attribut
								        biBase = (BufferedImage) BMPImage.readBMP(file);
							        try{   
							        	//Affiche dans le JLabel
							            bi=ImageIO.read(file);
							            //Redimenssionne la taille du JFrame en fonction de la taille de l'image
							            c.setIcon(new ImageIcon(bi));
							        }catch(IOException e){
							        }
							    }
							}
						}
						);
				
				fichierMenu.add(fermerFile);
				fermerFile.addActionListener(
						new ActionListener () {
							public void actionPerformed(ActionEvent e) {
								ImageIcon icon = new ImageIcon();
								c.setIcon(icon);
							}
						}
						);
				
						
				fichierMenu.add(saveFile);
				saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
				saveFile.addActionListener(
						new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								 try{   
									 	biBase=bi ; 
							            ImageIO.write(bi, "png", file);
							            System.out.println("la modification de l'image est enregistrée ");
							        }catch(IOException e){
							            System.out.println("erreur ");
							        }
							}
						});
				
				fichierMenu.add(saveAsFile);
				saveAsFile.addActionListener(
						new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								//Creation de la fenetre pour choisir le new emplacement de l'image
								final JFrame frame = new JFrame();
								JFileChooser filechooser= new JFileChooser();
							    filechooser.setDialogTitle("Choisis ton emplacement");
							    filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
							    int option =filechooser.showSaveDialog(frame); 
							    if(option==JFileChooser.APPROVE_OPTION)
							    {
							        try{   
							            ImageIO.write(bi, "png", filechooser.getSelectedFile());
							        }catch(IOException e){
							        }
							    }
							}
						});
				
				fichierMenu.addSeparator();
				
				quitFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
				fichierMenu.add(quitFile);
				quitFile.addActionListener( 
						new ActionListener(){
							public void actionPerformed(ActionEvent e) { 
								System.exit(0);
							} 
						});
				
				// LE menu edit 
				editMenu.setMnemonic('E'); // ALT + E le menu edit sera ouvert 

				menuBar.add(editMenu);
				editMenu.add(resetEdit);
				resetEdit.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent e) { 
								try {
									bi=ImageIO.read(file);
						            c.setIcon(new ImageIcon(bi)); 
								} catch (IOException e1) {
									// TODO Auto-generated catch block					e1.printStackTrace();
									}
								}
						});
				
				editMenu.addSeparator();	
				
				editMenu.add(sousMenuZoom);
				sousMenuZoom.add(zoomAEdit);
				zoomAEdit.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
					        	Image chocolat = biBase;
					        	//Appliquer Filtre blur
								chocolat = Zoom.zoomPlus(bi);
								ImageIcon icon = new ImageIcon(chocolat);
								c.setIcon(icon);
								bi = (BufferedImage) chocolat;
				        		
					        }
						});
				sousMenuZoom.add(zoomREdit);
				zoomREdit.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
					        	Image chocolat = biBase;
					        	//Appliquer Filtre blur
								chocolat = Zoom.zoomMoins(bi);
								ImageIcon icon = new ImageIcon(chocolat);
								c.setIcon(icon);
								bi = (BufferedImage) chocolat;
				        		
					        }
						});
				
		       
				// Le menu  Filtre
				menuBar.add(filtreMenu);	
				filtreMenu.setMnemonic('L');

				filtreMenu.add(sousMenuBlur); 
				
				sousMenuBlur.add(bLurLight); 
				bLurLight.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
					        	Image chocolat = biBase;
					        	try {
				        			//Appliquer Filtre blur
									chocolat = Filtre1.blur(bi);
									ImageIcon icon = new ImageIcon(chocolat);
					        		c.setIcon(icon);
					        		bi = (BufferedImage) chocolat;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        		
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
					        }
						});
				sousMenuBlur.add(blurMoy); 
				blurMoy.addActionListener((ActionListener)this);
				sousMenuBlur.add(blurGaussin); 
				blurGaussin.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
					        	Image chocolat = biBase;
					        	try {
				        			//Appliquer Filtre blur
									chocolat = Filtre1.gaussianBlur(bi);
									ImageIcon icon = new ImageIcon(chocolat);
					        		c.setIcon(icon);
					        		bi = (BufferedImage) chocolat;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        		
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
					        }
						});
				
				filtreMenu.addSeparator();	
				
				filtreMenu.add(sousMenuAjust);
				
				sousMenuAjust.add(inverse); 
				inverse.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
								Image chocolat = biBase;
								// Filtre qui retourne l'image 180° 
								chocolat = Filtre1.retourneImage(bi);
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
				        	
					        }
						});
				
				
				sousMenuAjust.add(rotL); 
				rotL.addActionListener (
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
								Image chocolat = biBase;
								//Filtre autre
				        		chocolat = Filtre1.rotation(bi);
				        		//Appliquer Filtre qui tourne l'image 90° a gauche 
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				
				sousMenuAjust.add(rotR); 
				rotR.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
								Image chocolat = biBase;
								//Filtre autre
				        		chocolat = Filtre1.rotation(bi);
				        		chocolat = Filtre1.retourneImage(bi);
				        		//Appliquer Filtre inverser couleur
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				sousMenuAjust.add(miroir); 
				miroir.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
								Image chocolat = biBase;
								//Filtre autre
				        		chocolat = Filtre1.miroirImage(bi);
				        		//Appliquer Filtre qui inverse l' image 
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				
				sousMenuAjust.addSeparator();
				
				sousMenuAjust.add(reduce); 
				reduce.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
								Image chocolat = biBase;
								//Filtre Zooom
				        		chocolat = Zoom.zoomMoins(bi);
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				
				sousMenuAjust.add(extend); 
				extend.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event)
					        {
								Image chocolat = biBase;
								chocolat = Zoom.zoomPlus(bi);
								//Appliquer zoom PLus
								ImageIcon icon = new ImageIcon(chocolat);
								c.setIcon(icon);
								bi = (BufferedImage) chocolat;
					        }
						});
				
				
				filtreMenu.addSeparator();	
				
				filtreMenu.add(sousMenuColor) ; 
				sousMenuColor.add(rouge); 
				rouge.addActionListener(
						new ActionListener() {
							Image chocolat = biBase; 
							public void actionPerformed(ActionEvent event)
					        {
								chocolat = Filtre1.redFiltre(bi);
						        // appliquer le filtre rouge 
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				sousMenuColor.add(bleue); 
				bleue.addActionListener(
						new ActionListener() {
							Image chocolat = biBase; 
							public void actionPerformed(ActionEvent event)
					        {
								chocolat = Filtre1.blueFiltre(bi);
						        // appliquer le filtre bleue
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				sousMenuColor.add(vert); 
				vert.addActionListener(
						new ActionListener() {
							Image chocolat = biBase; 
							public void actionPerformed(ActionEvent event)
					        {
								chocolat = Filtre1.greenFiltre(bi);
						        // appliquer le filtre vert
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				sousMenuColor.add(inverseColor); 
				inverseColor.addActionListener(
						new ActionListener() {
							Image chocolat = biBase; 
							public void actionPerformed(ActionEvent event)
					        {
								//Appliquer Filtre inverser couleur
								chocolat = Filtre1.InverseColor(bi);
								
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				filtreMenu.addSeparator();
				
				filtreMenu.add(miroirCouper); 
				miroirCouper.addActionListener(
						new ActionListener() {
							Image chocolat = biBase; 
							public void actionPerformed(ActionEvent event)
					        {
								//Appliquer Filtre miroir couper en deux 
				        		chocolat = Filtre1.miroirCouperImage(bi);
								ImageIcon icon = new ImageIcon(chocolat);
				        		c.setIcon(icon);
				        		bi = (BufferedImage) chocolat;
					        }
						});
				
				// le menu About 
				// contient une page txt qui explique comment ca marche 
				aboutMenu.setMnemonic('A');
				menuBar.add(aboutMenu);
				aboutMenu.add(itemAbout); 
				itemAbout.addActionListener((ActionListener)this);
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}		
				
		//Notre bouton qui applique un filtre
		class BoutonFiltre extends JButton{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			int nbFiltre;
			BoutonFiltre(int a,String s){
				super(s);
				this.nbFiltre = a;
				this.addActionListener(
					new ActionListener() {
			        public void actionPerformed(ActionEvent event)
			        {
			        	Image chocolat = biBase;
			        	if (a == 9){
			        		OpenWebcam a = new OpenWebcam();
			        		
			        	}else if(a == 18){
			        		splitPane.remove(pan2);
			        		pan2 = new paneGauche();
			        		splitPane.add(pan2);
			        		splitPane.setDividerLocation(600);
			        	}
			        	else if (a == 10){
			        		try {
								Send.send( bi , null);
							} catch (Exception e) {
								e.printStackTrace();
							}
			        	}
			        	
			        }
				});     
				
			}
			

			
		}
		//Notre bouton qui importe l'image
		
		
		class ImporterBouton extends JButton implements ActionListener{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			//Le constructeur 
			ImporterBouton(){
				super("Importer Image");
				this.addActionListener(this);
			}
			//Le clique sur le bouton
			public void actionPerformed(ActionEvent evt){
				//Creation de la fenetre pour choisir l'image
				JFileChooser filechooser= new JFileChooser();
			    filechooser.setDialogTitle("Choisis ton image");
			    filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    int returnval=filechooser.showOpenDialog(this);
			    if(returnval==JFileChooser.APPROVE_OPTION)
			    {
			         file = filechooser.getSelectedFile();
			        //enregistre l'image en attribut
			        biBase = (BufferedImage) BMPImage.readBMP(file);
			        
			        try{   
			        	//Affiche dans le JLabel
			            bi=ImageIO.read(file);
			            //Redimenssionne la taille du JFrame en fonction de la taille de l'image
			            c.setIcon(new ImageIcon(bi));
			        }catch(IOException e){
			        }
			    }
			}
		}
		class OpenWebcam {
			BufferedImage a;
			Webcam webcam;
			OpenWebcam(){
					JPanel pano = new JPanel();
					webcam = Webcam.getDefault();
					webcam.setViewSize(WebcamResolution.VGA.getSize());
					WebcamPanel panel = new WebcamPanel(webcam);
					panel.setFPSDisplayed(true);
					panel.setDisplayDebugInfo(true);
					panel.setImageSizeDisplayed(true);
					panel.setMirrored(true);
					
					JButton prendre = new JButton("Prendre une photo");
					JFrame window = new JFrame("Webcam");
					prendre.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event){
							BufferedImage aff = webcam.getImage();
							
							a = new BufferedImage(aff.getWidth(),aff.getHeight(),BufferedImage.TYPE_INT_RGB);
							a.createGraphics().drawImage(aff, 0, 0, Color.WHITE, null);
							try {
								bi = a;
								ImageIcon icon = new ImageIcon(bi);
								c.setIcon(icon);
			   
								ImageIO.write(a, "PNG", new File("test.bmp"));
								webcam.close();
								window.dispose();
			   
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					pano.add(panel);
					pano.add(prendre);
					window.setContentPane(pano);
					window.setResizable(true);
					window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					window.setVisible(true);
					window.pack();
					window.setSize(650, 600);
				}
				public BufferedImage getImage(){
					return this.a;
				}
			}
		@Override
		public void update(Observable o, Object transmis) {
			if (transmis instanceof BufferedImage){
				BufferedImage image = (BufferedImage) transmis ;
				JFrame ji = new JFrame();
				JPanel po = new JPanel();
				JLabel c = new JLabel();
				ImageIcon icoff = new ImageIcon((BufferedImage) image);
				c.setIcon(icoff);
				po.add(c);
				ji.setVisible(true);
				ji.setContentPane(po);
			}
			if (transmis instanceof String){
				String s = (String) transmis;
			}
		}
		class paneGauche extends JPanel{

			/**
			 * 
			 */
			
			private static final long serialVersionUID = 1L;
			public paneGauche() {
			      this.setLayout(new BorderLayout());
			      JFileChooser fileChooser = new JFileChooser();
			      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			      fileChooser.setDialogTitle("Choisis mon gars");
			      
			      File dir = new File("images/");
			      fileChooser.setCurrentDirectory(dir );
			      fileChooser.setMultiSelectionEnabled(false);
			      
			      
			      
			      fileChooser.addActionListener(new ActionListener() {
			         public void actionPerformed(ActionEvent e) {
			            if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
			               System.out.println("File selected: " + fileChooser.getSelectedFile());
			               if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
				            	file = fileChooser.getSelectedFile();
						        //enregistre l'image en attribut
						        biBase = (BufferedImage) BMPImage.readBMP(file);
						        
						        try{   
						        	//Affiche dans le JLabel
						            bi=ImageIO.read(file);
						            //Redimenssionne la taille du JFrame en fonction de la taille de l'image
						            c.setIcon(new ImageIcon(bi));
						        }catch(IOException dfsfdsf){
						        }
				            }
			            }
			         }
			      });
			      this.add(fileChooser);
			}
		}
	}

	
	//class webcam permet de prendre des photo a partir de la webcam de l'ordi $
	
		
	//Constructeur de la fenetre
	public Fenetre(){
		win a = new win();
		ReceiveRunnable rec = new ReceiveRunnable(a);
		Thread t = new Thread(rec);
		t.start();
		a.setVisible(true);
	}
	
	
}
