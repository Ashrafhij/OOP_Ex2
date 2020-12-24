package gameClient;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Ex2 {
	
	public static void main(String[] args) {

		gameStart("Enter ID + Level : e.g. ' 123456789 11 ' \"");
	}

	//Starter Window with ID Check && Level Validation Check
	public static void gameStart(String str){
		JFrame window= new JFrame();
		window.setTitle("EX2 Game");
		//to open in the middle of the window
		window.setLocation(600, 300);
		window.setSize(333 , 123); 
		Container container = window.getContentPane();
		container.setLayout(new FlowLayout());
		JTextField txt = new JTextField();
		txt.setPreferredSize(new Dimension(100, 27));
		JLabel label = new JLabel(str);


		JButton ok = new JButton("OK");

		container.add(txt);
		container.add(ok);
		container.add(label);
		window.setVisible(true);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int id = 0, level = -1;
				String input = txt.getText();
				String[] splited = input.split(" ");
				//input should be id + level , any thing else should return error
				if(splited.length !=2) {
					window.dispose();
					gameStart("Wrong Content , Enter As Instructed");
				}else { //check if input just numbers and space + number id length is 9
					if(splited[0].length()==9 && input.matches("^[0-9 ]+$")) {
						id = Integer.parseInt(splited[0]);
						level = Integer.parseInt(splited[1]);
						if(level < 0 || level>23 || splited[0].length()!=9) {
							window.dispose();
							gameStart("Enter As Instructed .PS. "
									+ "Level Between 0-23 , "
									+ "ID 9 Digits");
						}else {
							Ex2_Client ex = new Ex2_Client();
							ex.myLevel(level);
							ex.myID(id);
							Thread client = new Thread(new Ex2_Client());
							client.start();
						}
					}else {
						window.dispose();
						gameStart("Wrong Content , Enter As Instructed");
					}}
				window.dispose();
			}
		});
	}
}
