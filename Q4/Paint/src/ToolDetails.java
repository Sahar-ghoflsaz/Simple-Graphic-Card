import java.awt.*;


public class ToolDetails extends Tool
{
  
    protected int strokeWidth;
    protected Color color;

    public ToolDetails(Color brushColor, int stroke, int type)
    {
        super(type);
        color = brushColor;
        strokeWidth = stroke;
    }

 
    public Color getColor()
    {
        return color;
    }

    public void setColor(Color clr)
    {
        color = clr;
    }

    public void setStrokeWidth(int dim)
    {
        strokeWidth = dim;
    }

    public int getStrokeWidth()
    {
        return strokeWidth;
    }
}
