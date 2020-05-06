import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class DrawingPanel extends JPanel implements  MouseListener, MouseMotionListener
{

   
    public Image OSI;                  
    int OSIWidth, OSIHeight;            
    private int mouseX, mouseY;         
    private int prevX, prevY;           
    private int startX, startY;         
    private boolean isDrawing;         
    private Graphics2D dragGraphics;   
    public Color brushColor;           
    int brushPoints[][];                
    protected Boolean mousePressed;     
    public Tool currentTool;            
    public ToolDetails currentToolDetails;  
    public Color backgroundColor;           


   
    public DrawingPanel()
    {
        backgroundColor = Color.white;                  
        setBackground(backgroundColor);
        setPreferredSize(new Dimension(640,480));     
        addMouseListener(this);                         
        addMouseMotionListener(this);                   
        mousePressed = false;                           
        brushColor = Color.black;                       
        currentTool = ToolFactory.createTool(ToolFactory.PENCILL_TOOL);             
        currentToolDetails = new ToolDetails(brushColor, 5, ToolFactory.PENCILL_TOOL);     
    }


   
    private void drawGraphics(Graphics2D graphics2D, Tool currentTool, int pointX1, int pointY1, int pointX2, int pointY2)
    {
        if (currentTool.toolType == ToolFactory.LINE_TOOL)                  
        {                                                                   
            graphics2D.setStroke(new BasicStroke(currentToolDetails.strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawLine(pointX1, pointY1, pointX2, pointY2);        
            repaint();                                                      
            return;
        }

        if (currentTool.toolType == ToolFactory.AIR_BRUSH_TOOL)            
        {
                Random rand = new Random();                                
                brushPoints = new int[(currentToolDetails.strokeWidth * currentToolDetails.strokeWidth) / 10][2];    
                for (int i = 0; i < (currentToolDetails.strokeWidth * currentToolDetails.strokeWidth) / 10; i++)
                {
                    int pts[] = new int[2];
                        pts[0] = rand.nextInt(currentToolDetails.strokeWidth);      
                        pts[1] = rand.nextInt(currentToolDetails.strokeWidth);
                    graphics2D.drawRect(pointX1 + pts[0], pointY1 + pts[1], 1, 1);  
                    brushPoints[i] = pts;
                }
            repaint();                                                              
        }

        int positionX, positionY;   
        int weight, height;        
        if (pointX1 >= pointX2)
        {  
            positionX = pointX2;
            weight = pointX1 - pointX2;
        }
        else
        {   
            positionX = pointX1;
            weight = pointX2 - pointX1;
        }
        if (pointY1 >= pointY2)
        {  
            positionY = pointY2;
            height = pointY1 - pointY2;
        }
        else
        {   
            positionY = pointY1;
            height = pointY2 - pointY1;
        }

        if (currentTool.toolType == ToolFactory.RECTANGLE_TOOL)            
        {                                                                  
            graphics2D.setStroke(new BasicStroke(currentToolDetails.strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawRect(positionX, positionY, weight, height);             
            repaint();                                                      
            return;
        }

        if (currentTool.toolType == ToolFactory.OVAL_TOOL)              
        {                                                                  
            graphics2D.setStroke(new BasicStroke(currentToolDetails.strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawOval(positionX, positionY, weight, height);               
            repaint();                                                      
            return;
        }

        if (currentTool.toolType == ToolFactory.ROUND_RECTANGLE_TOOL)        
        {                                                                    
            graphics2D.setStroke(new BasicStroke(currentToolDetails.strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawRoundRect(positionX, positionY, weight, height, 20, 20);    
            repaint();                                                      
            return;
        }

        if (currentTool.toolType == ToolFactory.FILLED_OVAL_TOOL)            
        {
            graphics2D.fillOval(positionX, positionY, weight, height);      
            return;
        }

        if (currentTool.toolType == ToolFactory.FILLED_RECTANGLE_TOOL)      
        {
            graphics2D.fillRect(positionX, positionY, weight, height);      
            return;
        }

        if (currentTool.toolType == ToolFactory.FILLED_ROUND_RECTANGLE_TOOL) 
        {
            graphics2D.fillRoundRect(positionX, positionY, weight, height, 20, 20);   
        }
    }

    
    private void repaintRectangle(int pointX1, int pointY1, int pointX2, int pointY2)
    {
        int x, y;  
        int w, h;  
        if (pointX2 >= pointX1)
        {   
            x = pointX1;
            w = pointX2 - pointX1;
        }
        else
        {   
            x = pointX2;
            w = pointX1 - pointX2;
        }
        if (pointY2 >= pointY1)
        {   
            y = pointY1;
            h = pointY2 - pointY1;
        }
        else
        {   
            y = pointY2;
            h = pointY1 - pointY2;
        }
        repaint(x, y, w+1, h+1);      
    }

   
    private void createOffScreenImage()
    {
        if (OSI == null || OSIWidth != getSize().width || OSIHeight != getSize().height) {
            
            OSI = null;  
            OSI = createImage(getSize().width, getSize().height);
            OSIWidth = getSize().width;
            OSIHeight = getSize().height;
            Graphics graphics = OSI.getGraphics();  
            graphics.setColor(getBackground());
            graphics.fillRect(0, 0, OSIWidth, OSIHeight);
            graphics.dispose();
        }
    }

   
    public void paintComponent(Graphics graphics)
    {
        createOffScreenImage();                            
        Graphics2D graphics2D = (Graphics2D)graphics;       
        graphics.drawImage(OSI, 0, 0, this);                
        if (isDrawing &&                                      
                currentTool.toolType != ToolFactory.PENCILL_TOOL &&  
                currentTool.toolType != ToolFactory.AIR_BRUSH_TOOL &&
                currentTool.toolType != ToolFactory.ERASER_TOOL)  
        {
            graphics.setColor(brushColor);                                            
            drawGraphics(graphics2D, currentTool, startX, startY, mouseX, mouseY);     
        }

    }

    public void setOSImage (BufferedImage image)
    {
        OSI = image;                          
        repaint();
    }

    public void setImage(BufferedImage image)  
    {
        int w = image.getWidth();
        int h = image.getHeight();
        OSI = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        OSI = createImage(w, h);
        OSIWidth = getSize().width;
        OSIHeight = getSize().height;
        repaint();
        Graphics graphics = OSI.getGraphics();  
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, OSIWidth, OSIHeight);
        graphics.dispose();
    }

    public void clearImage(BufferedImage image)
    {
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        repaint();
    }

    private Color getCurrentColor()             
    {
        if (currentTool.toolType != ToolFactory.ERASER_TOOL)  
        {
            return currentToolDetails.getColor();
        }
        else
        {
            return getBackground();
        }
    }

    public void setCurrentColor(Color clr)         
    {
        brushColor = clr;
        currentToolDetails.setColor(clr);
    }


    public void mousePressed(MouseEvent evt)
    {
        if (isDrawing)                    
            return;                     

        prevX = startX = evt.getX();    
        prevY = startY = evt.getY();

        brushColor = getCurrentColor();                 
        dragGraphics = (Graphics2D) OSI.getGraphics();  
        dragGraphics.setColor(brushColor);              
        dragGraphics.setBackground(getBackground());

        isDrawing = true;                                 
    }

    
    public void mouseReleased(MouseEvent evt)
    {
        if (!isDrawing)
            return;             
        isDrawing = false;       
        mouseX = evt.getX();    
        mouseY = evt.getY();

        if (currentTool.toolType != ToolFactory.PENCILL_TOOL && currentTool.toolType != ToolFactory.AIR_BRUSH_TOOL && currentTool.toolType != ToolFactory.ERASER_TOOL)
        {
            repaintRectangle(startX, startY, prevX, prevY);
            if (mouseX != startX && mouseY != startY) {
         
                drawGraphics(dragGraphics, currentTool, startX, startY, mouseX, mouseY);
                repaintRectangle(startX, startY, mouseX, mouseY);
            }
        }
        dragGraphics.dispose();
        dragGraphics = null;
    }

   
    public void mouseDragged(MouseEvent evt)
    {
        if (!isDrawing)
            return;  

        mouseX = evt.getX();   
        mouseY = evt.getY();   

        if (currentTool.toolType == ToolFactory.PENCILL_TOOL)
        {
            drawGraphics(dragGraphics, ToolFactory.createTool(ToolFactory.LINE_TOOL), prevX, prevY, mouseX, mouseY); // A CURVE is drawn as a series of LINEs.
            repaintRectangle(prevX, prevY, mouseX, mouseY);
        }

        else if (currentTool.toolType == ToolFactory.ERASER_TOOL)
        {
            drawGraphics(dragGraphics, ToolFactory.createTool(ToolFactory.LINE_TOOL), prevX, prevY, mouseX, mouseY);
            repaintRectangle(prevX, prevY, mouseX, mouseY);
        }

        else if (currentTool.toolType == ToolFactory.AIR_BRUSH_TOOL)
        {
            drawGraphics(dragGraphics, ToolFactory.createTool(ToolFactory.AIR_BRUSH_TOOL), prevX, prevY, mouseX, mouseY);
            repaintRectangle(prevX, prevY, mouseX, mouseY);
        }
        else
        {
            repaintRectangle(startX, startY, prevX, prevY);     
            repaintRectangle(startX, startY, mouseX, mouseY);   
        }

        prevX = mouseX;  
        prevY = mouseY;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {}

}
