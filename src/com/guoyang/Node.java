package com.guoyang;

import java.util.Comparator;

/**
 * @program: Alogrithm
 * @description:
 * @author: guoyang
 * @create: 2019-04-24 19:50
 **/
public class Node {

    int x;// X坐标
     int y;// Y坐标
   Node parentNode;// 父类节点
    int g;// 当前点到起点的移动耗费
    int h;// 当前点到终点的移动耗费，即曼哈顿距离|x1-x2|+|y1-y2|(忽略障碍物)
   int f;// f=g+h

    public Node(int x, int y, Node parentNode) {
        this.x = x;
        this.y = y;
        this.parentNode = parentNode;
    }

    public int compareTo(Node candidate) {
        return this.getF() - candidate.getF();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public String toString() {
        return "(" + x + "," + y + "," + f + ")";
    }
}

// 节点比较类
class NodeFComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return o1.getF() - o2.getF();
    }

}

