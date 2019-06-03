package es.uneatlantico.gdbd.gui;

import java.awt.BorderLayout;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

public class About extends JDialog {
	
	private static final Logger logger = LogManager.getLogger(About.class); 

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final static String UNEATLANTICO_ICONO = "uneat.png";
	private final static String LICENSE_FILE = "/LICENSE.txt";
	private JTextPane txInformation;
	private JScrollPane scrollPane;
	private JLabel lblLogo;
	/**
	 * Create the dialog.
	 */
	public About() {
		setResizable(false);
		setTitle("Acerca de");
		setBounds(100, 100, 670, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/about.png")));
		contentPanel.add(getScrollPane());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 230, 664, 35);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setBounds(532, 5, 120, 25);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		contentPanel.add(getLblLogo());
	}
	private JTextPane getTxInformation() {
		if (txInformation == null) {
			txInformation = new JTextPane();
			txInformation.setOpaque(false);
			txInformation.setEditable(false);
			txInformation.setFont(new Font("Arial", Font.PLAIN, 14));
			txInformation.setContentType("text/html");
			txInformation.setText(generateText());
			txInformation.setCaretPosition(0);
			txInformation.setBounds(163, 13, 269, 109);
		}
		return txInformation;
	}
	private String generateText() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML><BODY><CENTER>");
		
		
		sb.append("<B>Gestor de Documentación para Bases de Datos</B> ");
		sb.append("<BR><I>V");
		sb.append("0.0.5");
		sb.append(" ");
		sb.append("ALPHA");
		sb.append("</I>");
		sb.append("<BR>Alberto Gutiérrez Arroyo");
		sb.append("<BR>Trabajo de Fin de Grado");
		sb.append("<BR>Universidad Europea del Atlántico. 2019<BR>");
		sb.append(readLicense());
		sb.append("</CENTER></BODY></HTML>");
		
		return sb.toString();
		
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTxInformation());
			scrollPane.setOpaque(false);
			scrollPane.setBounds(228, 13, 424, 204);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return scrollPane;
	}
	private JLabel getLblLogo() {
		if (lblLogo == null) {
			lblLogo = new JLabel("");
			lblLogo.setBounds(22, 34, 183, 170);
			ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource(UNEATLANTICO_ICONO));
			
			lblLogo.setIcon(new ImageIcon(ScaledImage(img.getImage(), lblLogo.getWidth(), lblLogo.getHeight())));
		}
		
		return lblLogo;
	}
	
	private Image ScaledImage(Image img, int w, int h){
	    BufferedImage resizedImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);

	    Graphics2D g2 = resizedImage.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(img, 0,0,w,h,null);
	    g2.dispose();
	    return resizedImage;
	}
	
	public String readLicense() 
	{
		StringBuffer stringBuffer = new StringBuffer();
	    try {
	    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(LICENSE_FILE)));

//	    	this.getClass().getResource(LICENSE_FILE);
//	    	File file = new File(LICENSE_FILE);
//	    	FileReader fileReader = new FileReader(file);
//			BufferedReader bufferedReader = new BufferedReader(fileReader);
//			
			String line;
			while ((line = bufferedReader.readLine()) != null) 
			{
				stringBuffer.append("<BR>");
				stringBuffer.append(line.trim());
				
			}
			bufferedReader.close();
		} catch (FileNotFoundException fnfe) {
			logger.log(Level.ERROR, fnfe.getLocalizedMessage());
    		System.err.println(LICENSE_FILE + " no encontrado");
    	}
    	catch (IOException ioe) {
    		logger.log(Level.ERROR, ioe.getLocalizedMessage());
    		System.err.println("I/O error");
    	}
    	catch (Exception e) {
    		logger.log(Level.ERROR, e.getLocalizedMessage());
    	}
	    return stringBuffer.toString();
	}
}
