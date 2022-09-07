package cryptography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import javax.imageio.ImageIO;

public class encode {
    public static void main(String[] args) throws Exception {
    	
    	int headerSize = 16;
        String filename = "butterfly.jpg"; //opens up the image
        String message = "hidden message"; //message to encode
        String messageAscii = new BigInteger(message.getBytes()).toString(2); // converts the message into ASCII equivalent
        
	
        for(int length = messageAscii.length() % 8 ;length!=8;length++) messageAscii = "0"+ messageAscii; //uses modulus function to add the missing front zeroes
        BufferedImage p = ImageIO.read(new File(filename)); //p stands for picture
         
        //header
        String payloadSize = Integer.toBinaryString(messageAscii.length()); //converts the payloadsize into a binary string.
        for(int length = payloadSize.length() % headerSize ;length!=headerSize;length++)  payloadSize = "0"+payloadSize; //adds 0s in front until it matches with the header size
        int x=0 , y=0;
        for(int i=0;i<headerSize;i++){ // for every bit within the headersize
            int bit = Character.getNumericValue(payloadSize.charAt(i)); //check its numerical value (0 or 1)
            if (bit == 1) p.setRGB(x,y,p.getRGB(x,y) | 1); //if the bit is 1 then it changes the last RGB bit value into 0
            else {p.setRGB(x,y,p.getRGB(x,y) & ~(1));} //otherwise (bit is 0) then change it to 0
            x++;
            if(x == p.getWidth()-1){x=0;y++;}
            }
        
          x=p.getWidth()-1; y=p.getHeight()-1;
       
          //payload
        for(int i=messageAscii.length()-1;i>=0;i--){ //for the entire length of the secret message in ASCII
            int bit = Character.getNumericValue(messageAscii.charAt(i)); //retrieve the bits starting from the final one
									//e.g. using "yes" example would be 01111001011001010111001(1)
									        the next iteration would be 0111100101100101011100(1)1
							   					       then 011110010110010101110(0)11 etc.

            if (bit == 1) p.setRGB(x,y,p.getRGB(x,y) | 1); //if the bit is 1 then it changes the last RGB bit value into 0
            else {p.setRGB(x,y,p.getRGB(x,y) & ~(1));} //otherwise (bit is 0) then change it to 0
            x--;
            if(x == 0){x=p.getWidth()-1;y--;} 
            }
            File newPicture = new File("butterfly.png"); 
            ImageIO.write(p, "png", newPicture); //overwrite the picture, or create a new one.

    }
}
