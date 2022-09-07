package cryptography;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class decode {
	public static void main(String[] args) throws Exception {
		String filename = "butterfly.png";
		BufferedImage p = ImageIO.read(new File(filename));
		int i =0;
		int ascii = 0;
		String StringBinaryPayloadSize = "";
		String message = "";
		int headerSize = 16;
		int xH=0,yH=0;
		
		//header
		for(int j=0;j<headerSize;j++){  //for every bit within the header size
            int bit = p.getRGB(xH, yH) & 1; //check the final bit
			if(bit == 1) StringBinaryPayloadSize = StringBinaryPayloadSize +"1"; //if it is one add 1 into the payloadsize in stringbinary format
			
			else { StringBinaryPayloadSize = StringBinaryPayloadSize +"0"; } // else  0
            xH++;
            if(xH == p.getWidth()-1){xH=0;yH++;}
            }			
			int payloadSize = Integer.parseInt(StringBinaryPayloadSize, 2); // convert it into decimal
		
			//payload
			entirefor:
		for(int y=p.getHeight()-1;y!=0;y--) { //for the entire heigth of the pixel
			for(int x=p.getWidth()-1; x!=0; x--) { // and the entire width
				int bit = p.getRGB(x, y) & 1; // check the final bit value of the pixel
				if(bit == 1) { // if it is 1 then add 1 into the ascii variable
				ascii = ascii >> 1;
				ascii = ascii | 0x80;
				}
				else { ascii = ascii >> 1; }// else 0o
				i++;
				if(i==8) { //once 8 of them are taken, it will convert it into the equivalent ascii value
					message =(char) ascii + message;
					i=0;ascii=0; //reset the variable for the next character
				}
				payloadSize--;
				if(payloadSize==0) break entirefor; //once all the message taken, break
			}
		}
		System.out.println(message); // prints the message
	}
}
