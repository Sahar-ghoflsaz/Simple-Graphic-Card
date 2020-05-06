import javax.swing.*;
import java.awt.*;

public class PaintApplication extends JFrame
{
    
    public DrawingPanel drawingPanel;
    protected MenuBar menuBar;
    protected ColorPalette colorPalette;
    public PaintToolPanel paintToolPanel;

    
    public PaintApplication()
    {
        super("Melika Paint");  

        drawingPanel = new DrawingPanel();                              
        menuBar = new MenuBar();                                        
        colorPalette = new ColorPalette();                              
        paintToolPanel = new PaintToolPanel(new StrokeToolPanel(5));   
        add(menuBar, "North");                                         
        add(colorPalette, "South");
        add(paintToolPanel, "East");
        add(new JScrollPane(drawingPanel), "Center");

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
        				"E:\\java2\\paint\\paint.png"));    
        this.setSize(640,480);     
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   
        this.setLocationRelativeTo(null);                               
        this.setVisible(true);                                         
        setStaringColor();                                             
    }

    
    public void setStaringColor()     
    {
        ColorPalette.selectedColorDisplay.setBackground(Color.black);
        ColorPalette.selectedColor = ColorPalette.selectedColorDisplay.getBackground();
        drawingPanel.currentToolDetails.setColor(ColorPalette.selectedColorDisplay.getBackground());
        drawingPanel.brushColor = ColorPalette.selectedColor;
    }

}
