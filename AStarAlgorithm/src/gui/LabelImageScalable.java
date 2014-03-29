package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LabelImageScalable extends JLabel{
	public LabelImageScalable() {
	}
	
	public void setImage(String imageName){
		BufferedImage img;
		try {
			img = ImageIO.read(new File(imageName));
			Image image2 = img.getScaledInstance(this.getWidth(),
					this.getHeight(), Image.SCALE_SMOOTH);

			this.setIcon(new ImageIcon(image2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
