package com.yp.enstudy.utils;

class AiBinHosTest {
    public static void main(String args[]) {
        int l = 12, b = 5, n, u = 0, m, p, et, v, i, c;
        // l为List的数量
        // b为复习的次数，示例如下
        // b=4 , Between[] = { 0 , 1 , 2 , 4 , 7 }
        // b=5 , Between[] = { 0 , 1 , 2 , 4 , 7 , 15 }
        // 定义艾宾浩斯复习间隔天数的数组
        int Between[][];
        Between = new int[l + 1][b + 1];
        for (n = 0; n < l + 1; n++) {
            Between[n][0] = 0;
            Between[n][1] = 1;
            Between[n][2] = 2;
            Between[n][3] = 4;
            Between[n][4] = 7;
        }
        if (b == 5)
            for (n = 0; n < l + 1; n++) {
                Between[n][5] = 15;
            }
        // 测试Between赋值是否正确
        // for (n=0;n<l+1;n++)
        // {
        // for (v=0;v<b+1;v++)
        // {
        // System.out.print("Between"+n+v+"="+Between[n][v]+" ");
        // }
        // System.out.println();
        // }
        // 定义List数组
        int List[];
        List = new int[l + 1];
        for (n = 1; n < l + 1; n++)// 对List数组循环赋值
        {
            List[n] = n;
            // if (n<10)
            // System.out.println("List"+u+n+"="+u+List[n]);//测试List赋值是否正确
            // else System.out.println("List"+n+"="+List[n]);//测试List赋值是否正确
        }
        ;
        // 定义背单词计划的天数
        int Day[];
        int sum = 0;
        for (n = 1; n < b + 1; n++) {
            sum += Between[1][n];
        }

        Day = new int[l + sum + 1];
        for (n = 1; n < l + sum + 1; n++) {
            Day[n] = n;
            // if (n<10)
            // System.out.println("Day"+u+n+"="+u+Day[n]);//测试Day赋值是否正确
            // else System.out.println("Day"+n+"="+Day[n]);//测试Day赋值是否正确
        }
        ;
        // 开始生成艾宾浩斯背单词计划表
        // 循环天数
        for (n = 1; n < l + sum + 1; n++) {
            if (n < 10)
                System.out.print("Day" + u + n + " ");// 打印天数
            else
                System.out.print("Day" + n + " ");// 打印天数
            if (n < l + 1) {
                if (n < 10)
                    System.out.print("List" + u + n + " * ");// 打印天数
                else
                    System.out.print("List" + n + " * ");// 打印天数
            } else {
                System.out.print("       * ");
            }
            // 循环复习间隔
            for (p = 0; p < b + 1; p++) {
                // 循环List
                for (m = 1; m < l + 1; m++) {

                    if (Day[n] - List[m] == Between[m][0]) {
                        if (Between[m][0] < Between[m][1]) {
                            Between[m][0] = Between[m][1];
                            List[m] = Day[n];
                        } else if (Between[m][0] < Between[m][2]) {
                            Between[m][0] = Between[m][2];
                            List[m] = Day[n];
                        } else if (Between[m][0] < Between[m][3]) {
                            Between[m][0] = Between[m][3];
                            List[m] = Day[n];
                        } else if (Between[m][0] < Between[m][4]) {
                            Between[m][0] = Between[m][4];
                            List[m] = Day[n];
                        } else if (b == 5) {
                            if (Between[m][0] < Between[m][5])
                                Between[m][0] = Between[m][5];
                            List[m] = Day[n];
                        } else
                            List[m] = 0;// 终止List

                        if (m < 10)
                            System.out.print("List" + u + m + " ");// 打印List数
                        else
                            System.out.print("List" + m + " ");// 打印List数
                    }
                }
            }
            System.out.println(" ");
        }
    }
}
