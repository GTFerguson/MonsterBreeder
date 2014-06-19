/*
 * GameSound.java
 * @author P. Plassmann 
 * @version 1.0 16/03/2013
 * 
 * This class is an example how complex stuff such as sound handling can be packaged away neatly 
 * in an object and only simple methods such as "playClip", "stopClip", etc are exposed to the
 * user of the GameSound object.
 */

import java.io.*;                   // required for file I/O
import java.net.URL;                // required for figuring out path names to files
import javax.sound.sampled.*;       // required for using the sound system



public class GameSound
{
    // Object variable. Note that this is 'private' and thus can't be fiddled with from the outside.
    // Access to this private clip only via the public mehods such as "playClip".
    private Clip clp; 
    private String clipName;

    // Constructor. You pass into the constructor a string with the file name and it then tries to load
    // this file and store it in the "clp" object declared above. 
    // If it fails to do that it produces some error messages (see 'catch' part below)
    public GameSound(String s)
    {
        // Make a copy of the file name so that we can use it later in error messages if necessary
        clipName = s;

        // As always with File I/O we need 'try'-'catch' constructions just in case the file is not
        // there or has been spelled incorrectly.

        try{
            // Build the full path name to the sound clip
            URL url = getClass().getResource(s);
            // Open the audio file as an input stream (calls helper method 'loadStrea'm, coded below)
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(loadStream(url.openStream()));
            // Figure out which encoding format the sound file is using
            AudioFormat af = audioInputStream.getFormat();
            // Calculate the size of the clip
            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());

            // Un-comment the line below to get some info on the clip.
            // System.out.println("\nLOADING:\nClip "+(num+1)+"= "+url+"\nSize= "+size+" bytes");

            // Provide storage space
            byte[] audio = new byte[size];
            // Now read into the storage space just provided
            audioInputStream.read(audio, 0, size);

            // Attach the audio data to a clip
            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            try {
                clp = (Clip) AudioSystem.getLine(info);
                clp.open(af, audio, 0, size);

            }
            catch(Exception e){
            }

        } catch(UnsupportedAudioFileException e) {
            System.out.println("Sorry, this file format is not supported by SoundBank");
            return;
        } catch(IOException e) {
            System.out.println("Hmmm... can't find this file: "+clipName);
            return;
        }

    }

    // Helper method (called in the constructor above).
    // Note that this is a 'private' method, i.e. not accessible from outside this class
    private ByteArrayInputStream loadStream(InputStream inputstream) throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte data[] = new byte[1024];
        for(int i = inputstream.read(data); i != -1; i = inputstream.read(data))
            bytearrayoutputstream.write(data, 0, i);

        inputstream.close();
        bytearrayoutputstream.close();
        data = bytearrayoutputstream.toByteArray();
        return new ByteArrayInputStream(data);
    }

    // Play a sound. Checks if the sound number passed into the method (variable x) is valid before trying.
    public void playClip() 
    {
        try{
            clp.start();
        }
        catch(Exception e){
            System.out.println("GameSound class, playClip method: problem playing this sound: " +clipName);
        }
    }

    
    // Rewinds a clip to the beginning
    public void rewindClip() 
    {

        try{
            clp.setFramePosition(0);  // rewind to beginning
        }
        catch(Exception e){
            System.out.println("GameSound class, rewindClip method: problem rewinding " +clipName);

        }
    }

    // Play a clip repeatedly.
    public void loopClip() 
    {
        clp.start();
        clp.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Stop a sound. 
    public void stopClip() 
    {
        clp.stop();
    }

    // Set the volume of a clip. Range: 0 to 10
    public void setVolume(int volume) 
    {
        if((volume>10)||(volume< 0)) 
        {
            System.out.println("GameSound class, setVolume method for sound " +clipName);
            System.out.println("The volume has to be between 0 and 10.\nSetting to 5");
            volume=5;
        }
        
        // The "gain" in Java is really in "decibel" and defined between -80 (off) and 6 (full blast).
        // It is also logarithmic, wich is tricky to understand. Anything below -3 is too quiet to hear.
        // Therefore let's translate the volume (0 to 10) to something that is actually usable.        
        float gain;
        switch( volume ) {
            case 0 : gain=-80.0f; break;
            case 1 : gain=-40.0f; break;
            case 2 : gain=-25.0f; break;
            case 3 : gain=-10.0f; break;
            case 4 : gain= -5.0f; break;
            case 5 : gain=  0.0f; break;
            case 6 : gain=  1.0f; break;
            case 7 : gain=  2.0f; break;
            case 8 : gain=  3.5f; break;
            case 9 : gain=  6.0f; break;
            case 10: gain=  6.0f; break;
            default: gain=  4.0f;
        }
       

        // Now set the gain
        FloatControl gainControl = (FloatControl) clp.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gain);
    }
}