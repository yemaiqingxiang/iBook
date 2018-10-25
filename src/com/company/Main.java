package com.company;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        //更新目录
//        new Book("demo");
        //创建书文件夹
        new add("demo", ".jpg");

    }

    /**
     * 创建文件
     */
    static class add {

        public add(String bookname, String suffix) {
            String path = "";
            File directory = new File("");//设定为当前文件夹
            try {
                path = directory.getCanonicalPath();//获取标准的路径
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content = path + "/Book/" + bookname + "/content/";
            String cover = path + "/Book/" + bookname + "/cover/";
            String img = path + "/Book/" + bookname + "/img/";
            String covermd = path + "/Book/" + bookname + "/cover/COVER.md";
            createFile(covermd);
            createDir(content);
            createDir(cover);
            createDir(img);
            List<String> r = new ArrayList<>();
            String s2 = "";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/README.md"));
                String str;
                while ((str = reader.readLine()) != null) {
                    r.add(str);
                }
                reader.close();
                BufferedWriter w = new BufferedWriter(new FileWriter(path + "/README.md", false));
                for (int i = 0; i < r.size(); i++) {
                    String s1 = r.get(i);
                    if (i < 3) {
                        w.write(s1);
                        w.newLine();
                    } else if (i % 2 == 1) {
                        System.out.println("双" + i);
                        if (i == r.size() - 2) {
                            char[] chars = s1.toCharArray();
                            int index = 0;
                            for (char c : chars) {
                                if (c == '|') {
                                    index++;
                                }
                            }
                            if (index < 6) {
                                w.write(s1 + "<img src=\"Book/CoverPhoto/" + bookname  + suffix + " width=150 height=180/>|");
                                w.newLine();
                            } else {
                                w.write(s1);
                                w.newLine();
                            }
                        } else {
                            w.write(s1);
                            w.newLine();
                        }
                    } else {
                        System.out.println("单" + i);
                        if (i == r.size() - 1) {
                            char[] chars = s1.toCharArray();
                            int ci = 0;
                            for (char c : chars) {
                                if (c == '|') {
                                    ci++;
                                }
                            }
                            if (ci < 6) {
                                w.write(s1 + "[" + bookname + "](Book" + bookname + "/cover/COVER.md)|");
                                w.newLine();
                            } else {
                                w.write(s1);
                                w.newLine();
                                w.write("|<img src=\"Book/CoverPhoto/" + bookname  + suffix +  "width=150 height=180/>|");
                                w.newLine();
                                w.write("|[" + bookname + "](Book" + bookname + "/cover/COVER.md)|");
                            }
                        } else {
                            w.write(s1);
                            w.newLine();
                        }
                    }
                }
                w.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public boolean createFile(String destFileName) {
            File file = new File(destFileName);
            if (file.exists()) {
                System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
                return false;
            }
            if (destFileName.endsWith(File.separator)) {
                System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
                return false;
            }
            //判断目标文件所在的目录是否存在
            if (!file.getParentFile().exists()) {
                //如果目标文件所在的目录不存在，则创建父目录
                System.out.println("目标文件所在目录不存在，准备创建它！");
                if (!file.getParentFile().mkdirs()) {
                    System.out.println("创建目标文件所在目录失败！");
                    return false;
                }
            }
            //创建目标文件
            try {
                if (file.createNewFile()) {
                    System.out.println("创建单个文件" + destFileName + "成功！");
                    return true;
                } else {
                    System.out.println("创建单个文件" + destFileName + "失败！");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
                return false;
            }
        }


        public boolean createDir(String destDirName) {
            File dir = new File(destDirName);
            if (dir.exists()) {
                System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
                return false;
            }
            if (!destDirName.endsWith(File.separator)) {
                destDirName = destDirName + File.separator;
            }
            //创建目录
            if (dir.mkdirs()) {
                System.out.println("创建目录" + destDirName + "成功！");
                return true;
            } else {
                System.out.println("创建目录" + destDirName + "失败！");
                return false;
            }
        }

    }

    /**
     * 写目录
     */
    static class Book {
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

    static class iBook {
        String coverpath;
        List<String> pagepath = new ArrayList<>();
        List<String> pagename = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        private void gettiter() {
            pagepath.forEach(s -> {
                File file = new File(s);
                try {
                    System.out.println(s);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader br = new BufferedReader(fileReader);
                    String s1 = br.readLine();
                    titles.add(s1.substring(s1.indexOf(" "), s1.length()));
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
                pagename.forEach(s -> {
                    gettiter();
                    String str = "- [" + titles.get(index.get()) + "](../contrnt/" + s + ")";
                    try {
                        out.newLine();
                        out.write(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    index.getAndIncrement();

                });
            } catch (Exception e) {

            }
        }
    }
}
