package silisyum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class Dosyadan_NEC_Oku extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8269370798852929547L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea kutu;
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Dosyadan_NEC_Oku dialog = new Dosyadan_NEC_Oku();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Dosyadan_NEC_Oku() {
		setBounds(100, 100, 1000, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("Yukle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String butunDosya = "";
				String bizimVeriler = "";
					
					try {
						butunDosya = new String(Files.readAllBytes(Paths.get("C:\\4nec2\\out\\yeni_dipol_04.out")));
						Scanner sc = new Scanner(butunDosya);
						sc.useDelimiter("DEGREES[\r\n]+").next();						
						sc.useDelimiter("\\s").next();
						
						String blok = sc.useDelimiter("[\r\n]+[\r\n]+[\r\n]+").next();
						sc.close();
						sc = new Scanner(blok);
						
						String devam_mi = "+"; // Devam
						
						while(devam_mi == "+") {
							// Theta
							sc.useDelimiter("\\S").next();
							sc.useDelimiter("\\s");						
							bizimVeriler += sc.next();
							bizimVeriler += "\t";
							
							// Phi
							sc.useDelimiter("\\S").next();
							sc.useDelimiter("\\s");						
							bizimVeriler += sc.next();
							bizimVeriler += "\t";
							
							// Vertical ve Horizontali atla
							sc.useDelimiter("\\S").next();
							sc.useDelimiter("\\s").next();
							sc.useDelimiter("\\S").next();
							sc.useDelimiter("\\s").next();
							
							// Total dB
							sc.useDelimiter("\\S").next();
							sc.useDelimiter("\\s");						
							bizimVeriler += sc.next();
							bizimVeriler += "\n";
							
							// Alt satira gec
							sc.useDelimiter("[\r\n]+").next();
							if(sc.hasNext() == false) break; // Bisey kalmamis birak gitsin.		
							sc.useDelimiter("\\s").next();							

						}
							

						sc.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				kutu.setText(bizimVeriler);
				
			}
		});
		btnNewButton.setBounds(433, 504, 89, 23);
		contentPanel.add(btnNewButton);
		{
			kutu = new JTextArea();
			kutu.setBorder(new LineBorder(new Color(0, 0, 0)));
			kutu.setBounds(10, 11, 964, 431);
			scroll = new JScrollPane(kutu, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setLocation(0, 0);
			scroll.setSize(984, 476);
			contentPanel.add(scroll);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
}
