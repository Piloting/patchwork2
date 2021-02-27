package ru.pilot.patchwork.service.block;

public enum PolygonType {
    
    LINE     (new double[] { 0,0,    10,10                 }),
    TRIANGLE (new double[] { 0,0,    0,10,   10,0          }),
    RECT     (new double[] { 0,0,    0,10,   10,10,  10,0  }),
    RHOMBUS  (new double[] { 0,5,    5,10,   10,5,   5,0   }),
    TRAPEZOID(new double[] { 0,0,    0,10,   10,8,   10,8  });

    private final double[] simplePoints;

    PolygonType(double[] points){
        simplePoints = points;
    }
    
    public double[] getSimplePoints(){
        return simplePoints; //Arrays.copyOf(simplePoints, simplePoints.length);
    }
    
}
