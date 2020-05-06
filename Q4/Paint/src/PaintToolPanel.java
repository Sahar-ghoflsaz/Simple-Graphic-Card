import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaintToolPanel extends JPanel
{
    
    protected ToolButton toolButtons[];
    public StrokeToolPanel strokeToolPanel;
    private JComboBox fillerType;
    private ImageIcon pencil = new ImageIcon("pic\\pencil.png");
    private ImageIcon roundRectangle = new ImageIcon("pic\\polygon.png");
    private ImageIcon filledRoundRectangle = new ImageIcon("pic\\filled-polygon.png");
    private ImageIcon oval = new ImageIcon("pic\\oval.png");
    private ImageIcon filledOval = new ImageIcon("\\pic\\filled-oval.png");
    private ImageIcon rectangle = new ImageIcon("pic\\rectangle.png");
    private ImageIcon filledRectangle = new ImageIcon("pic\\filled-rectangle.png");
    private ImageIcon lineTool = new ImageIcon("pic\\line.png");
    private ImageIcon paintBrush = new ImageIcon("pic\\paint-brush.png");
    private ImageIcon eraser = new ImageIcon("pic\\eraser.png");
    private JPanel toolPanel = new JPanel();
    private ToolButton colorPickerButton;


    
    public PaintToolPanel(StrokeToolPanel strokeToolPanel)
    {
        setBackground(Color.darkGray);                          
        setPreferredSize(new Dimension(200, 0));
        setLayout(new BorderLayout(8, 8));

        toolPanel.setLayout(new GridLayout(4, 2));              
        toolPanel.setBackground(Color.darkGray);
        toolPanel.setPreferredSize(new Dimension(200, 300));
        this.strokeToolPanel = strokeToolPanel;                 
        toolButtons = new ToolButton[8];                       

        String[] fillerTypes = {"EMPTY", "FILLED"};            
        fillerType = new JComboBox(fillerTypes);               
        ComboBoxHandler handler = new ComboBoxHandler();        
        fillerType.addActionListener(handler);                  
        fillerType.setFont(new Font("Cambria", Font.BOLD, 16)); 

        
        ImageIcon colorPicker = new ImageIcon("pic\\colors.jpg");
        colorPickerButton = new ToolButton(colorPicker, ToolFactory.createTool(ToolFactory.PENCILL_TOOL));
        colorPickerButton.addMouseListener(new MouseAdapter() {      

            public void mousePressed(MouseEvent event) {                
                {                                                       
                    ColorPalette.selectedColorDisplay.setBackground(JColorChooser.showDialog(Main.paint, "Change Color", Main.paint.drawingPanel.brushColor));
                    ColorPalette.selectedColor = ColorPalette.selectedColorDisplay.getBackground();                                   
                    Main.paint.drawingPanel.currentToolDetails.setColor(ColorPalette.selectedColorDisplay.getBackground());     
                    Main.paint.drawingPanel.setCurrentColor(ColorPalette.selectedColor);                                   
                }
            }
        });


        addBasicToolButtons();               
        addEmptyShapeToolButtons();          
        for (ToolButton toolButton : toolButtons) toolPanel.add(toolButton);   

        add(toolPanel, "North");
        add(fillerType, "Center");
        add(strokeToolPanel, "South");
    }

    private void addBasicToolButtons()
    {
        toolButtons[0] = new ToolButton(pencil, ToolFactory.createTool(ToolFactory.PENCILL_TOOL));
        toolButtons[1] = new ToolButton(paintBrush, ToolFactory.createTool(ToolFactory.AIR_BRUSH_TOOL));
        toolButtons[2] = new ToolButton(eraser, ToolFactory.createTool(ToolFactory.ERASER_TOOL));
        toolButtons[3] = colorPickerButton;
        toolButtons[4] = new ToolButton(lineTool, ToolFactory.createTool(ToolFactory.LINE_TOOL));
    }

    private void addEmptyShapeToolButtons()
    {
        toolButtons[5] = new ToolButton(oval, ToolFactory.createTool(ToolFactory.OVAL_TOOL));
        toolButtons[6] = new ToolButton(roundRectangle, ToolFactory.createTool(ToolFactory.ROUND_RECTANGLE_TOOL));
        toolButtons[7] = new ToolButton(rectangle, ToolFactory.createTool(ToolFactory.RECTANGLE_TOOL));
    }

    private void addFilledShapeToolButtons()
    {
        toolButtons[5] = new ToolButton(filledOval, ToolFactory.createTool(ToolFactory.FILLED_OVAL_TOOL));
        toolButtons[6] = new ToolButton(filledRoundRectangle, ToolFactory.createTool(ToolFactory.FILLED_ROUND_RECTANGLE_TOOL));
        toolButtons[7] = new ToolButton(filledRectangle, ToolFactory.createTool(ToolFactory.FILLED_RECTANGLE_TOOL));
    }

    
    private class  ComboBoxHandler implements ActionListener  
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            fillerType = (JComboBox)e.getSource();
            int selectedValue = fillerType.getSelectedIndex();
            if (selectedValue ==0)          
            {
                for (ToolButton toolButton1 : toolButtons) toolPanel.remove(toolButton1);  
                revalidate();
                repaint();
                addBasicToolButtons();                     
                addEmptyShapeToolButtons();                
                for (ToolButton toolButton : toolButtons) toolPanel.add(toolButton);
            }
            else if (selectedValue == 1 )  
            {
                for (ToolButton toolButton1 : toolButtons) toolPanel.remove(toolButton1); 
                revalidate();
                repaint();
                addBasicToolButtons();                     
                addFilledShapeToolButtons();              
                for (ToolButton toolButton : toolButtons) toolPanel.add(toolButton);
            }
        }
    }
}