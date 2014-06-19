/* 
 * GameImage.java
 * @author P. Plassmann 
 * @version 2.0 29/12/2014
 * 
 * This class is an example how complex stuff such as image handling can be packaged away neatly 
 * in an object and only simple methods such as the constructor and the 'draw' method are exposed to 
 * the user of the GameImage object.
 * 
 * This class:
 *      - loads images from file (in the 'GameImage' constructor)
 *      - displays them at user provided x,y positions ('draw' method)
 *      - can also display images scaled and rotated ('drawRotatedScaled' method)
 *      - can detect collision between two images ('collidesWith' method)
 *      - can figure out if an image is fully within another one ('isWithin' method)
 * 
 * Additionally, the user can set the 'visible' flag ('setVisible' method) to indicate if an image is 
 * shown or not. This also influences collision detection (invisible images can't collide).
 * 
 * Standard 'get' methods to interrogate the current position, dimension and visibility of images are
 * also provided.
 * 
 */

import java.net.URL;                  // required for figuring out path names to files
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;                // required for using Images
import java.awt.Transparency;

import javax.swing.ImageIcon;         // required for using ImageIcons

import java.awt.Graphics2D;           // required for using a Graphics2D canvas
import java.awt.geom.AffineTransform; // weird stuff required for drawing images
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;            // for collision detection we use rectangle intersect


public class GameImage
{

    // Object variables. 
    // Note that this makes the image 'private', i.e. it can only be accessed via the 
    // public 'draw' method coded below and thus can't be fiddled with from the outside
    private Image img;                  // the image itself
    private int x,y;                    // its x and y position when drawn
    private boolean visible=false;      // initial status, constructor sets it to true if successful

    
    // Constructor. You pass the full pathname as a String into the constructor. It tries to
    // read it from disk and then stores it away in the object variable 'img'. From there it
    // can be called up onto a Canvas any time by calling the 'draw' method underneath.
    public GameImage(String s) {
        ImageIcon i;
        try{
            // Build the full path name to the image file
            URL url = getClass().getResource(s);
            // Load the image as an image icon first (don't ask...)
            i = new ImageIcon( url );

        } catch(Exception e) {
            System.out.println("GameImage constructor: can't find this image file: "+s);
            return;
        }

        try{
            // Extract the image from the ImageIcon and store it in as an Image type
            img = i.getImage() ;
            visible=true;

        } catch(Exception e) {
            System.out.println("GameImage constructor: can't convert 'ImageIcon' of file: "+s+" to the storage type 'Image'");
            return;
        }

    } // end of constructor

    
    // Draw the image on a 'Canvas' at position xPos/yPos. 
    public void draw(Graphics2D theCanvas, int xPos, int yPos) 
    {
        
       
        if( visible ) {
            try{
                theCanvas.drawImage(img, new AffineTransform(1f,0f,0f,1f,xPos,yPos),null);
            }
            catch(Exception e){
                System.out.println("GameImage: problem encountered in the 'draw' method");
            }
        }   

        x=xPos;     // store the current x 
        y=yPos;     //      and y positions
    } // end of draw method
    
    // Same as above, but this time with the option to rotate and to scale.
    // Rotation is in degrees (will accept negative numbers and re-base those >360).
    // Scale 1.0 is full size, scale 0.5 is half size, scle 2.0 is double size, etc.
    public void drawRotatedScaled(Graphics2D theCanvas, int xPos, int yPos, int rotation, float scale) 
    {
        // scale of zero can't be handled, check
        if(scale<0.01) scale =0.01f;
        
        if( visible ) {
            try{
                AffineTransform trans = new AffineTransform(1f,0f,0f,1f,xPos,yPos);
                trans.rotate(Math.toRadians(rotation),img.getWidth(null)/2 *scale, img.getHeight(null)/2 *scale);
                trans.scale(scale, scale);
                theCanvas.drawImage(img, trans, null);
            }
            catch(Exception e){
                System.out.println("GameImage: problem encountered in the 'drawRotatedScaled' method");
            }
        }

        x=xPos;     // store the current x 
        y=yPos;     // and y positions
    } // end of draw method

    //Same as drawRotatedScaled but it now flips the image horizontally.
    public void drawRSflipHor(Graphics2D theCanvas, int xPos, int yPos, int rotation, float scale) 
    {
    	BufferedImage image;
    	
        // scale of zero can't be handled, check
        if(scale<0.01) scale =0.01f;
        
        if( visible ) {
            try{
            	  // Create a buffered image from the source image with a format that's compatible with the screen
               	  GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
               	  GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
            	  GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
            	  // If the source image has no alpha info use Transparency.OPAQUE instead
            	  image = graphicsConfiguration.createCompatibleImage(img.getWidth(null), img.getHeight(null), Transparency.BITMASK);
            	
            	  // Copy image to buffered image
            	  Graphics graphics = image.createGraphics();
            	  
            	  // Paint the image onto the buffered image
            	  graphics.drawImage(img, 0, 0, null);
            	  graphics.dispose();

                AffineTransform trans = new AffineTransform(1f,0f,0f,1f,xPos,yPos);
                trans.rotate(Math.toRadians(rotation),img.getWidth(null)/2 *scale, img.getHeight(null)/2 *scale);
                trans.scale(scale, scale);
                
             // Flip the image horizontally
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-img.getWidth(null), 0);  
                op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                image = op.filter(image, null);

                
                theCanvas.drawImage(image, trans, null);
                

            }
            catch(Exception e){
                System.out.println("GameImage: problem encountered in the 'drawRotatedScaled' method");
            }
        }

        x=xPos;     // store the current x 
        y=yPos;     // and y positions
    } // end of draw method
    
    // A set of 4 methods to return position and dimensions of the image
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return img.getWidth(null);
    }

    public int getHeight() {
        return img.getHeight(null);
    }

    
    // Access the visible/invisible flag
    public boolean isVisible() {
        return visible;
    }

    public void setVisible( boolean newStatus ) {
        visible= newStatus;
    }
    


    
    // Collision detection
    // We first create 2 Rectangles which have the same position and dimension of both images.
    // Then we use the 'intersects' method of the Rectangle class to find out if they overlap.
    // For details see: http://docs.oracle.com/javase/1.5.0/docs/api/java/awt/Rectangle.html
    public boolean collidesWith( GameImage GI2 ) {
        
        if(!visible) return false;
        
        Rectangle thisImg     = new Rectangle( this.getX(), this.getY(), this.getWidth(), this.getHeight() );  
        Rectangle theOtherImg = new Rectangle( GI2.getX(),  GI2.getY(),  GI2.getWidth(),  GI2.getHeight()  );  

        if( thisImg.intersects( theOtherImg ) ) return true;
        else                                    return false;

    }

    // Check if this image is fully inside another image
    public boolean isWithin( GameImage GI2 ) {
        if(!visible) return false;
        
        Rectangle thisImg     = new Rectangle( this.getX(), this.getY(), this.getWidth(), this.getHeight() );  
        Rectangle theOtherImg = new Rectangle( GI2.getX(),  GI2.getY(),  GI2.getWidth(),  GI2.getHeight()  );  

        if( theOtherImg.contains( thisImg ) ) return true;
        else                                  return false;
    }

    // Overloading the previous method so that we can also pass an area into it
    public boolean isWithin( Rectangle anArea ) {
        if(!visible) return false;
 
        Rectangle thisImg     = new Rectangle( this.getX(), this.getY(), this.getWidth(), this.getHeight() );  

        if( anArea.contains( thisImg ) ) return true;
        else                             return false;
    }

    // You can extend the above collision/overlap detection functionality by using more of the methods
    // of the Rectangle class. For details go to: http://docs.oracle.com/javase/1.5.0/docs/api/java/awt/Rectangle.html

} // end of class GameImage