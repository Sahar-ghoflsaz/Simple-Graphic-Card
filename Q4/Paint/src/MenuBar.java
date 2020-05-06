import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.imageio.ImageIO;

public class MenuBar extends JMenuBar
{
    
    public JMenu file, view, options, help;
    public JMenuItem quit, newFile, openFile, saveFile;
    public JMenuItem howToPaint, about;
    public JFileChooser fileChooser = null;


   
    public MenuBar()
    {
        MenuOptionsHandler itemHandler = new MenuOptionsHandler();   
        file = new JMenu("File");                                    
        help = new JMenu("Help");
        view = new JMenu("View");
        options = new JMenu("Tools");
        newFile = new JMenuItem("New File");
        openFile = new JMenuItem("Open File");
        saveFile = new JMenuItem("Save File");
        quit = new JMenuItem("Exit");
        newFile.addActionListener(itemHandler);                      
        openFile.addActionListener(itemHandler);
        saveFile.addActionListener(itemHandler);
        quit.addActionListener(itemHandler);
        file.add(newFile);                                           
        file.add(openFile);
        file.add(saveFile);
        file.addSeparator();
        file.add(quit);
        howToPaint = new JMenuItem("Help...");
        howToPaint.addActionListener(itemHandler);
        about = new JMenuItem("About...");
        about.addActionListener(itemHandler);
        help.add(howToPaint);
        view.add(about);
        add(file);
        add(view);
        add(options);
        add(help);
    }

    
    public JFileChooser getFileChooser()
    {
        if (fileChooser ==null)
        {
            fileChooser = new JFileChooser();                       
            fileChooser.setFileFilter(new PNGFileFilter());         
        }
        return fileChooser;
    }

    public static BufferedImage getScreenShot(Component component)    
    {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());  
        return image;
    }

   
    private class MenuOptionsHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == quit)          
            {
                Main.paint.dispose();
                System.exit(0);
            }
            if (event.getSource() == newFile)       
            {

                BufferedImage bi = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);   //create new BufferedImage
                Main.paint.drawingPanel.clearImage(bi);                                         //clear current image
                Main.paint.drawingPanel.setImage(bi);                                           //set image to new blank image
            }
            if (event.getSource() == saveFile)     
            {
                JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
                int result = jFileChooser.showSaveDialog(Main.paint.drawingPanel);
                if (result==JFileChooser.APPROVE_OPTION )
                {
                    try
                    {
                        byte[] imageInByte;
                        File selectedFile = jFileChooser.getSelectedFile();
//                        selectedFile = new File(selectedFile.getAbsolutePath() + ".bmp");      //get isSelected file
                        BufferedImage img = getScreenShot(Main.paint.drawingPanel);            //get current image screenshot
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(img, "bmp", baos);                               //write the image to the isSelected file
                    	baos.flush();
			imageInByte = baos.toByteArray();
                        sendToUART("01000000");
                        StringBuilder stringBuilder = new StringBuilder();
                        int i = 0;
                        for (byte b : imageInByte) {
                            stringBuilder.append(b & 0xff);
                            if (++i == 8) {
                                String finalString = stringBuilder.toString();
                                sendToUART(finalString);
                                i = 0;
                            }
                        }
			baos.close();
                        

                    } catch (IOException ioe)
                    {
                        JOptionPane.showMessageDialog(null, "Could not save the file");
                    }
                }
            }
            if (event.getSource() == openFile)       
            {
                JFileChooser ch = getFileChooser();                                            //open file chooser
                int result = ch.showOpenDialog(Main.paint.drawingPanel);
                if (result==JFileChooser.APPROVE_OPTION )                                      //if OK
                {
                    try
                    {
                        Main.paint.drawingPanel.setOSImage(ImageIO.read(ch.getSelectedFile())); //set current image to isSelected image
                    } catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Could not open file");
                    }
                }
            }
            if (event.getSource() == howToPaint)       
            {
                JOptionPane.showMessageDialog(null, "Use the tool buttons on the left to paint components on the screen.\n" +
                        "Change the color of the components by selecting a color from the palette.\n" +
                        "Change the stroke of the components by moving the slider on the left.");
            }
            if (event.getSource() == about)         
            {
                JOptionPane.showMessageDialog(null, "This application was made for the purpose of my last asignment for second term of university");
            }
        }
    }

   

    public static void sendToUART(String messageString) {
        Enumeration portList;
        CommPortIdentifier portId;
        SerialPort serialPort = null;
        OutputStream outputStream = null;
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println(portId.getName());
                if (portId.getName().equals("COM4")) {
                    //if (portId.getName().equals("/dev/term/a")) {
                    try {
                        serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
                    } catch (PortInUseException e) {}
                    try {
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {}
                    try {
                        serialPort.setSerialPortParams(256000,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {}
                    try {
                        outputStream.write(messageString.getBytes());
                    } catch (IOException e) {}
                }
            }
        }
    }
    
    private static class PNGFileFilter extends FileFilter
    {
        public boolean accept(File file)             
        {
            return file.getName().toLowerCase().endsWith(".bmp") || file.isDirectory();
        }

        public String getDescription()
        {
            return "bitmap image  (*.bmp) ";
        }
    }

}