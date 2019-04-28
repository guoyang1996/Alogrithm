package com.guoyang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AStarJFrame extends JFrame implements KeyListener {
    int rows;
    int cols;
    int[][] color;
    final int GRID_SIZE = 10;
    Node start;
    Node end;
    Node current;
    List<Node> path = new ArrayList<>();
    List<Node> openList = new ArrayList<Node>();// 开启列表
    List<Node> closeList = new ArrayList<Node>();// 关闭列表
    boolean isEnd = false;

    public AStarJFrame(int rows, int cols, int[][] color, int startX, int startY, int endX, int endY) {
        this.rows = rows;
        this.cols = cols;
        this.color = color;
        start = new Node(startX, startY, null);
        end = new Node(endX, endY, null);
        current = start;

        openList.add(start);
        setSize(cols * GRID_SIZE + 100, rows * GRID_SIZE + 100);
        setLocation(new Point(400, 150));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        int[][] color = ReadDataFromFile.readIntArrays("data\\map.txt");
        AStarJFrame j = new AStarJFrame(20, 40, color, 4, 10, 35, 0);
//        int[][] color = ReadDataFromFile.readIntArrays("data\\map0.txt");
//        AStarJFrame j = new AStarJFrame(14, 17, color, 3, 8, 14, 9);
        j.setTitle("A*寻路算法");
        //j.next();
        j.addKeyListener(j);
    }


    public void paint(Graphics g) {

        g.clearRect(0, 0, getWidth(), getHeight());

        //画横线
        int x1 = 35, x2 = 35 + GRID_SIZE * cols;
        for (int i = 0; i < rows + 1; i++) {
            int y = 50 + GRID_SIZE * i;
            g.drawLine(x1, y, x2, y);
        }

        //画竖线
        int y1 = 50, y2 = 50 + GRID_SIZE * rows;
        for (int i = 0; i < cols + 1; i++) {
            int x = 35 + GRID_SIZE * i;
            g.drawLine(x, y1, x, y2);
        }
        //到达终点
        if (isEnd) {
            g.drawString("已经到达目的地", x2 / 2 + 50, y2 + 25);
        }

        //涂颜色
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                x1 = 36 + GRID_SIZE * j;
                y1 = 51 + GRID_SIZE * i;
                //画格子颜色
                switch (color[i][j]) {
                    case -1:
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(x1, y1, GRID_SIZE - 1, GRID_SIZE - 1);
                        break;
                    case 2:
                        g.setColor(Color.CYAN);
                        g.fillRect(x1, y1, GRID_SIZE - 1, GRID_SIZE - 1);
                        break;
                    case 4:
                        g.setColor(Color.ORANGE);
                        g.fillRect(x1, y1, GRID_SIZE - 1, GRID_SIZE - 1);
                        break;
                }

            }
        }

        g.setColor(Color.BLACK);
        //画起点
        x1 = 38 + GRID_SIZE * start.getX();
        y1 = 52 + GRID_SIZE * start.getY();
        g.drawString("s", x1, y1);
        //画终点
        x1 = 38 + GRID_SIZE * end.getX();
        y1 = 52 + GRID_SIZE * end.getY() + 5;
        g.drawString("t", x1, y1);

        //画路径
        for (Node p : path) {
            x1 = 38 + GRID_SIZE * p.getX();
            y1 = 52 + GRID_SIZE * p.getY();
            g.fillOval(x1, y1, GRID_SIZE - 3, GRID_SIZE - 3);

        }
        //画函数值
        g.drawString("g="+current.g+" h="+current.h+" f="+current.f,  25, y2 + 25);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            while(!isEnd){
                isEnd = next();
            }
            countPath();
            this.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            isEnd = next();
            countPath();
            this.repaint();
        }
    }
    private void countPath(){
        //计算路径
        Node n = current;
        path.clear();
        while (!(n.x == start.x && n.y == start.y)) {
            path.add(n);
            System.out.print("(" + n.x + "," + n.y + ") ");
            if (n.parentNode != null) {
                n = n.parentNode;
            }
        }
        path.add(start);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean next() {
        final int CONST_HENG = 10;// 垂直方向或水平方向移动的路径评分
        final int CONST_XIE = 14;// 斜方向移动的路径评分
        if (!isEnd && !openList.isEmpty() && !openList.contains(end)) {
            current = minList(openList);
            if (current.x == end.x && current.y == end.y
                    || openList.contains(end)) {
                return true;
            }

            // 上
            if (current.y - 1 >= 0) {
                checkPath(current.x, current.y - 1, current, end, CONST_HENG);
            }
            // 下
            if (current.y + 1 < rows) {
                checkPath(current.x, current.y + 1, current, end,
                        CONST_HENG);
            }
            // 左
            if (current.x - 1 >= 0) {
                checkPath(current.x - 1, current.y, current, end,
                        CONST_HENG);
            }
            // 右
            if (current.x + 1 < cols) {
                checkPath(current.x + 1, current.y, current, end,
                        CONST_HENG);
            }
            // 左上
            if (current.x - 1 >= 0 && current.y - 1 >= 0) {
                checkPath(current.x - 1, current.y - 1, current, end,
                        CONST_XIE);
            }
            // 左下
            if (current.x - 1 >= 0 && current.y + 1 < rows) {
                checkPath(current.x - 1, current.y + 1, current, end,
                        CONST_XIE);
            }
            // 右上
            if (current.x + 1 < cols && current.y - 1 >= 0) {
                checkPath(current.x + 1, current.y - 1, current, end,
                        CONST_XIE);
            }
            // 右下
            if (current.x + 1 < cols && current.y + 1 < rows) {
                checkPath(current.x + 1, current.y + 1, current, end,
                        CONST_XIE);
            }
            openList.remove(current);
            closeList.add(current);

        }
        return false;

    }

    boolean checkPath(int x, int y, Node preNode, Node end, int cost) {
        Node node = new Node(x, y, preNode);
        // 查找地图中是否能通过
        if (color[y][x] == -1) {
            closeList.add(node);
            return false;
        }
        // 查找关闭列表中是否存在
        if (isListContains(closeList, x, y) != -1) {// 存在
            return false;
        }
        // 查找开启列表中是否存在
        int index = -1;
        if ((index = isListContains(openList, x, y)) != -1) {// 存在
            // G值是否更小，即是否更新G，F值
            if ((preNode.g + cost) < openList.get(index).g) {
                count(node, end, cost);
                openList.set(index, node);
            }
        } else {
            // 不存在，添加到开启列表中
            node.setParentNode(preNode);
            count(node, end, cost);
            openList.add(node);
        }
        return true;
    }

    private void count(Node node, Node eNode, int cost) {
        countG(node, cost);
        countH(node, eNode);
        countF(node);
    }

    // 计算G值
    private void countG(Node node, int cost) {
        if (node.getParentNode() == null) {
            node.setG(10 * color[node.getY()][node.getX()]);
        } else {
            node.setG(node.getParentNode().getG() + 10 * color[node.getY()][node.getX()] + cost);
        }
    }

    // 计算H值
    private void countH(Node node, Node eNode) {
        node.setH((Math.abs(node.getX() - eNode.getX()) + Math.abs(node.getY()
                - eNode.getY())) * 10);
    }

    // 计算F值
    private void countF(Node node) {
        node.setF(node.getG() + node.getH());
    }

    private int isListContains(List<Node> list, int x, int y) {
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            if (node.getX() == x && node.getY() == y) {
                return i;
            }
        }
        return -1;
    }

    // 找最小值
    private Node minList(List<Node> list) {
        Iterator<Node> i = list.iterator();
        Node candidate = i.next();

        while (i.hasNext()) {
            Node next = i.next();
            if (next.compareTo(candidate) < 0)
                candidate = next;
        }
        return candidate;
    }
}