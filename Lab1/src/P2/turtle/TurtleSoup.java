/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) 
    {
        //throw new RuntimeException("implement me!");
    	for(int i=0;i<4;i++)
    	{
        	turtle.forward(sideLength);
        	turtle.turn(90);
    	}
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        //throw new RuntimeException("implement me!");
    	double angle = (sides-2)*180.0/sides;
    	return angle;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        //throw new RuntimeException("implement me!");
    	return (int) Math.round(360 / (180 - angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        //throw new RuntimeException("implement me!");
    	for(int i=0;i<sides;i++)
    	{
    		turtle.forward(sideLength);
    		turtle.turn(180.0-calculateRegularPolygonAngle(sides));
    	}
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        //throw new RuntimeException("implement me!");
    	//使用向量的夹角方法计算方位角
    	//首先表示出两个向量，记为a和b，a=(aX,aY),b=(bX,bY);
    	double aX = Math.sin(Math.toRadians(currentBearing));
    	double aY = Math.cos(Math.toRadians(currentBearing));
    	double bX = targetX - currentX;
    	double bY = targetY - currentY;
    	//使用向量夹角公式计算夹角angle的cos值
    	double dotProduct = aX*bX + aY*bY;
    	double magnitudeA = Math.sqrt(aX*aX+aY*aY);
    	double magnitudeB = Math.sqrt(bX*bX+bY*bY);
        // 计算夹角
        double cosAngle = dotProduct / (magnitudeA * magnitudeB);
        double angle = Math.acos(cosAngle); // 结果以弧度为单位
        // 将弧度转换为度
        double degrees = Math.toDegrees(angle);
        // 将角度转换到 0 到 360 度之间
        if (bX * aY - bY * aX < 0) {
            degrees = 360 - degrees;
        }
        return degrees;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        //throw new RuntimeException("implement me!");
    	List<Double> bearingsList = new ArrayList<Double>();
    	double currentBearing = 0;
    	double result = 0;
    	if(xCoords.size()==0) return bearingsList;//点数为0，则列表大小为0
    	for(int i=0;i<xCoords.size()-1;i++)
    	{
    		result = calculateBearingToPoint(currentBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1));
    		bearingsList.add(result);
    		currentBearing = currentBearing + result;
    		if(currentBearing>360)
    		{
    			currentBearing = currentBearing - 360;
    		}
    	}
    	return bearingsList;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        //throw new RuntimeException("implement me!");
    	//首先考虑特殊情况，如果集合中有0，1，2，3个点，那么凸包即为它本身
    	if(points.size()<=3)
    	{
    		return points;
    	}
    	//，若在三个点以上，则选出最左下的点，其必在凸包中
    	Point left = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    	for(Point point:points)
    	{
    		if(point.x()<=left.x()&&point.y()<=left.y())
    		{
    			left = point;
    		}
    	}
    	//初始化变量
    	Point endPoint = null;//末端点
    	Point pointOnHull = left;//当前能找到的最左侧点,也是最先加入凸包中的点
    	Set<Point> convexHullPoints = new HashSet<Point>();//凸包集合
    	double v1X,v1Y,v2X,v2Y;//两个向量的坐标表示。用于判断左侧右侧
    	Set<Point> pointsOnHull = new HashSet<Point>();//需要加入凸包的点集合，存储凸包边缘共线的多个点
    	Boolean isSet = false;//是否出现凸包边缘共线的情况
    	Point pastPoint = null;//存储上一个加入凸包的顶点，用于共线情况
    	while(endPoint!=left)
    	{
    		//判断共线情况
    		if(!isSet)
    		{
    			convexHullPoints.add(pointOnHull);
    		}
    		else //这种情况下，遍历该集合，找出离pastPoint最远的点加入
    		{
    			double max = 0;
    			Point farPoint = null;
				for(Point p:pointsOnHull)
				{
					double diff = Math.abs(p.x()-pastPoint.x())+Math.abs(p.y()-pastPoint.y());
					if(diff > max)
					{
						max = diff;
						farPoint = p;
					}
				}
				pointOnHull = farPoint;
				convexHullPoints.add(pointOnHull);
			}
    		isSet = false;//这里必须重置isSet为false，否则上次isSet的值可能会保留
    		pointsOnHull.clear();//最后需要清空该集合
    		//下面需要选出一点作为endpoint，但该点不能与pointHull重合
    		//从集合中抽出一点需要用迭代器，但不能重复使用，以免无法从初始位置开始
    		Iterator<Point> iterator = points.iterator();
    		if(iterator.hasNext())
    		{
    			endPoint = iterator.next();
    			if(endPoint==pointOnHull&&iterator.hasNext())
    			{
    				endPoint = iterator.next();
    			}
    		}
    		for(Point p:points)
    		{
    			//计算向量
    			v1X = endPoint.x()-pointOnHull.x();
    			v1Y = endPoint.y()-pointOnHull.y();
    			v2X = p.x()-pointOnHull.x();
    			v2Y = p.y()-pointOnHull.y();
    			//计算叉积
    			double crossProduct = v1X*v2Y - v2X*v1Y;
    			//叉积大于0，则p位于当前线段左侧，则更新endpoint
    			if(crossProduct>0)
    			{
    				endPoint = p;
    				//如果出现了左侧点，则pointsOnHull集合作废
    				isSet = false;
    				pointsOnHull.clear();
    			}
    			//这里需要判断p是否与另外两点重合。若重合，则忽略此情况
    			else if(p==endPoint||p==pointOnHull)
    			{
    				
    			}
    			//此种情况说明三点共线。此时需要待定。先将共线的点加入集合pointsOnHull
    			else if(crossProduct==0)
    			{
    				pointsOnHull.add(endPoint);
    				pointsOnHull.add(p);
    				isSet = true;
    			}
    		}
    		pastPoint = pointOnHull;
    		pointOnHull = endPoint;
    	}
    	//最终，endPoint会回到最初的left点，此时完成了凸包的求解。
    	return convexHullPoints;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        //throw new RuntimeException("implement me!");
    	//画出花蕊
    	turtle.color(PenColor.ORANGE);
    	for(int i=0;i<12;i++)
    	{
    		drawRegularPolygon(turtle, 5, 40);
    		turtle.turn(30);
    	}

    	//均匀的画出12个粉色内花瓣
    	turtle.color(PenColor.PINK);
    	for(int i=0;i<12;i++)
    	{
    		//画出一个花瓣
        	for(int j=0;j<2;j++)
        	{
        		turtle.forward(20);
        		turtle.turn(12);
        		turtle.forward(20);
        		turtle.turn(18);
        		turtle.forward(40);
        		turtle.turn(18);
        		turtle.forward(20);
        		turtle.turn(12);
        		turtle.forward(20);
        		turtle.turn(120);
        	}
        	turtle.turn(360/12.0);
    	}
    	//均匀的画出12个红色外花瓣
    	turtle.color(PenColor.RED);
    	for(int i=0;i<12;i++)
    	{
    		//画出一个花瓣
        	for(int j=0;j<2;j++)
        	{
        		turtle.forward(40);
        		turtle.turn(12);
        		turtle.forward(40);
        		turtle.turn(18);
        		turtle.forward(80);
        		turtle.turn(18);
        		turtle.forward(40);
        		turtle.turn(12);
        		turtle.forward(40);
        		turtle.turn(120);
        	}
        	turtle.turn(360/12.0);
    	}
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();

    }

}
