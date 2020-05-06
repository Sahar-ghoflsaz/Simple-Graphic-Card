import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;



public class StrokeToolPanel extends JPanel
{
    
    protected JSlider strokeSlider;
    protected JPanel strokePanel;
    protected StrokePanel stroke;

    
    public StrokeToolPanel(int stroke)
    {
        setPreferredSize(new Dimension(50, 256));                   
        setBackground(Color.darkGray);
        setLayout(new FlowLayout());

        strokeSlider = new JSlider(0, 0, 16, 5);                   
        strokeSlider.setPreferredSize(new Dimension(200,20));     
        strokeSlider.setPaintTicks(false);
        strokeSlider.setMajorTickSpacing(1);
        strokeSlider.setValue(stroke);
        strokeSlider.revalidate();

        SlideChangeListener listener = new SlideChangeListener();   
        strokeSlider.addChangeListener(listener);
        this.stroke = new StrokePanel();                           
        strokePanel = new JPanel();                                 
        strokePanel.setBackground(new Color(228, 237, 245));
        strokePanel.setPreferredSize(new Dimension(200, 100));
        strokePanel.setLayout(new FlowLayout());
        strokePanel.add(this.stroke, "South");                      
        add(strokePanel);                                          
        add(strokeSlider);                                         
    }

    
    private class SlideChangeListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent event)   
        {
            Main.paint.drawingPanel.currentToolDetails.setStrokeWidth(strokeSlider.getValue());  
            repaint();
        }
    }
}
