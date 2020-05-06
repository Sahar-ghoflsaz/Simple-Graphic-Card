import javax.swing.*;
import java.awt.*;

public class StrokePanel extends JPanel
{
       public StrokePanel()
    {
        setPreferredSize(new Dimension(84, 84));   
    }

    
    public void paintComponent(Graphics g)   
    {
        g.setColor(Main.paint.drawingPanel.currentToolDetails.getColor());
        g.fillRect(getWidth() / 2 - Main.paint.drawingPanel.currentToolDetails.getStrokeWidth() / 2,
                getHeight() / 2 - Main.paint.drawingPanel.currentToolDetails.getStrokeWidth() / 2,
                Main.paint.drawingPanel.currentToolDetails.getStrokeWidth(),
                Main.paint.drawingPanel.currentToolDetails.getStrokeWidth());
        g.setColor(Color.black);
        g.drawRect(getWidth() / 2 - Main.paint.drawingPanel.currentToolDetails.getStrokeWidth() / 2,
                getHeight() / 2 - Main.paint.drawingPanel.currentToolDetails.getStrokeWidth() / 2,
                Main.paint.drawingPanel.currentToolDetails.getStrokeWidth(),
                Main.paint.drawingPanel.currentToolDetails.getStrokeWidth());
  }
}