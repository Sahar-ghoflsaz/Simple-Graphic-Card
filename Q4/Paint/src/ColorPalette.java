import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorPalette extends JPanel
{
    
    protected ColorButton colorButtons[];     
    public static JPanel selectedColorDisplay;   
    protected Color colors[];                 
    public static Color selectedColor;           

    
    public ColorPalette()
    {
        setBackground(Color.darkGray);                
        setLayout(new BorderLayout());               
        colors = new Color[92];                       
        colors[0] = Color.BLACK;
        colors[1] = Color.WHITE;
        colors[2] = Color.RED;
        colors[3] = Color.YELLOW;
        colors[4] = Color.GREEN;
        colors[5] = Color.BLUE;
        colors[6] = Color.CYAN;
        colors[7] = Color.PINK;


        selectedColor = Color.black;                                    
        selectedColorDisplay = new JPanel();                            
        selectedColorDisplay.setPreferredSize(new Dimension(180, 92));   
        selectedColorDisplay.addMouseListener(new MouseAdapter() {      

            public void mousePressed(MouseEvent event) {               
                {                                                       
                    selectedColorDisplay.setBackground(JColorChooser.showDialog(Main.paint, "Change Color", Main.paint.drawingPanel.brushColor));
                    selectedColor = selectedColorDisplay.getBackground();                                   //change the isSelected color
                    Main.paint.drawingPanel.currentToolDetails.setColor(selectedColorDisplay.getBackground());     //change the ToolDetails color
                    Main.paint.drawingPanel.setCurrentColor(selectedColor);                                   //change the DrawingPanel brushColor
                }
            }
        });
        JPanel colorButtonsGrid = new JPanel();                     //create the ColorButton grid
        colorButtonsGrid.setBackground(Color.darkGray);
        colorButtonsGrid.setLayout(new GridLayout(4, 16, 6, 6));
        colorButtons = new ColorButton[colors.length];              //add the created colors to the ColorButton grid
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new ColorButton(colors[i]);
            colorButtonsGrid.add(colorButtons[i]);
        }

        ColorPanel colorButtonsPanel = new ColorPanel(Color.darkGray);      
        colorButtonsPanel.setLayout(new BorderLayout(6, 6));
        colorButtonsPanel.add(selectedColorDisplay, "West");
        colorButtonsPanel.add(colorButtonsGrid, "Center");
        JPanel colorButtonRows = new JPanel();                              
        colorButtonRows.setLayout(new BorderLayout());
        colorButtonRows.add(new ColorPanel(Color.darkGray), "West");
        colorButtonRows.add(new ColorPanel(Color.darkGray), "East");
        colorButtonRows.add(new ColorPanel(Color.darkGray), "South");
        colorButtonRows.add(new ColorPanel(Color.darkGray), "North");
        colorButtonRows.add(colorButtonsPanel, "Center");
        add(colorButtonRows, "Center");
    }

   
    public void deselectAll()
    {                                                            
        for (ColorButton colorButton : colorButtons) colorButton.isSelected = false;
    }

    public void paintComponent(Graphics g)                      
    {
        super.paintComponent(g);
    }
}