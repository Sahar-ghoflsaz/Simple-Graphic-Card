import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolButton extends JButton implements ActionListener
{

   
    public JLabel label;
    public Tool tool;

    
    public ToolButton(Icon icon, Tool tool)
    {
        label = new JLabel(icon);
        setLayout(new BorderLayout());
        add(label);
        this.tool = tool;
        addActionListener(this);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }

    
    public void actionPerformed(ActionEvent event)
    {
        Main.paint.drawingPanel.currentTool = tool;
        Main.paint.repaint();
    }
}
