import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.io.File;
import java.io.FileWriter;

class painter {

    private static JFrame mainFrame;
    private static JFrame palleteFrame;
    private static JFrame shapeFrame;
    private static JFrame shapeEditFrame;
    private static JFrame shapeEditFrameMenu;
    
    private static DefaultListModel listModel;
    private static JList ShapeEditList;
    
    public static JPanel Canvas;
    private static JPanel shapeCanvas;
    
    public static int CanvasHeight;
    public static int CanvasWidth;
    
    private static int maxX;
    private static int maxY;
    
    public static Checkbox checkboxShade;
    
    public static Scrollbar x_ranger, y_ranger, radius_ranger;
    
    private static Color drawingColor = Color.black, fillColor = Color.blue, shapeColor = Color.black;
    
    public static Color backgroundColor = Color.white;
    
    public static JLabel scolorlabel, sbacklabel, sfilllabel, shapecolorlabel;
    
    public static JScrollPane Canvasscrollpane;
    
    public static BigInteger LINEcnt, CIRCLEcnt, SQUAREcnt, RECTANGLEcnt, TRIANGLEcnt, POLYGONcnt, TETHLAcnt, FREELINEcnt;
    
    public enum ShapeType {
        LINE, CIRCLE, SQUARE, RECTANGLE, TRIANGLE, POLYGON, TETHLA, FREELINE, NONE
    }
    
    public static ShapeType cur_state = ShapeType.NONE;
    
    public static ArrayList <Shape> shapes = new ArrayList<Shape>();
    
    public static int cnt_clicked;
    public static boolean right_clicked;
    public static boolean just_added;
    
    public static abstract class Shape {
        Color color;
        abstract ShapeType whatami( );
        abstract String printDescription( );
        void setColor( Color color ) {
            this.color = color;
        }
    }
    
    public static Comparator<Point> PointCompare = new Comparator<Point>() {
        @Override
        public int compare( Point p1, Point p2 ) {
            
            if ( p1.x == p2.x ) {
                return new Integer( p2.y ).compareTo( p1.y );
            }
            
            return new Integer( p2.x ).compareTo( p1.x );
        }
        
    };
    
    public static class Point {
        int x, y;
        Point( int x, int y ) {
            this.x = x;
            this.y = y;
        }
        public Point Copy( ) {
            return new Point( this.x, this.y );
        }
    }
    
    public static class Line extends Shape {
        Point p1, p2;
        Line( Color color, Point p1, Point p2 ) {
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
        }
        ShapeType whatami( ) {
            return ( ShapeType.LINE );
        }
        String printDescription( ) {
            String ret = "LINE";
            ret += " " + this.color.getRGB();
            ret += " " + this.p1.x + " " + this.p1.y;
            ret += " " + this.p2.x + " " + this.p2.y;
            return ( ret );
        }
    }
    
    public static class Circle extends Shape {
        Point center;
        int radius;
        boolean hasShade;
        
        Circle( Color color, Point center, int radius ) {
            this.color = color;
            this.center = center;
            this.radius = radius;
        }
        Circle( Color color, Point center, int radius, boolean hasShade ) {
            this.color = color;
            this.center = center;
            this.radius = radius;
            this.hasShade = hasShade;
        }
        
        ShapeType whatami( ) {
            return ( ShapeType.CIRCLE );
        }
        String printDescription( ) {
            String ret = "CIRCLE";
            ret += " " + this.color.getRGB();
            ret += " " + this.getShade();
            ret += " " + this.center.x + " " + this.center.y;
            ret += " " + this.radius;
            return ( ret );
        }
        public void setShade( boolean tmp ) {
            this.hasShade = tmp;
        }
        public boolean getShade( ) {
            return ( this.hasShade );
        }
    }
    
    public static class Square extends Shape {
        Point p1, p2;
        boolean hasShade;
        Square( Color color, Point p1, Point p2 ) {
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
        }
        Square( Color color, Point p1, Point p2, boolean hasShade ) {
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
            this.hasShade = hasShade;
        }
        ShapeType whatami( ) {
            return ( ShapeType.SQUARE );
        }
        String printDescription( ) {
            String ret = "SQUARE";
            ret += " " + this.color.getRGB();
            ret += " " + this.getShade();
            ret += " " + this.p1.x + " " + this.p1.y;
            ret += " " + this.p2.x + " " + this.p2.y;
            return ( ret );
        }
        public void setShade( boolean tmp ) {
            this.hasShade = tmp;
        }
        public boolean getShade( ) {
            return ( this.hasShade );
        }
    }
    
    public static class Rectangle extends Shape {
        Point p1, p2;
        boolean hasShade;
        Rectangle( Color color, Point p1, Point p2 ) {
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
        }
        Rectangle( Color color, Point p1, Point p2, boolean hasShade ) {
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
            this.hasShade = hasShade;
        }
        ShapeType whatami( ) {
            return ( ShapeType.RECTANGLE );
        }
        String printDescription( ) {
            String ret = "RECTANGLE";
            ret += " " + this.color.getRGB();
            ret += " " + this.getShade();
            ret += " " + this.p1.x + " " + this.p1.y;
            ret += " " + this.p2.x + " " + this.p2.y;
            return ( ret );
        }
        public void setShade( boolean tmp ) {
            this.hasShade = tmp;
        }
        public boolean getShade( ) {
            return ( this.hasShade );
        }
    }
    
    public static class Triangle extends Shape {
        Point p1, p2, p3;
        boolean hasShade;
        Triangle( Color color, Point p1, Point p2, Point p3 ) {
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }
        Triangle( Color color, Point p1, Point p2, Point p3, boolean hasShade ) {
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.hasShade = hasShade;
        }
        ShapeType whatami( ) {
            return ( ShapeType.TRIANGLE );
        }
        String printDescription( ) {
            String ret = "TRIANGLE";
            ret += " " + this.color.getRGB();
            ret += " " + this.getShade();
            ret += " " + this.p1.x + " " + this.p1.y;
            ret += " " + this.p2.x + " " + this.p2.y;
            ret += " " + this.p3.x + " " + this.p3.y;
            return ( ret );
        }
        public void setShade( boolean tmp ) {
            this.hasShade = tmp;
        }
        public boolean getShade( ) {
            return ( this.hasShade );
        }
    }
    
    public static class Polygon extends Shape {
        ArrayList <Point> points;
        boolean hasShade;
        Polygon( Color color, ArrayList <Point> points ) {
            this.color = color;
            this.points = new ArrayList<Point>( points );
        }
        Polygon( Color color, ArrayList <Point> points, boolean hasShade ) {
            this.color = color;
            this.points = new ArrayList<Point>( points );
            this.hasShade = hasShade;
        }
        ShapeType whatami( ) {
            return ( ShapeType.POLYGON );
        }
        String printDescription( ) {
            String ret = "POLYGON";
            ret += " " + this.color.getRGB();
            ret += " " + this.getShade();
            ret += " " + this.points.size();
            for ( int i = 0; i < this.points.size(); ++i ) {
                ret += " " + this.points.get(i).x + " " + this.points.get(i).y;
            }
            return ( ret );
        }
        public void setShade( boolean tmp ) {
            this.hasShade = tmp;
        }
        public boolean getShade( ) {
            return ( this.hasShade );
        }
    }
    
    public static class Tethla extends Shape {
        ArrayList <Point> points;
        Tethla( Color color, ArrayList <Point> points ) {
            this.color = color;
            this.points = new ArrayList<Point>( points );
        }
        ShapeType whatami( ) {
            return ( ShapeType.TETHLA );
        }
        String printDescription( ) {
            String ret = "TETHLA";
            ret += " " + this.color.getRGB();
            ret += " " + this.points.size();
            for ( int i = 0; i < this.points.size(); ++i ) {
                ret += " " + this.points.get(i).x + " " + this.points.get(i).y;
            }
            return ( ret );
        }
    }
    
    public static class FreeLine extends Shape {
        ArrayList <Point> points;
        FreeLine( Color color, ArrayList <Point> points ) {
            this.color = color;
            this.points = new ArrayList<Point>( points );
        }
        ShapeType whatami( ) {
            return ( ShapeType.FREELINE );
        }
        String printDescription( ) {
            String ret = "FREELINE";
            ret += " " + this.color.getRGB();
            ret += " " + this.points.size();
            for ( int i = 0; i < this.points.size(); ++i ) {
                ret += " " + this.points.get(i).x + " " + this.points.get(i).y;
            }
            return ( ret );
        }
    }
    
    public static int ccw( Point A, Point B, Point C ) {
        return ( (B.x - A.x) * (C.y - A.y) - (B.y - A.y) * (C.x - A.x) );
    }
    
    public static int sign( int tmp ) {
        return ( (tmp > 0) ? 1 : -1 );
    }
    
    
    public static int MAXN = 10000;
    
    public static ArrayList<Integer> X = new ArrayList<Integer>();//int[MAXN];
    public static ArrayList<Integer> Y = new ArrayList<Integer>();//int[] Y = new int[MAXN];
    public static int x1, x2, y1, y2;
    
    public static void AddIfNecessary( int pos ) {

        while ( X.size() <= pos ) {
            X.add( 0 );
        }

        while ( Y.size() <= pos ) {
            Y.add( 0 );
        }

    }
        
    public static void SetPixel( Graphics g, Point p ) {
        g.drawLine( p.x, p.y, p.x, p.y );
    }
    
    public static ArrayList<Point> GetbresenhamLinePoints( Line line ) {
        
        ArrayList<Point> Points = new ArrayList<Point>();
        
        maxX = Math.max( maxX, Math.max( line.p1.x, line.p2.x ) );
        maxY = Math.max( maxY, Math.max( line.p1.y, line.p2.y ) );
        
        int x0, y0, x1, y1;
    
        x0 = line.p1.x;
        y0 = line.p1.y;
        x1 = line.p2.x;
        y1 = line.p2.y;
        
        boolean has_swap = false;
        
        if ( Math.abs(y1 - y0) > Math.abs(x1 - x0) ) {
            has_swap = true;
            int temp = x0;
            x0 = y0;
            y0 = temp;
            
            temp = x1;
            x1 = y1;
            y1 = temp;
        }
        
        
        if ( x0 > x1 ) {
            
            int tmp = x0;
            x0 = x1;
            x1 = tmp;
            
            tmp = y0;
            y0 = y1;
            y1 = tmp;
            
        }
        
        int dx = x1 - x0;
        int dy = y1 - y0;
        int d = 0;

        int x = x0;
        int y = y0;
        
        if ( !has_swap ) {
            Points.add( new Point( x, y ) );
        }else {
            Points.add( new Point( y, x ) );
        }
        
        while ( x < x1 ) {
        
            d += dy;
        
            if ( dy < 0 ) {
            
                if ( d < -dx/2 ) {
                    d += dx;
                    --y;
                }
            
            }else if ( d > dx/2 ) {
                d -= dx;
                ++y;
            }
            
            ++x;
            
            if ( !has_swap ) {
                Points.add( new Point( x, y ) );
            }else {
                Points.add( new Point( y, x ) );
            }
            
        
        }
        
        return ( Points );
        
    }
    
    public static void drawbresenhamLine( Graphics g, Line line ) {
            
        maxX = Math.max( maxX, Math.max( line.p1.x, line.p2.x ) );
        maxY = Math.max( maxY, Math.max( line.p1.y, line.p2.y ) );
        
        g.setColor( line.color );
        
        int x0, y0, x1, y1;
    
        x0 = line.p1.x;
        y0 = line.p1.y;
        x1 = line.p2.x;
        y1 = line.p2.y;
        
        boolean has_swap = false;
        
        if ( Math.abs(y1 - y0) > Math.abs(x1 - x0) ) {
            has_swap = true;
            int temp = x0;
            x0 = y0;
            y0 = temp;
            
            temp = x1;
            x1 = y1;
            y1 = temp;
        }
        
        
        if ( x0 > x1 ) {
            
            int tmp = x0;
            x0 = x1;
            x1 = tmp;
            
            tmp = y0;
            y0 = y1;
            y1 = tmp;
            
        }
        
        int dx = x1 - x0;
        int dy = y1 - y0;
        int d = 0;

        int x = x0;
        int y = y0;
        
        if ( !has_swap ) {
            SetPixel( g, new Point( x, y ) );
        }else {
            SetPixel( g, new Point( y, x ) );
        }
        
        while ( x < x1 ) {
        
            d += dy;
        
            if ( dy < 0 ) {
            
                if ( d < -dx/2 ) {
                    d += dx;
                    --y;
                }
            
            }else if ( d > dx/2 ) {
                d -= dx;
                ++y;
            }
            
            ++x;
            
            if ( !has_swap ) {
                SetPixel( g, new Point( x, y ) );
            }else {
                SetPixel( g, new Point( y, x ) );                    
            }
            
        
        }
        
    }
        
    public static ArrayList<Point> GetbresenhamCirclePoints( Circle circle ) {
        
        int x0, y0, x1, y1;
    
        int x = 0;
        int y = circle.radius;
        int d = 3 - ( 2*circle.radius );
        
        ArrayList<Point> Points = new ArrayList<Point>();
        
        Points.add( new Point( x + circle.center.x, -y+circle.center.y ) );
        
        while ( y >= x ) {
        
            if ( d <= 0 ) {
                d += ( 4*x + 6 );
            }else {
                d += 4 * ( x - y ) + 10;
                --y;
            }
            
            ++x;
    
            Points.add( new Point( circle.center.x, -y+circle.center.y ) );
            Points.add( new Point( circle.center.x, +y+circle.center.y ) );
    
            Points.add( new Point( x + circle.center.x, y + circle.center.y ) );
            
            Points.add( new Point( y + circle.center.x, x + circle.center.y ) );
            
            Points.add( new Point( y + circle.center.x, -x + circle.center.y ) );
            
            Points.add( new Point( x + circle.center.x, -y + circle.center.y ) );
            
            Points.add( new Point( -x + circle.center.x, -y + circle.center.y ) );
            
            Points.add( new Point( -y + circle.center.x, -x + circle.center.y ) );
            
            Points.add( new Point( -y + circle.center.x, (x + circle.center.y) ) );
            
            Points.add( new Point( -x + circle.center.x, (y + circle.center.y) ) );
            
        }
        
        return ( Points );
        
    }
    
    public static void drawbresenhamCircle( Graphics g, Circle circle ) {
        
        g.setColor( circle.color );
        
        int x0, y0, x1, y1;
    
        int x = 0;
        int y = circle.radius;
        int d = 3 - ( 2*circle.radius );
        
        SetPixel( g, new Point( x + circle.center.x, -y+circle.center.y ) );
            
        while ( y >= x ) {
        
            if ( d <= 0 ) {
                d += ( 4*x + 6 );
            }else {
                d += 4 * ( x - y ) + 10;
                --y;
            }

            ++x;
    
            SetPixel( g, new Point( x + circle.center.x, y + circle.center.y ) );
                
            SetPixel( g, new Point( y + circle.center.x, x + circle.center.y ) );
            
            SetPixel( g, new Point( y + circle.center.x, -x + circle.center.y ) );
            
            SetPixel( g, new Point( x + circle.center.x, -y + circle.center.y ) );
            
            SetPixel( g, new Point( -x + circle.center.x, -y + circle.center.y ) );
            
            SetPixel( g, new Point( -y + circle.center.x, -x + circle.center.y ) );
            
            SetPixel( g, new Point( -y + circle.center.x, (x + circle.center.y) ) );
            
            SetPixel( g, new Point( -x + circle.center.x, (y + circle.center.y) ) );
            
        }
        
    }

    public static void InitCanvasSize( ) {
    
        boolean done = false;
    
        String display = "Enter height";
    
        while ( !done ) {
        
            done = true;
        
            String tmp_height = JOptionPane.showInputDialog( display );
    
            try {
                CanvasHeight = Integer.parseInt( tmp_height );
            }catch( Exception e ) {
                done = false;
            }
            
            if ( CanvasHeight < 1 ) {
                done = false;
            }
            
            display = "Please enter a valid height";
            
        }
            
        done = false;
    
        display = "Enter width";
        
        while ( !done ) {
            
            done = true;
            
            String tmp_width = JOptionPane.showInputDialog( display );
            
            try {
                CanvasWidth = Integer.parseInt( tmp_width );
            }catch( Exception e ) {
                done = false;
                e.printStackTrace();
            }
            
            if ( CanvasWidth < 1 ) {
                done = false;
            }
            
            display = "Please enter a valid width";
            
        }

    }
    
    public static void main( String args[] ) {

        LINEcnt = CIRCLEcnt = SQUAREcnt = RECTANGLEcnt = TRIANGLEcnt = POLYGONcnt = TETHLAcnt = FREELINEcnt = BigInteger.ZERO;
    
        InitCanvasSize( );
    
        mainFrame = new JFrame( "Paint" );
        
        mainFrame.setSize( CanvasWidth, CanvasHeight );//400, 400 );

        mainFrame.setDefaultCloseOperation( mainFrame.EXIT_ON_CLOSE );
        
        mainFrame.setLayout( new GridLayout( 1, 1 ) );
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int dy = screenSize.height/2;
        int dx = screenSize.width/2;
    
        mainFrame.setLocation(dx - mainFrame.getWidth()/2, dy-mainFrame.getHeight()/2);
        
        shapeFrame = new JFrame( "Shapes & Color" );
        shapeFrame.setSize( 288, 195 );
        shapeFrame.setDefaultCloseOperation( mainFrame.EXIT_ON_CLOSE );
        shapeFrame.setLayout( new GridLayout( 1, 1 ) );
        shapeFrame.setLocation( mainFrame.getX()-shapeFrame.getWidth(), mainFrame.getY() );
        shapeFrame.setVisible( true );
        
        shapeEditFrame = new JFrame( "Edit shapes" );
        shapeEditFrame.setSize( 401, 297 );
        shapeEditFrame.setDefaultCloseOperation( mainFrame.EXIT_ON_CLOSE );
        shapeEditFrame.setLocation( mainFrame.getX()-shapeEditFrame.getWidth(), shapeFrame.getY()+shapeFrame.getHeight() );//shapeEditFrame.getHeight()-shapeEditFrame.getHeight()/2 );
        shapeEditFrame.setVisible( true );
                
        listModel = new DefaultListModel();
        
        ShapeEditList = new JList( listModel );
       
        ShapeEditList.addMouseListener(
            
            new MouseAdapter() {
                    
                public void mouseClicked( MouseEvent e ) {
                
                    if ( ShapeEditList.getSelectedIndex() == -1 ) {
                        return;
                    }

                    Shape tmp_shape = shapes.get( ShapeEditList.getSelectedIndex() );
                
                }
                
            }
        
        );
        
        JPanel buttonEditPanel = new JPanel();
        
        checkboxShade = new Checkbox( "Shade", null, true );
        
        Button newStage = new Button( "New" );
        
        newStage.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    InitCanvasSize();
                    shapes.clear();
                    listModel.clear();
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int dy = screenSize.height/2;
                    int dx = screenSize.width/2;
                    mainFrame.setSize( CanvasWidth, CanvasHeight );
                    mainFrame.setLocation(dx - mainFrame.getWidth()/2, dy-mainFrame.getHeight()/2);                                
                    LINEcnt = CIRCLEcnt = SQUAREcnt = RECTANGLEcnt = TRIANGLEcnt = POLYGONcnt = TETHLAcnt = FREELINEcnt = BigInteger.ZERO;
                    Canvas.repaint();
                }
            }
            
        );
        
        Button saveStage = new Button( "Save" );
        
        saveStage.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    FileWriter output;
                    
                    JFileChooser saveFile = new JFileChooser();
                    
                    File workingDirectory = new File( System.getProperty("user.dir") );
                    saveFile.setCurrentDirectory( workingDirectory );  
                     
                    int v = saveFile.showSaveDialog(null);
                    
                    File file_to_save = null;
                    
                    if ( v == JFileChooser.APPROVE_OPTION) {
                         file_to_save = saveFile.getSelectedFile();
                    }else {
                        return;
                    }
                    
                    try {
                        output = new FileWriter( file_to_save );
                        
                        output.write( CanvasWidth + " " + CanvasHeight + " " + backgroundColor.getRGB() + " " + shapes.size() );
                        
                        output.write( "\n" );
                        
                        for ( int i = 0; i < shapes.size(); ++i ) {
                            output.write( shapes.get(i).printDescription() + "\n" );
                        }
                        
                        output.close();
                    }catch( Exception fw ) {
                        fw.printStackTrace();
                    }
                    
                }
            }
            
        );
        
        Button loadStage = new Button( "Load" );
        
        loadStage.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    
                    JFileChooser openFile = new JFileChooser();
                     
                    File workingDirectory = new File( System.getProperty("user.dir") );
                    openFile.setCurrentDirectory( workingDirectory );
                     
                    int v = openFile.showOpenDialog(null);
                    
                    File file_to_read = null;
                    
                    if ( v == JFileChooser.APPROVE_OPTION) {
                         file_to_read = openFile.getSelectedFile();
                    }else {
                        return;
                    }
                    
                    shapes.clear();
                    listModel.clear();
       
                    LINEcnt = CIRCLEcnt = SQUAREcnt = RECTANGLEcnt = TRIANGLEcnt = POLYGONcnt = TETHLAcnt = FREELINEcnt = BigInteger.ZERO;
                    
                    Scanner input = null;
                    
                    try {
                        input = new Scanner( new File( file_to_read.getAbsolutePath() ) );
                        
                        CanvasWidth = input.nextInt();
                        CanvasHeight = input.nextInt();
                        
                        int backcolor = input.nextInt();
                        
                        backgroundColor = new Color( backcolor );
                        
                        sbacklabel.setForeground( backgroundColor );
                        sbacklabel.setBackground( backgroundColor );
                        Canvas.setBackground( backgroundColor );
                        
                        mainFrame.setSize( CanvasWidth, CanvasHeight );
                        
                        LINEcnt = CIRCLEcnt = SQUAREcnt = RECTANGLEcnt = TRIANGLEcnt = POLYGONcnt = TETHLAcnt = FREELINEcnt = BigInteger.ZERO;
                    
                        int total = input.nextInt();
                    
                        for ( int k = 0; k < total; ++k ) {
                    
                            String shapeType = input.next();
                            
                            switch( shapeType ) {
                                case "LINE":
                                    {
                                        int color = input.nextInt();
                                        Point p1 = new Point( 0, 0 );
                                        p1.x = input.nextInt();
                                        p1.y = input.nextInt();
                                        Point p2 = new Point( 0, 0 );
                                        p2.x = input.nextInt();
                                        p2.y = input.nextInt();
                                        
                                        shapes.add( new Line( new Color( color ), p1, p2 ) );
                                        LINEcnt = LINEcnt.add( BigInteger.ONE );
                                        listModel.addElement( "Line" + LINEcnt.toString() );
                                    }
                                break;
                                case "CIRCLE":
                                    {
                                        int color = input.nextInt();
                                        boolean shade = input.nextBoolean();
                                        Point center = new Point( 0, 0 );
                                        center.x = input.nextInt();
                                        center.y = input.nextInt();
                                        int radius = input.nextInt();
                                        
                                        shapes.add( new Circle( drawingColor, center, radius, shade ) );
                                        CIRCLEcnt = CIRCLEcnt.add( BigInteger.ONE );
                                        listModel.addElement( "Circle" + CIRCLEcnt.toString() );
                                    }
                                break;
                                case "SQUARE":
                                    {
                                        int color = input.nextInt();
                                        boolean shade = input.nextBoolean();
                                        Point p1 = new Point( 0, 0 );
                                        p1.x = input.nextInt();
                                        p1.y = input.nextInt();
                                        Point p2 = new Point( 0, 0 );
                                        p2.x = input.nextInt();
                                        p2.y = input.nextInt();
                                        
                                        shapes.add( new Square( new Color( color ), p1, p2, shade ) );
                                        SQUAREcnt = SQUAREcnt.add( BigInteger.ONE );
                                        listModel.addElement( "Square"+ SQUAREcnt.toString() );
                                    }
                                break;
                                case "RECTANGLE":
                                    {
                                        int color = input.nextInt();
                                        boolean shade = input.nextBoolean();
                                        Point p1 = new Point( 0, 0 );
                                        p1.x = input.nextInt();
                                        p1.y = input.nextInt();
                                        Point p2 = new Point( 0, 0 );
                                        p2.x = input.nextInt();
                                        p2.y = input.nextInt();
                                    
                                        shapes.add( new Rectangle( new Color( color ), p1, p2, shade ) );
                                        RECTANGLEcnt = RECTANGLEcnt.add( BigInteger.ONE );
                                        listModel.addElement( "Rectangle" + RECTANGLEcnt.toString() );
                                    }
                                break;
                                case "TRIANGLE":
                                    {
                                        int color = input.nextInt();
                                        boolean shade = input.nextBoolean();
                                        Point p1 = new Point( 0, 0 );
                                        p1.x = input.nextInt();
                                        p1.y = input.nextInt();
                                        Point p2 = new Point( 0, 0 );
                                        p2.x = input.nextInt();
                                        p2.y = input.nextInt();
                                        Point p3 = new Point( 0, 0 );
                                        p3.x = input.nextInt();
                                        p3.y = input.nextInt();
                                        
                                        shapes.add( new Triangle( new Color( color ), p1, p2, p3, shade ) );
                                        TRIANGLEcnt = TRIANGLEcnt.add( BigInteger.ONE );
                                        listModel.addElement( "Triangle" + TRIANGLEcnt.toString() );
                                        just_added = true;
                                        cnt_clicked = 0;
                                    }
                                break;
                                case "POLYGON":
                                    {
                                        int color = input.nextInt();
                                        boolean shade = input.nextBoolean();
                                        int how = input.nextInt();
                                        
                                        ArrayList <Point> points = new ArrayList<Point>();
                                        
                                        for ( int i = 0; i < how; ++i ) {
                                            Point tmp = new Point( 0, 0 );
                                            tmp.x = input.nextInt();
                                            tmp.y = input.nextInt();
                                            points.add( tmp );
                                        }
                                        
                                        POLYGONcnt = POLYGONcnt.add( BigInteger.ONE );
                                        shapes.add( new Polygon( new Color( color ), points, shade ) );
                                        listModel.addElement( "Polygon"+ POLYGONcnt.toString() );
                                    }
                                break;
                                case "TETHLA":
                                    {
                                        int color = input.nextInt();
                                        int how = input.nextInt();
                                        
                                        ArrayList <Point> points = new ArrayList<Point>();
                                        
                                        for ( int i = 0; i < how; ++i ) {
                                            Point tmp = new Point( 0, 0 );
                                            tmp.x = input.nextInt();
                                            tmp.y = input.nextInt();
                                            points.add( tmp );
                                        }
                                        
                                        TETHLAcnt = TETHLAcnt.add( BigInteger.ONE );
                                        shapes.add( new Tethla( new Color( color ), points ) );
                                        listModel.addElement( "Tethla" + TETHLAcnt.toString() );
                                    }
                                break;
                                case "FREELINE":
                                    {
                                        int color = input.nextInt();
                                        int how = input.nextInt();
                                        
                                        ArrayList <Point> points = new ArrayList<Point>();
                                        
                                        for ( int i = 0; i < how; ++i ) {
                                            Point tmp = new Point( 0, 0 );
                                            tmp.x = input.nextInt();
                                            tmp.y = input.nextInt();
                                            points.add( tmp );
                                        }
                                    
                                        FREELINEcnt = FREELINEcnt.add( BigInteger.ONE );
                                        shapes.add( new FreeLine( new Color( color ), points ) );
                                        listModel.addElement( "Freeline" + FREELINEcnt.toString() );
                                    }
                                break;
                            }
                        }
                        
                    }catch( Exception fw ) {
                        fw.printStackTrace();
                    }finally {
                        if ( input != null ) {
                            input.close();
                        }
                    }
                    //
                    cnt_clicked = 0;
                    Canvas.repaint();
                    
                }
            }
            
        );
        
        Button deleteShape = new Button( "Delete" );
        
        deleteShape.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    if ( shapes.size() > 0 ) {
                        shapes.remove( ShapeEditList.getSelectedIndex() );//Element( "Line" + LINEcnt.toString() );
                        listModel.remove( ShapeEditList.getSelectedIndex() );//Element( "Line" + LINEcnt.toString() );
                        Canvas.repaint();
                    }
                }
                
            }
        
        );
        
        Button MoveUpShape = new Button( "Move Up" );
        
        MoveUpShape.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    if ( shapes.size() > 1 ) {
                    
                        int idx = ShapeEditList.getSelectedIndex();
                    
                        if ( idx == 0 ) {
                            return;
                        }
                    
                        Shape tmp = shapes.get( idx );
                        shapes.set( idx, shapes.get( idx-1 ) );
                        shapes.set( idx-1, tmp );
                    
                        listModel.insertElementAt( listModel.get(idx-1), idx+1 );
                        listModel.removeElementAt( idx-1 );
                        
                    }
                }
                
            }
        
        );
        
        Button MoveDownShape = new Button( "Move Down" );
        
        MoveDownShape.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                
                    if ( shapes.size() > 1 ) {
                    
                        int idx = ShapeEditList.getSelectedIndex();
                    
                        if ( idx == shapes.size()-1 ) {
                            return;
                        }
                    
                        Shape tmp = shapes.get( idx );
                        shapes.set( idx, shapes.get( idx+1 ) );
                        shapes.set( idx+1, tmp );
                    
                        listModel.insertElementAt( listModel.get(idx), idx+2 );
                        listModel.removeElementAt( idx );
                        ShapeEditList.setSelectedIndex( idx+1 );
                        
                    }
                }
                
            }
        
        );
        
        JScrollPane scrollpane = new JScrollPane( ShapeEditList );
        
        buttonEditPanel.setLayout( new GridLayout( 2, 4 ) );
        
        buttonEditPanel.add( new JLabel() );
        buttonEditPanel.add( newStage );
        buttonEditPanel.add( loadStage );
        buttonEditPanel.add( saveStage );
        buttonEditPanel.add( checkboxShade );
        buttonEditPanel.add( deleteShape );
        buttonEditPanel.add( MoveUpShape );
        buttonEditPanel.add( MoveDownShape );
        
        JPanel block_panel = new JPanel();
       
        block_panel.setLayout( new BorderLayout() );

        block_panel.add( buttonEditPanel );
       
        Button x_decrease = new Button( "<" );
        
        x_decrease.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
          
                    if ( ShapeEditList.getSelectedIndex() == -1 ) {
                        return;
                    }
          
                    Shape tmp_shape = shapes.get( ShapeEditList.getSelectedIndex() );
                            
                    //BEGIN
                    switch( tmp_shape.whatami() ) {
                        case LINE:
                             --( (Line)tmp_shape ).p1.x;
                             --( (Line)tmp_shape ).p2.x;
                        break;
                        case CIRCLE:
                             --( (Circle)tmp_shape ).center.x;
                             --( (Circle)tmp_shape ).center.x;
                        break;
                        case SQUARE:
                             --( (Square)tmp_shape ).p1.x;
                             --( (Square)tmp_shape ).p2.x;
                        break;
                        case RECTANGLE:
                             --( (Rectangle)tmp_shape ).p1.x;
                             --( (Rectangle)tmp_shape ).p2.x;
                        break;
                        case TRIANGLE:
                             --( (Triangle)tmp_shape ).p1.x;
                             --( (Triangle)tmp_shape ).p2.x;
                             --( (Triangle)tmp_shape ).p3.x;
                        break;
                        case POLYGON:
                            for ( int i = 0; i < ( (Polygon)tmp_shape ).points.size(); ++i ) {
                                --( (Polygon)tmp_shape ).points.get(i).x;
                            }
                        break;
                        case TETHLA:
                            for ( int i = 0; i < ( (Tethla)tmp_shape ).points.size(); ++i ) {
                                --( (Tethla)tmp_shape ).points.get(i).x;
                            }
                        break;
                        case FREELINE:
                            for ( int i = 0; i < ( (FreeLine)tmp_shape ).points.size(); ++i ) {
                                --( (FreeLine)tmp_shape ).points.get(i).x;
                            }
                        break;
                    }
                    //END
        
                    shapes.set( ShapeEditList.getSelectedIndex(), tmp_shape );
                    Canvas.repaint();
                    Canvas.invalidate();
                
                }
             });
        
        Button x_increase = new Button( ">" );
        
        x_increase.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
          
                    if ( ShapeEditList.getSelectedIndex() == -1 ) {
                        return;
                    }
          
                    Shape tmp_shape = shapes.get( ShapeEditList.getSelectedIndex() );
                            
                    //BEGIN
                    switch( tmp_shape.whatami() ) {
                        case LINE:
                             ++( (Line)tmp_shape ).p1.x;
                             ++( (Line)tmp_shape ).p2.x;
                        break;
                        case CIRCLE:
                             ++( (Circle)tmp_shape ).center.x;
                             ++( (Circle)tmp_shape ).center.x;
                        break;
                        case SQUARE:
                             ++( (Square)tmp_shape ).p1.x;
                             ++( (Square)tmp_shape ).p2.x;
                        break;
                        case RECTANGLE:
                             ++( (Rectangle)tmp_shape ).p1.x;
                             ++( (Rectangle)tmp_shape ).p2.x;
                        break;
                        case TRIANGLE:
                             ++( (Triangle)tmp_shape ).p1.x;
                             ++( (Triangle)tmp_shape ).p2.x;
                             ++( (Triangle)tmp_shape ).p3.x;
                        break;
                        case POLYGON:
                            for ( int i = 0; i < ( (Polygon)tmp_shape ).points.size(); ++i ) {
                                ++( (Polygon)tmp_shape ).points.get(i).x;
                            }
                        break;
                        case TETHLA:
                            for ( int i = 0; i < ( (Tethla)tmp_shape ).points.size(); ++i ) {
                                ++( (Tethla)tmp_shape ).points.get(i).x;
                            }
                        break;
                        case FREELINE:
                            for ( int i = 0; i < ( (FreeLine)tmp_shape ).points.size(); ++i ) {
                                ++( (FreeLine)tmp_shape ).points.get(i).x;
                            }
                        break;
                    }
                    //END
        
                    shapes.set( ShapeEditList.getSelectedIndex(), tmp_shape );
                    Canvas.repaint();
                
                }
             });
        
        Button y_decrease = new Button( "^" );
        
        y_decrease.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
          
                    if ( ShapeEditList.getSelectedIndex() == -1 ) {
                        return;
                    }
          
                    Shape tmp_shape = shapes.get( ShapeEditList.getSelectedIndex() );
                            
                    //BEGIN
                    switch( tmp_shape.whatami() ) {
                        case LINE:
                             --( (Line)tmp_shape ).p1.y;
                             --( (Line)tmp_shape ).p2.y;
                        break;
                        case CIRCLE:
                             --( (Circle)tmp_shape ).center.y;
                             --( (Circle)tmp_shape ).center.y;
                        break;
                        case SQUARE:
                             --( (Square)tmp_shape ).p1.y;
                             --( (Square)tmp_shape ).p2.y;
                        break;
                        case RECTANGLE:
                             --( (Rectangle)tmp_shape ).p1.y;
                             --( (Rectangle)tmp_shape ).p2.y;
                        break;
                        case TRIANGLE:
                             --( (Triangle)tmp_shape ).p1.y;
                             --( (Triangle)tmp_shape ).p2.y;
                             --( (Triangle)tmp_shape ).p3.y;
                        break;
                        case POLYGON:
                            for ( int i = 0; i < ( (Polygon)tmp_shape ).points.size(); ++i ) {
                                --( (Polygon)tmp_shape ).points.get(i).y;
                            }
                        break;
                        case TETHLA:
                            for ( int i = 0; i < ( (Tethla)tmp_shape ).points.size(); ++i ) {
                                --( (Tethla)tmp_shape ).points.get(i).y;
                            }
                        break;
                        case FREELINE:
                            for ( int i = 0; i < ( (FreeLine)tmp_shape ).points.size(); ++i ) {
                                --( (FreeLine)tmp_shape ).points.get(i).y;
                            }
                        break;
                    }
                    //END
        
                    shapes.set( ShapeEditList.getSelectedIndex(), tmp_shape );
                    Canvas.repaint();
                    Canvas.invalidate();
                
                }
             });
        
        Button y_increase = new Button( "v" );
        
        y_increase.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
          
                    if ( ShapeEditList.getSelectedIndex() == -1 ) {
                        return;
                    }
          
                    Shape tmp_shape = shapes.get( ShapeEditList.getSelectedIndex() );
                            
                    //BEGIN
                    switch( tmp_shape.whatami() ) {
                        case LINE:
                             ++( (Line)tmp_shape ).p1.y;
                             ++( (Line)tmp_shape ).p2.y;
                        break;
                        case CIRCLE:
                             ++( (Circle)tmp_shape ).center.y;
                             ++( (Circle)tmp_shape ).center.y;
                        break;
                        case SQUARE:
                             ++( (Square)tmp_shape ).p1.y;
                             ++( (Square)tmp_shape ).p2.y;
                        break;
                        case RECTANGLE:
                             ++( (Rectangle)tmp_shape ).p1.y;
                             ++( (Rectangle)tmp_shape ).p2.y;
                        break;
                        case TRIANGLE:
                             ++( (Triangle)tmp_shape ).p1.y;
                             ++( (Triangle)tmp_shape ).p2.y;
                             ++( (Triangle)tmp_shape ).p3.y;
                        break;
                        case POLYGON:
                            for ( int i = 0; i < ( (Polygon)tmp_shape ).points.size(); ++i ) {
                                ++( (Polygon)tmp_shape ).points.get(i).y;
                            }
                        break;
                        case TETHLA:
                            for ( int i = 0; i < ( (Tethla)tmp_shape ).points.size(); ++i ) {
                                ++( (Tethla)tmp_shape ).points.get(i).y;
                            }
                        break;
                        case FREELINE:
                            for ( int i = 0; i < ( (FreeLine)tmp_shape ).points.size(); ++i ) {
                                ++( (FreeLine)tmp_shape ).points.get(i).y;
                            }
                        break;
                    }
                    //END
        
                    shapes.set( ShapeEditList.getSelectedIndex(), tmp_shape );
                    Canvas.repaint();
                
                }
             });
        
        JPanel property_panel = new JPanel();
        property_panel.setLayout( new GridLayout( 4, 3 ) );
        
        JLabel tmp_label = new JLabel( "X" );
        property_panel.add( tmp_label );
        property_panel.add( x_decrease );
        property_panel.add( x_increase );
        
        y_ranger = new Scrollbar(Scrollbar.HORIZONTAL, 0, 10, 0, 300);
       
        tmp_label = new JLabel( "Y" );
        property_panel.add( tmp_label );
        property_panel.add( y_decrease );
        property_panel.add( y_increase );
        
        
        radius_ranger = new Scrollbar(Scrollbar.HORIZONTAL, 0, 10, 0, 300);
       
        tmp_label = new JLabel( "Radius" );
        property_panel.add( tmp_label );
        
        Button radius_decrease = new Button( "<" );
        
        radius_decrease.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
          
                    if ( ShapeEditList.getSelectedIndex() == -1 ) {
                        return;
                    }
          
                    Shape tmp_shape = shapes.get( ShapeEditList.getSelectedIndex() );
                            
                    //BEGIN
                    switch( tmp_shape.whatami() ) {
                        case CIRCLE:
                             --( (Circle)tmp_shape ).radius;
                        break;
                    }
                    //END
        
                    shapes.set( ShapeEditList.getSelectedIndex(), tmp_shape );
                    Canvas.repaint();
                
                }
             });
        
        Button radius_increase = new Button( "<" );
        
        radius_increase.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
          
                    if ( ShapeEditList.getSelectedIndex() == -1 ) {
                        return;
                    }
          
                    Shape tmp_shape = shapes.get( ShapeEditList.getSelectedIndex() );
                            
                    //BEGIN
                    switch( tmp_shape.whatami() ) {
                        case CIRCLE:
                             ++( (Circle)tmp_shape ).radius;
                        break;
                    }
                    //END
        
                    shapes.set( ShapeEditList.getSelectedIndex(), tmp_shape );
                    Canvas.repaint();
                
                }
             });
        
        property_panel.add( radius_decrease );
        property_panel.add( radius_increase );
        
        JButton shapecolor = new JButton( "Shape Color" );

        shapecolorlabel = new JLabel( );

        shapecolorlabel.setForeground( drawingColor );
        shapecolorlabel.setBackground( drawingColor );
        shapecolorlabel.setOpaque( true );
        
        property_panel.add( shapecolor );
        property_panel.add( shapecolorlabel );
        
        shapecolor.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    
                    if ( ShapeEditList.getSelectedIndex() == -1 ) {
                        return;
                    }
                    
                    shapeColor = JColorChooser.showDialog( null, "Choose shape color", Color.white);
                    shapecolorlabel.setForeground( shapeColor );
                    shapecolorlabel.setBackground( shapeColor );
                    
                    shapes.get( ShapeEditList.getSelectedIndex() ).setColor( shapeColor );
                    
                }
                
            }
        
        );
        
        shapeEditFrame.getContentPane().add( buttonEditPanel, BorderLayout.NORTH );//ShapeEditList );
        shapeEditFrame.getContentPane().add( property_panel, BorderLayout.CENTER );//ShapeEditList );
        shapeEditFrame.getContentPane().add( scrollpane, BorderLayout.SOUTH );//ShapeEditList );
        
        shapeEditFrame.pack();
      
        JButton scolor = new JButton( "Current Color" );

        scolorlabel = new JLabel( );

        scolorlabel.setForeground( drawingColor );
        scolorlabel.setBackground( drawingColor );
        scolorlabel.setOpaque( true );
        
        scolor.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    drawingColor = JColorChooser.showDialog( null, "Choose drawing color", Color.white);
                    scolorlabel.setForeground( drawingColor );
                    scolorlabel.setBackground( drawingColor );
                }
                
            }
        
        );
        
        JButton sbackcolor = new JButton( "Background Color" );
        
        sbacklabel = new JLabel( );

        sbacklabel.setForeground( drawingColor );
        sbacklabel.setBackground( drawingColor );
        sbacklabel.setOpaque( true );
        
        sbacklabel.setForeground( backgroundColor );
        sbacklabel.setBackground( backgroundColor );
        
        sbackcolor.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    backgroundColor = JColorChooser.showDialog( null, "Choose background color", Color.white);
                    sbacklabel.setForeground( backgroundColor );
                    sbacklabel.setBackground( backgroundColor );
                    Canvas.setBackground( backgroundColor );
                }
                
            }
        
        );
        
        Canvas = new JPanel() {
        
            protected void paintComponent( Graphics g ) {
                
                super.paintComponent( g );
            
                maxX = maxY = 0;
                
                for ( Shape tmp_shape : shapes ) {
                
                    Color tmp_color = tmp_shape.color;
                
                    switch( tmp_shape.whatami() ) {
                        case LINE:
                            drawbresenhamLine( g, (Line)tmp_shape );
                        break;
                        case CIRCLE:
                        
                            //circle color-fill
                            if ( ( (Circle)tmp_shape ).getShade() ) {
                                ArrayList<Point> Coords = new ArrayList<Point>();
                            
                                ArrayList<Point> tmp_list = GetbresenhamCirclePoints( (Circle)tmp_shape );
                                
                                for ( int j = 0; j < tmp_list.size(); ++j ) {
                                    Coords.add( tmp_list.get(j).Copy() );
                                }
                                
                                Collections.sort( Coords, PointCompare );
                              
                                for ( int i = 1; i < Coords.size(); ++i ) {
                                    Point p1 = Coords.get(i-1);
                                    Point p2 = Coords.get( i );
                                    Line line = new Line( tmp_color, new Point( p1.x, p1.y ), new Point( p2.x, p2.y ) );
                                    drawbresenhamLine( g, (Line)line );
                                }
                                
                            }
                        
                            drawbresenhamCircle( g, (Circle)tmp_shape );
                            
                        break;
                        case SQUARE:
                            {
                                int x = ( (Square)tmp_shape ).p1.x;
                                int y = ( (Square)tmp_shape ).p1.y;
                                
                                int width = ( (Square)tmp_shape ).p2.x - x;
                                int height = ( (Square)tmp_shape ).p2.y - y;
                                
                                width = ( (width * height) > 0 ? 1 : - 1 ) * height;
                                
                                if ( ( (Square)tmp_shape ).getShade() == true ) {
                                    for ( int j = 0; j <= Math.abs(height); ++j ) {
                                    Line tmp = new Line( tmp_color, new Point(x,y+j*sign(height) ), new Point(x+width,y+j*sign(height) ) );
                                    drawbresenhamLine( g, (Line)tmp );
                                    }                            
                                }else {
                                
                                    Line top = new Line( tmp_color, new Point(x,y), new Point(x+width,y) );
                                    Line bottom = new Line( tmp_color, new Point(x,y+height), new Point(x+width,y+height) );
                                    Line left = new Line( tmp_color, new Point(x,y), new Point(x,y+height) );
                                    Line right = new Line( tmp_color, new Point(x+width,y), new Point(x+width,y+height) );
                                    
                                    drawbresenhamLine( g, (Line)top );
                                    drawbresenhamLine( g, (Line)bottom );
                                    drawbresenhamLine( g, (Line)left );
                                    drawbresenhamLine( g, (Line)right );
                                }
                                
                            }
                        break;
                        case RECTANGLE:
                        
                            int x = ( (Rectangle)tmp_shape ).p1.x;
                            int y = ( (Rectangle)tmp_shape ).p1.y;
                            
                            int width = ( (Rectangle)tmp_shape ).p2.x - x;
                            int height = ( (Rectangle)tmp_shape ).p2.y - y;
                            
                            if ( ( (Rectangle)tmp_shape ).getShade() == true ) {
                                for ( int j = 0; j <= Math.abs(height); ++j ) {
                                    Line tmp = new Line( tmp_color, new Point(x,y+j*sign(height) ), new Point(x+width,y+j*sign(height) ) );
                                    drawbresenhamLine( g, (Line)tmp );
                                }                            
                            }else {
                            
                                Line top = new Line( tmp_color, new Point(x,y), new Point(x+width,y) );//, (Rectangle)tmp_shape.p1.x );
                                Line bottom = new Line( tmp_color, new Point(x,y+height), new Point(x+width,y+height) );
                                Line left = new Line( tmp_color, new Point(x,y), new Point(x,y+height) );
                                Line right = new Line( tmp_color, new Point(x+width,y), new Point(x+width,y+height) );
                                
                                drawbresenhamLine( g, (Line)top );
                                drawbresenhamLine( g, (Line)bottom );
                                drawbresenhamLine( g, (Line)left );
                                drawbresenhamLine( g, (Line)right );
                                
                            }
                            
                        break;
                        case TRIANGLE:
                            Line l1 = new Line( tmp_color, new Point( ( (Triangle)tmp_shape ).p1.x, ( (Triangle)tmp_shape ).p1.y ), new Point( ( (Triangle)tmp_shape ).p2.x, ( (Triangle)tmp_shape ).p2.y ) );
                            Line l2 = new Line( tmp_color, new Point( ( (Triangle)tmp_shape ).p2.x, ( (Triangle)tmp_shape ).p2.y ), new Point( ( (Triangle)tmp_shape ).p3.x, ( (Triangle)tmp_shape ).p3.y ) );
                            Line l3 = new Line( tmp_color, new Point( ( (Triangle)tmp_shape ).p3.x, ( (Triangle)tmp_shape ).p3.y ), new Point( ( (Triangle)tmp_shape ).p1.x, ( (Triangle)tmp_shape ).p1.y ) );
                            drawbresenhamLine( g, (Line)l1 );
                           
                            drawbresenhamLine( g, (Line)l2 );
                            
                            drawbresenhamLine( g, (Line)l3 );
                            
                            //triangle color-fill
                            if ( ( (Triangle)tmp_shape ).getShade() ) {
                                ArrayList<Point> Coords = new ArrayList<Point>();
                            
                                ArrayList<Point> tmp_list = GetbresenhamLinePoints( l1 );
                                
                                for ( int j = 0; j < tmp_list.size(); ++j ) {
                                    Coords.add( tmp_list.get(j).Copy() );
                                }
                                
                                tmp_list = GetbresenhamLinePoints( l2 );
                                
                                for ( int j = 0; j < tmp_list.size(); ++j ) {
                                    Coords.add( tmp_list.get(j).Copy() );
                                }
                                
                                tmp_list = GetbresenhamLinePoints( l3 );
                                
                                for ( int j = 0; j < tmp_list.size(); ++j ) {
                                    Coords.add( tmp_list.get(j).Copy() );
                                }
                                
                                Collections.sort( Coords, PointCompare );
                              
                                for ( int i = 0; i < Coords.size(); ++i ) {
                                    Point p1 = Coords.get(i);
                                    Point p2 = Coords.get( (i+1) % ( Coords.size() ) );
                                    Line line = new Line( tmp_color, new Point( p1.x, p1.y ), new Point( p2.x, p2.y ) );
                                    drawbresenhamLine( g, (Line)line );
                                }
                                
                            }
                            
                        break;
                        case POLYGON:
                        
                            for ( int i = 1; i <= ( (Polygon)tmp_shape ).points.size(); ++i ) {
                                
                                Point p1 = ( (Polygon)tmp_shape ).points.get(i-1);
                                Point p2 = ( (Polygon)tmp_shape ).points.get(i%( (Polygon)tmp_shape ).points.size());
                            
                                Line line = new Line( tmp_color, new Point( p1.x, p1.y ), new Point( p2.x, p2.y ) );
                                drawbresenhamLine( g, (Line)line );
                            }
                            
                            //polygon color-fill
                            if ( ( (Polygon)tmp_shape ).getShade() == true ) {
                            
                                ArrayList<Point> Coords = new ArrayList<Point>();
                                
                                for ( int i = 1; i <= ( (Polygon)tmp_shape ).points.size(); ++i ) {
                                    
                                    Point p1 = ( (Polygon)tmp_shape ).points.get(i-1).Copy();
                                    Point p2 = ( (Polygon)tmp_shape ).points.get(i%( (Polygon)tmp_shape ).points.size()).Copy();
                                    
                                    Coords.add( p1.Copy() );
                                    
                                    Coords.add( p2.Copy() );
                                   
                                    Line line = new Line( tmp_color, new Point( p1.x, p1.y ), new Point( p2.x, p2.y ) );
                                    
                                    ArrayList<Point> tmp_list = GetbresenhamLinePoints( line );
                                    
                                    for ( int j = 0; j < tmp_list.size(); ++j ) {
                                        Coords.add( tmp_list.get(j).Copy() );
                                    }
                                    
                                }
                                
                                Collections.sort( Coords, PointCompare );
                              
                                for ( int i = 0; i < Coords.size(); ++i ) {
                                    Point p1 = Coords.get(i);
                                    Point p2 = Coords.get( (i+1) % ( Coords.size() ) );
                                    Line line = new Line( tmp_color, new Point( p1.x, p1.y ), new Point( p2.x, p2.y ) );
                                    drawbresenhamLine( g, (Line)line );
                                }
                                
                            }
                            
                        break;
                        case TETHLA:
                        
                            for ( int i = 1; i < ( (Tethla)tmp_shape ).points.size(); ++i ) {
                                
                                Point p1 = ( (Tethla)tmp_shape ).points.get(i-1);
                                Point p2 = ( (Tethla)tmp_shape ).points.get(i);
                            
                                Line line = new Line( tmp_color, new Point( p1.x, p1.y ), new Point( p2.x, p2.y ) );
                                drawbresenhamLine( g, (Line)line );
                            }
                            
                        break;
                        case FREELINE:
                        
                            if ( ( (FreeLine)tmp_shape ).points.size() == 1 ) {
                                Point p = ( (FreeLine)tmp_shape ).points.get(0);
                                g.setColor( Color.black );
                                SetPixel( g, (Point)p );
                            }else {
                        
                                for ( int i = 1; i < ( (FreeLine)tmp_shape ).points.size(); ++i ) {
                                    Point p1 = ( (FreeLine)tmp_shape ).points.get(i-1);
                                    Point p2 = ( (FreeLine)tmp_shape ).points.get(i);
                                    Line tmp = new Line( tmp_color, p1, p2 );
                                    drawbresenhamLine( g, (Line)tmp );
                                }
                                
                            }
                            
                        break;
                    }
                }
        
                if ( maxX > CanvasWidth ) {
                    Canvasscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                }else {
                    Canvasscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                }
                
                if ( maxY > CanvasHeight ) {
                    Canvasscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                }else {
                    Canvasscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
                
                Canvas.setPreferredSize( new Dimension( Math.max( maxX, CanvasWidth ), Math.max( maxY, CanvasHeight ) ) );
                
                if ( just_added == true ) {//X.get(0) == -1 && Y.get(0) == -1 ) {
                    return;
                }
                
                switch( cur_state ) {
                    case LINE:
                        if ( X.size() > 1 && Y.size() > 1 ) {
                            Line line = new Line( drawingColor, new Point(X.get(0),Y.get(0)), new Point(X.get(1),Y.get(1)) );                                
                            drawbresenhamLine( g, line );
                        }
                    break;
                    case CIRCLE:
                        if ( X.size() > 1 && Y.size() > 1 ) {
                            Circle circle = new Circle( drawingColor, new Point(X.get(0),Y.get(0)), (int)Math.sqrt( (X.get(1)-X.get(0))*(X.get(1)-X.get(0)) + (Y.get(1)-Y.get(0))*(Y.get(1)-Y.get(0)) ) );                                
                            drawbresenhamCircle( g, circle );
                        }
                    break;
                    case SQUARE:
                        {
                            if ( X.size() > 1 && Y.size() > 1 ) {
                        
                                int x = X.get(0);
                                int y = Y.get(0);
                                
                                int width = X.get(1) - X.get(0);
                                int height = Y.get(1) - Y.get(0);
                                
                                width = ( (width * height) > 0 ? 1 : - 1 ) * height;
                                
                                Line top = new Line( drawingColor, new Point(x,y), new Point(x+width,y) );
                                Line bottom = new Line( drawingColor, new Point(x,y+height), new Point(x+width,y+height) );
                                Line left = new Line( drawingColor, new Point(x,y), new Point(x,y+height) );
                                Line right = new Line( drawingColor, new Point(x+width,y), new Point(x+width,y+height) );
                                
                                drawbresenhamLine( g, (Line)top );
                                drawbresenhamLine( g, (Line)bottom );
                                drawbresenhamLine( g, (Line)left );
                                drawbresenhamLine( g, (Line)right );
                            }
                        }
                        break;
                    case RECTANGLE:
                        if ( X.size() > 1 && Y.size() > 1 ) {
                        
                            int x = X.get(0);
                            int y = Y.get(0);
                            
                            int width = X.get(1) - X.get(0);
                            int height = Y.get(1) - Y.get(0);
                            
                            Line top = new Line( drawingColor, new Point(x,y), new Point(x+width,y) );
                            Line bottom = new Line( drawingColor, new Point(x,y+height), new Point(x+width,y+height) );
                            Line left = new Line( drawingColor, new Point(x,y), new Point(x,y+height) );
                            Line right = new Line( drawingColor, new Point(x+width,y), new Point(x+width,y+height) );
                            
                            drawbresenhamLine( g, (Line)top );
                            drawbresenhamLine( g, (Line)bottom );
                            drawbresenhamLine( g, (Line)left );
                            drawbresenhamLine( g, (Line)right );
                        }
                    break;
                    case TRIANGLE:
                    case POLYGON:
                        if ( X.size() > cnt_clicked && Y.size() > cnt_clicked ) {
                            for ( int i = 1; i <= cnt_clicked; ++i ) {
                                Line tmp_line = new Line( drawingColor, new Point(X.get(i-1),Y.get(i-1)), new Point(X.get(i),Y.get(i)) );
                                drawbresenhamLine( g, (Line)tmp_line );
                            }
                        }
                    break;
                    case TETHLA:
                        if ( X.size() > cnt_clicked && Y.size() > cnt_clicked ) {
                            for ( int i = 1; i <= cnt_clicked; ++i ) {
                            
                                if ( i >= 4 ) {
                                    if ( ccw( new Point( X.get( i-2 ), Y.get( i-2 ) ), new Point( X.get(i-1), Y.get(i-1) ), new Point(X.get( i-3 ),Y.get( i-3 ) ) ) == 0 ) {
                                        System.out.println( "Invalid shape" );
                                        cnt_clicked = 0;
                                        break;
                                    }
                                }
                            
                                Line tmp_line = new Line( drawingColor, new Point(X.get(i-1),Y.get(i-1)), new Point(X.get(i),Y.get(i)) );
                                drawbresenhamLine( g, (Line)tmp_line );
                            }
                        }
                    break;
                    case FREELINE:
                    
                        if ( cnt_clicked == 0 ) {
                            if ( X.size() > 0 && Y.size() > 0 ) {
                                Point tmp_point = new Point( X.get(0),Y.get(0) );
                                g.setColor( Color.black );
                                SetPixel( g, (Point)tmp_point );
                            }
                        }else {
                    
                            if ( X.size() >= cnt_clicked && Y.size() >= cnt_clicked ) {
                            
                                for ( int i = 1; i < cnt_clicked; ++i ) {
                                    Point tmp_point1 = new Point( X.get(i-1),Y.get(i-1) );
                                    Point tmp_point2 = new Point( X.get(i),Y.get(i) );
                                    Line tmp_line = new Line( drawingColor, tmp_point1, tmp_point2 );
                                    drawbresenhamLine( g, (Line)tmp_line );
                                    
                                }

                            }
                                
                        }
                        
                    break;
                }
            
                
            }
        
        };
        
        mainFrame.addComponentListener (
            new ComponentAdapter( ) {
                public void componentResized(ComponentEvent e) {
                    Canvas.repaint();
                }
            }
        );
        
        
        Canvasscrollpane = new JScrollPane( Canvas );
        Canvasscrollpane.setPreferredSize( new Dimension( CanvasHeight, CanvasWidth ) );
        
        mainFrame.getContentPane().add( Canvasscrollpane );
        mainFrame.pack();
        
        JButton sline = new JButton( "Line" );
        
        sline.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    cur_state = ShapeType.LINE;
                    cnt_clicked = 0;
                }
                
            }
        
        );
        
        JButton scircle = new JButton( "Circle" );
        
        scircle.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    cur_state = ShapeType.CIRCLE;
                    cnt_clicked = 0;
                }
                
            }
        
        );
        
        JButton ssquare = new JButton( "Square" );
        
        ssquare.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    cur_state = ShapeType.SQUARE;
                    cnt_clicked = 0;
                }
                
            }
        
        );
        
        JButton srect = new JButton( "Rectangle" );
        
        srect.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    cur_state = ShapeType.RECTANGLE;
                    cnt_clicked = 0;
                }
                
            }
        
        );
        
        JButton striangle = new JButton( "Triangle" );
        
        striangle.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    cur_state = ShapeType.TRIANGLE;
                    cnt_clicked = 0;
                }
                
            }
        
        );
        
        JButton spolygon = new JButton( "Polygon" );
        
        spolygon.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    cur_state = ShapeType.POLYGON;
                    cnt_clicked = 0;
                }
                
            }
        
        );
        
        JButton stethla = new JButton( "Tethla" );
        
        stethla.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    cur_state = ShapeType.TETHLA;
                    cnt_clicked = 0;
                }
                
            }
        
        );
        
        JButton sfreeline = new JButton( "Freeline" );
        
        sfreeline.addActionListener (
        
            new ActionListener( ) {
                
                public void actionPerformed( ActionEvent e ) {
                    cur_state = ShapeType.FREELINE;
                    cnt_clicked = 0;
                }
                
            }
        
        );
        
        shapeCanvas = new JPanel();
        shapeCanvas.setLayout( new GridLayout( 6, 2 ) );
        
        shapeCanvas.add( sline );
        shapeCanvas.add( scircle );
        shapeCanvas.add( ssquare );
        shapeCanvas.add( srect );
        shapeCanvas.add( striangle );
        shapeCanvas.add( spolygon );
        shapeCanvas.add( stethla );
        shapeCanvas.add( sfreeline );
        
        shapeCanvas.add( scolor );
        shapeCanvas.add( scolorlabel );
        shapeCanvas.add( sbackcolor );
        shapeCanvas.add( sbacklabel );
        
        shapeCanvas.setVisible( true );
        
        shapeFrame.add( shapeCanvas );
        shapeFrame.pack();
        
        Canvas.setBackground( backgroundColor );
        
        Canvas.addMouseListener(
            
            new MouseAdapter() {
                    
                public void mousePressed( MouseEvent e ) {
                    
                    if ( SwingUtilities.isRightMouseButton( e ) ) {
                        right_clicked = true;
                        return;
                    }else {
                        right_clicked = false;
                    }
                    
                    if ( cnt_clicked == 0 ) {
                        AddIfNecessary( cnt_clicked );
                        X.set( cnt_clicked, e.getX() );
                        Y.set( cnt_clicked, e.getY() );
                        ++cnt_clicked;
                        just_added = false;
                    }
                    
                }
                
                public void mouseReleased( MouseEvent e ) {
                    
                    AddIfNecessary( cnt_clicked );
                    
                    X.set( cnt_clicked, e.getX() );
                    Y.set( cnt_clicked, e.getY() );
                    
                    AddIfNecessary( cnt_clicked+1 );
                    
                    X.set( cnt_clicked+1, e.getX() );
                    Y.set( cnt_clicked+1, e.getY() );
                    
                    ++cnt_clicked;
                    
                    just_added = false;
                    
                    Canvas.setPreferredSize( new Dimension( Math.max( maxX, CanvasWidth ), Math.max( maxY, CanvasHeight ) ) );
                
                    switch( cur_state ) {
                        case LINE:
                            shapes.add( new Line( drawingColor, new Point(X.get(0),Y.get(0)), new Point(X.get(1),Y.get(1)) ) );
                            LINEcnt = LINEcnt.add( BigInteger.ONE );
                            listModel.addElement( "Line" + LINEcnt.toString() );
                            just_added = true;
                            cnt_clicked = 0;
                        break;
                        case CIRCLE:
                            shapes.add( new Circle( drawingColor, new Point(X.get(0),Y.get(0)), (int)Math.sqrt( (X.get(1)-X.get(0))*(X.get(1)-X.get(0)) + (Y.get(1)-Y.get(0))*(Y.get(1)-Y.get(0)) ), checkboxShade.getState() ) );
                            CIRCLEcnt = CIRCLEcnt.add( BigInteger.ONE );
                            listModel.addElement( "Circle" + CIRCLEcnt.toString() );
                            just_added = true;
                            cnt_clicked = 0;
                        break;
                        case SQUARE:
                            shapes.add( new Square( drawingColor, new Point(X.get(0),Y.get(0)), new Point(X.get(1),Y.get(1)), checkboxShade.getState() ) );
                            SQUAREcnt = SQUAREcnt.add( BigInteger.ONE );
                            listModel.addElement( "Square"+ SQUAREcnt.toString() );
                            just_added = true;
                            cnt_clicked = 0;
                        break;
                        case RECTANGLE:
                            shapes.add( new Rectangle( drawingColor, new Point(X.get(0),Y.get(0)), new Point(X.get(1),Y.get(1)), checkboxShade.getState() ) );
                            RECTANGLEcnt = RECTANGLEcnt.add( BigInteger.ONE );
                            listModel.addElement( "Rectangle" + RECTANGLEcnt.toString() );
                            just_added = true;
                            cnt_clicked = 0;
                        break;
                        case TRIANGLE:
                            if ( cnt_clicked >= 3 ) {
                                shapes.add( new Triangle( drawingColor, new Point(X.get(0),Y.get(0)), new Point(X.get(1),Y.get(1)), new Point(X.get(2),Y.get(2)), checkboxShade.getState() ) );
                                TRIANGLEcnt = TRIANGLEcnt.add( BigInteger.ONE );
                                listModel.addElement( "Triangle" + TRIANGLEcnt.toString() );
                                just_added = true;
                                cnt_clicked = 0;
                            }
                        break;
                        case POLYGON:
                            if ( cnt_clicked >= 3 && right_clicked ) {
                                ArrayList <Point> points = new ArrayList<Point>();
                                
                                for ( int i = 0; i < cnt_clicked-1; ++i )
                                    points.add( new Point( X.get(i), Y.get(i) ) );
                                
                                POLYGONcnt = POLYGONcnt.add( BigInteger.ONE );
                                shapes.add( new Polygon( drawingColor, points, checkboxShade.getState() ) );
                                listModel.addElement( "Polygon"+ POLYGONcnt.toString() );
                                just_added = true;
                                cnt_clicked = 0;
                                right_clicked = false;
                            }
                        break;
                        case TETHLA:
                            if ( cnt_clicked >= 3 && right_clicked ) {
                                ArrayList <Point> points = new ArrayList<Point>();
                                
                                for ( int i = 0; i < cnt_clicked-1; ++i )
                                    points.add( new Point( X.get(i), Y.get(i) ) );
                                
                                TETHLAcnt = TETHLAcnt.add( BigInteger.ONE );
                                shapes.add( new Tethla( drawingColor, points ) );
                                listModel.addElement( "Tethla" + TETHLAcnt.toString() );
                                just_added = true;
                                cnt_clicked = 0;
                                right_clicked = false;
                            }
                        break;
                        case FREELINE:
                            ArrayList <Point> points = new ArrayList<Point>();
                                
                            for ( int i = 0; i < cnt_clicked; ++i )
                                points.add( new Point( X.get(i), Y.get(i) ) );
                        
                            FREELINEcnt = FREELINEcnt.add( BigInteger.ONE );
                            shapes.add( new FreeLine( drawingColor, points ) );
                            listModel.addElement( "Freeline" + FREELINEcnt.toString() );
                            just_added = true;
                            cnt_clicked = 0;
                        
                        break;
                    }
                    
                    Canvas.repaint();
                    
                }
                
            }
        
        );
        
        Canvas.addMouseMotionListener(
            
            new MouseMotionAdapter() {
                
                public void mouseClicked( MouseEvent e ) {
                    
                }
                
                public void mouseMoved( MouseEvent e ) {
                
                }
                
                public void mouseDragged( MouseEvent e ) {
                
                    AddIfNecessary( cnt_clicked );
                
                    X.set( cnt_clicked, e.getX() );
                    Y.set( cnt_clicked, e.getY() );
                    
                    if ( cur_state == ShapeType.FREELINE ) {
                        ++cnt_clicked;
                    }
                
                    Canvas.repaint();
                    Canvas.invalidate();
                
                }
                
            }
        
        );
        
        mainFrame.setVisible( true );
    
    }

}