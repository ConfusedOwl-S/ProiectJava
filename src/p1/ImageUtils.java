package p1;

import java.awt.Image;
import java.io.*;
import java.util.Base64;

import javax.imageio.ImageIO;
/**
 * Clasa este folosita pentru prelucrarea imaginilor din baza de date
 */
public class ImageUtils {
	/**
	 * Metoda ce codifica o imagine data in base64
	 * @param imageFile
	 * @return String ce reprezinta imaginea codificata in base64
	 */
	public static String encodeImageToBase64(File imageFile) {
        try (FileInputStream fileInputStream = new FileInputStream(imageFile)) {
            byte[] imageBytes = new byte[(int) imageFile.length()];
            fileInputStream.read(imageBytes);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * Metoda ce converteste un String de numere din base64 intr-o imagine
	 * @param base64String
	 * @return byteArray ce reprezinta imaginea stocata in baza de date
	 */
	public static Image decodeBase64ToImage(String base64String) {
	    try {
	        // Decode the Base64 string into a byte array
	        byte[] imageBytes = Base64.getDecoder().decode(base64String);

	        // Convert the byte array into an image
	        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
	        return ImageIO.read(byteArrayInputStream);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
