package com.demo;

import com.sun.deploy.util.StringUtils;

import java.util.*;

/**
 * @author lijunyu
 * @Description: TODO
 * @return $
 * @throws
 * @date $ $
 * ===========================================
 * 修改人：lijunyu，    修改时间：$ $，    修改版本：
 * 修改备注：
 * ===========================================
 */
public class MinPathDemo {

    static class Node {
        int x; //节点编号
        int lenth;//长度
        Node parent;// 上一节点

        public Node(int x, int lenth, Node parent) {
            this.x = x;
            this.lenth = lenth;
            this.parent = parent;
        }

        public Node append(Node parent ,Node node ) {
            if (parent == null) {
                parent = node;
            } else {
                append(parent.parent ,node);
            }
            return parent;
        }
    }

    public static void main(String[] args) {


        System.out.println("在此输入顶点信息，如：1-4,1-6,2-4,2-6,3-5,3-6,4-5,5-6");
        // 获取输入字符串
        Scanner sc=new Scanner(System.in);
        String str=sc.next(); // "1-4,1-6,2-4,2-6,3-5,3-6,4-5,5-6"
        // 获取顶点字符串
        String[] strarr = str.split(",");

        int[][] map = new int[11][11];//记录权值，顺便记录链接情况，可以考虑附加邻接表
        initmap(map,strarr);//初始化

        System.out.println("在此输入开始顶点与停止顶点（分隔符用” “），如：1 3");
        // 获取输入字符串
        Scanner numsr=new Scanner(System.in);
        String numstr = numsr.next();
        String numstr2 = numsr.next();
        int startnum = Integer.parseInt(numstr);
        int endnum = Integer.parseInt(numstr2);

        boolean bool[] = new boolean[11];//判断是否已经确定
//        int len[]=new int[6];//长度
        List<Node> len = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            len.add(i, new Node(i, Integer.MAX_VALUE, null));
        }
        Queue<Node> q1 = new PriorityQueue<Node>(com);
        len.get(0).lenth = 0;//从0这个点开始
        q1.add(new Node(startnum-1, 0, null));
//        int count = 0;//计算执行了几次dijkstra
        while (!q1.isEmpty()) {
            Node t1 = q1.poll();
            int index = t1.x;//节点编号
            int length = t1.lenth;//节点当前点距离
            bool[index] = true;//抛出的点确定
//            count++;//其实执行了6次就可以确定就不需要继续执行了  这句可有可无，有了减少计算次数
            for (int i = 0; i < map[index].length; i++) {
                if (map[index][i] > 0 && !bool[i]) {
                    // 更新节点级路径长度
                    Node node = new Node(i, length + map[index][i], t1);
                    if (len.get(i).lenth > node.lenth)//需要更新节点的时候更新节点并加入队列
                    {
                        Node hisnode = len.get(i);
                        Node parent = hisnode.parent;
                        len.set(i, len.get(i).append(parent,node));
                        q1.add(node);
                    }
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            if(i == endnum - 1){
                Node node = len.get(i);
                String path =  (node.x +1)   + "";
                path = getPath(node, path);
                System.out.println(path);
            }
        }
    }


    public static String getPath(Node node, String path) {
        if (node.parent != null) {
            path = path + " " +  (node.parent.x +1 )  ;
            path = getPath(node.parent, path);
        }
        return path;
    }

    static Comparator<Node> com = new Comparator<Node>() {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.lenth - o2.lenth;
        }
    };

    private static void initmap(int[][] map,String[] strarr) {
        for(String str : strarr){
            String[] mapstrarr = str.split("-");
            int x = Integer.parseInt(mapstrarr[0]);
            int y = Integer.parseInt(mapstrarr[1]);
            if(x>0 && x < 11 && y > 0 && y< 11){
                map[x-1][y-1] = 1;
                // 为了可以运行到所有的顶点
                map[y-1][x-1] = 1;
            } else {
                throw new NumberFormatException("顶点需要控制在1-10！");
            }
        }
    }

    //1-4,1-6,2-4,2-6,3-5,3-6,4-5,5-6
/*    private static void initmap(int[][] map) {
        map[0][3] = 1;
        map[0][5] = 1;
        map[1][3] = 1;
        map[1][5] = 1;
        map[2][4] = 1;
        map[2][5] = 1;
        map[3][0] = 1;
        map[3][1] = 1;
        map[3][4] = 1;
        map[4][2] = 1;
        map[4][3] = 1;
        map[4][5] = 1;
        map[5][0] = 1;
        map[5][1] = 1;
        map[5][2] = 1;
        map[5][4] = 1;
    }*/
}
