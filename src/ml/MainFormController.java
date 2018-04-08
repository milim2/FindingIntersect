package ml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainFormController implements Initializable
{
    // constants
    private static final int PADDING = 10;      // pixels
    private static final int UNIT_COUNT = 10;   // # of units only on positive side

    // member vars
    private int width;                  // width of drawing area
    private int height;                 // height of drawing area
    private int centerX;                // center X in screen space
    private int centerY;                // center y in screen space
    private double mouseX;              // screen coordinate x
    private double mouseY;              // screen coordinate y
    private double coordRatio;          // map screen coord to logical coord, s/l
    private double coordX;              // logical coordinate x
    private double coordY;              // logical coordinate y
    private Line[] hLines;              // horizontal grid lines
    private Line[] vLines;              // vertical grid lines

    // JavaFX controls
    private Rectangle rectClip;         // clipping rectangle
    @FXML
    private Pane paneView;
    @FXML
    private Pane paneControl;
    @FXML
    private Label labelCoord;
    @FXML
    private Line line1a;
    @FXML
    private Line line1b;
    @FXML
    private Line line1c;
    @FXML
    private Line line2a;
    @FXML
    private Line line2b;
    @FXML
    private Line line2c;
    @FXML
    private Circle point1a;
    @FXML
    private Circle point1b;
    @FXML
    private Circle point2a;
    @FXML
    private Circle point2b;
    @FXML
    private Circle pointIntersect;
    @FXML
    private Button reset;
    @FXML
    private Label intersect;
    @FXML
    private Slider sliderL1X1;
    @FXML
    private Label labelL1X1;
    @FXML
    private Label labelL1Y1;
    @FXML
    private Label labelL1X2;
    @FXML
    private Label labelL1Y2;
    @FXML
    private Slider sliderL1X2;
    @FXML
    private Slider sliderL1Y1;
    @FXML
    private Slider sliderL1Y2;
   
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // init line arrays
        initGrid();

        // set clip region for drawing area
        rectClip = new Rectangle(500, 500);
        paneView.setClip(rectClip);

        // update width and height of drawing area
        ChangeListener resizeListener = (ov, oldV, newV) -> handleViewResized();
        paneView.widthProperty().addListener(resizeListener);
        paneView.heightProperty().addListener(resizeListener);
        
        // property binding for the sliders
        labelL1X1.textProperty().bind(sliderL1X1.valueProperty().asString("%.1f"));
        labelL1Y1.textProperty().bind(sliderL1Y1.valueProperty().asString("%.1f"));
        labelL1X2.textProperty().bind(sliderL1X2.valueProperty().asString("%.1f"));
        labelL1Y2.textProperty().bind(sliderL1Y2.valueProperty().asString("%.1f"));        
    
    }



    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMouseMoved(MouseEvent event)
    {
        mouseX = event.getX();
        mouseY = event.getY();
        coordX = (mouseX - centerX) / coordRatio;
        coordY = (height - mouseY - centerY) / coordRatio;
        labelCoord.setText(String.format("(%.1f, %.1f)", coordX, coordY));
    }



    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMouseDragged(MouseEvent event)
    {
        mouseX = event.getX();
        mouseY = event.getY();
        coordX = (mouseX - centerX) / coordRatio;
        coordY = (height - mouseY - centerY) / coordRatio;
        labelCoord.setText(String.format("(%.1f, %.1f)", coordX, coordY));
    }



    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMousePressed(MouseEvent event)
    {
        
    }



    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMouseReleased(MouseEvent event)
    {
    }



    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMouseExited(MouseEvent event)
    {
        labelCoord.setText("");
    }



    ///////////////////////////////////////////////////////////////////////////
    private void initGrid()
    {
        int lineCount = UNIT_COUNT * 2 + 1; // both side plus 1 at enter
        hLines = new Line[lineCount];
        vLines = new Line[lineCount];

        // create line objects
        for(int i = 0; i < lineCount; ++i)
        {
            hLines[i] = new Line();
            hLines[i].setStrokeWidth(0.2);
            hLines[i].setStroke(Color.GRAY);
            paneView.getChildren().add(hLines[i]);
            hLines[i].toBack();

            vLines[i] = new Line();
            vLines[i].setStrokeWidth(0.2);
            vLines[i].setStroke(Color.GRAY);
            paneView.getChildren().add(vLines[i]);
            vLines[i].toBack();
        }

        // for center line
        hLines[lineCount / 2].setStroke(Color.BLACK);
        hLines[lineCount / 2].setStrokeWidth(0.4);
        vLines[lineCount / 2].setStroke(Color.BLACK);
        vLines[lineCount / 2].setStrokeWidth(0.4);

        // layout grid lines
        updateGrid();
    }



    ///////////////////////////////////////////////////////////////////////////
    private void handleViewResized()
    {
        width = (int)paneView.getWidth();
        height = (int)paneView.getHeight();

        // compute the ratio of scrren to virtual = s / v
        double dim = Math.min(width, height) - (PADDING * 2);
        coordRatio = dim / (UNIT_COUNT * 2.0);

        centerX = (int)(width * 0.5 + 0.5);
        centerY = (int)(height * 0.5 + 0.5);
        System.out.printf("center: (%d, %d)\n", centerX, centerY);

        // update clipping region
        rectClip.setWidth(width);
        rectClip.setHeight(height);

        updateGrid();
        updateLines();
    }



    ///////////////////////////////////////////////////////////////////////////
    private void updateGrid()
    {
        int dim;    // square dimension
        int xGap, yGap;

        if(width > height)
        {
            dim = height - (PADDING * 2);
            xGap = (int)((width - dim) * 0.5 + 0.5);
            yGap = PADDING;
        }
        else
        {
            dim = width - (PADDING * 2);
            xGap = PADDING;
            yGap = (int)((height - dim) * 0.5 + 0.5);
        }
        double step = dim / (UNIT_COUNT * 2.0);

        for(int i = 0; i < hLines.length; ++i)
        {
            hLines[i].setStartX(xGap);
            hLines[i].setStartY(yGap + (int)(step * i + 0.5));
            hLines[i].setEndX(width - xGap);
            hLines[i].setEndY(yGap + (int)(step * i + 0.5));

            vLines[i].setStartX(xGap + (int)(step * i + 0.5));
            vLines[i].setStartY(yGap);
            vLines[i].setEndX(xGap + (int)(step * i + 0.5));
            vLines[i].setEndY(height - yGap);
        }
    }



    ///////////////////////////////////////////////////////////////////////////
    private void updateLines()
    {
        point1a.setCenterX(centerX + (coordRatio * 0));
        point1a.setCenterY(centerY + (coordRatio * 0));
        System.out.println(centerX + coordRatio);
    }

    
    public void reset(Stage stage){        
        reset.setOnAction(e -> {
            reset(stage);
        });
    }
    
    
   
}
