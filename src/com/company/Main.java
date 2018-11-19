package com.company;


import com.company.add.Add;
import com.company.add.Book;

public class Main {
//    private static final String bookName = "Spring-mvc";
//    private static final String bookName = "DD";
    private static final String bookName = "Vue";
    public static void main(String[] args) {
        //更新目录
        new Book(bookName);
        //创建书文件夹
//        new Add(bookName, ".png");
    }

}
