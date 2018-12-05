package com.company.add;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Book {
    private String bookName;
    private String path = "";
    iBook b;

    public Book(String s) {
        this.bookName = s;
        this.b = new iBook();
        readFile();
        b.init();
    }

    public void readFile() {
        File file = new File(path());
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String fileName = tempList[i].getName();
                b.pagename.add(fileName);
                b.pagepath.add(path + "/Book/" + bookName + "/content/" + fileName);
            }
        }
    }

    public String path() {
        File directory = new File("");//设定为当前文件夹
        try {
            path = directory.getCanonicalPath();//获取标准的路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        b.coverpath = path + "/Book/" + bookName + "/cover/COVER.md";
        return path + "/Book/" + bookName + "/content";
    }
}

class iBook {
    String coverpath;
    List<String> pagepath = new ArrayList<>();
    List<String> pagename = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    private void gettiter() {
        pagepath.forEach(s -> {
            File file = new File(s);
            try {
                String name = s.substring(s.lastIndexOf('/')+1,s.lastIndexOf('.'));
                System.out.println(name);
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                String s1 = br.readLine();
                titles.add(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void init() {
        try {
            AtomicInteger index = new AtomicInteger();
            BufferedWriter out = new BufferedWriter(new FileWriter(coverpath, false));
            out.write("## 目录");
            for (int i = 0; i < pagename.size(); i++) {
                gettiter();
                String str = "- [" + titles.get(index.get()) + "](../content/" +pagename.get(i) + ")";
                out.newLine();
                out.write(str);
                index.getAndIncrement();
            }
            out.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
