import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorButton extends JPanel
{
    Color color;
    boolean isSelected;

    
    public ColorButton(Color clr)
    {
        color = clr;                                
        isSelected = false;                       
        setBackground(color);                       
        addMouseListener(new MouseHandler());       
    }

    
    public void paintComponent(Graphics g)    
    {
        super.paintComponent(g);              
        g.setColor(Color.lightGray);           
        g.drawRect(0, 0, getWidth(), getHeight());  
        if(isSelected)
        {                                      
            g.setColor(Color.white);
            g.drawRect(1, 1, getWidth(), getHeight());
            g.drawRect(-1, -1, getWidth(), getHeight());
        }
    }

    
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed(MouseEvent event)          
        {
            Main.paint.colorPalette.deselectAll();          
            isSelected = true;                                
            Main.paint.drawingPanel.setCurrentColor(color);      
            ColorPalette.selectedColorDisplay.setBackground(color);  
            Main.paint.repaint();                            
        }
        public void mouseReleased(MouseEvent mouseevent){ }

        public void mouseClicked(MouseEvent mouseevent) {}

        public void mouseEntered(MouseEvent mouseevent){ }

    }
}
