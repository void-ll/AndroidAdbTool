import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ProguardGeneratorUtil {
    public static final String CRLF = "\r\n";

    /**
     * 英文字符
     */
    public static final class LetterProguard {
        private static final int TYPE_PACKAGE = 1;
        private static final int TYPE_CLASS = 2;
        private static final int TYPE_VARIABLE = 3;
        private static final String FILE_NAME_PACKAGE = "dictionary-package.txt";
        private static final String FILE_NAME_CLASS = "dictionary-class.txt";
        private static final String FILE_NAME_VARIABLE = "dictionary-variable.txt";
        private static final char[] CHARS_UPPER = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        private static final char[] CHARS_LOWER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        private static final char[] CHARS_ALL = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        public static void start(File dir, int lines, int length) {
            start(1, dir, lines, length);
            start(2, dir, lines, length);
            start(TYPE_VARIABLE, dir, lines, length);
        }

        private static void start(int type, File dir, int lines, int length) {
            String fileName;
            if (type == 1) {
                fileName = FILE_NAME_PACKAGE;
            } else if (type == 2) {
                fileName = FILE_NAME_CLASS;
            } else if (type == TYPE_VARIABLE) {
                fileName = FILE_NAME_VARIABLE;
            } else {
                return;
            }
            BufferedWriter out = null;
            try {
                try {
                    if (!dir.exists() && dir.mkdirs()) {
                        System.out.println("The proguard directory created successfully! " + dir.getAbsolutePath());
                    }
                    Random random = new Random();
                    List<String> list = new ArrayList<>();
                    File file = new File(dir, fileName);
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
                    int i = 0;
                    while (i < lines) {
                        StringBuilder builder = new StringBuilder();
                        if (type == 2) {
                            builder.append(getRandomChar(random, CHARS_UPPER));
                        } else {
                            builder.append(getRandomChar(random, CHARS_LOWER));
                        }
                        if (length > 1) {
                            for (int j = 0; j < length - 1; j++) {
                                builder.append(getRandomChar(random, CHARS_ALL));
                            }
                        }
                        String text = builder.toString();
                        if (list.contains(text)) {
                            System.err.println("The proguard dictionary " + fileName + " contains " + text + ", skip " + text + "!");
                        } else {
                            list.add(text);
                            out.write(text + (i < lines - 1 ? CRLF : ""));
                        }
                        i++;
                    }
                    list.clear();
                    System.out.println("The proguard dictionary is written successfully! " + file.getAbsolutePath());
                    ProguardGeneratorUtil.closeIOQuietly(out);
                } catch (Exception e) {
                    e.printStackTrace();
                    ProguardGeneratorUtil.closeIOQuietly(out);
                }
            } catch (Throwable th) {
                ProguardGeneratorUtil.closeIOQuietly(out);
                throw th;
            }
        }

        private static char getRandomChar(Random random, char[] chars) {
            return chars[random.nextInt(chars.length)];
        }
    }

    /**
     * 特殊字符
     */
    public static final class CharacterProguard {
        private static final String FILE_NAME = "dictionary-character.txt";

        public static void start(File dir, int lines, char... chars) {
            int length = Math.max(6, (int) ((Math.log(lines) / Math.log(chars.length)) * 1.5d));
            BufferedWriter out = null;
            try {
                try {
                    if (!dir.exists() && dir.mkdirs()) {
                        System.out.println("The proguard directory created successfully! " + dir.getAbsolutePath());
                    }
                    List<String> list = new ArrayList<>();
                    File file = new File(dir, FILE_NAME);
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
                    int i = 0;
                    while (i < lines) {
                        int len = (int) (2.0d + (Math.random() * length));
                        StringBuilder builder = new StringBuilder();
                        int i1 = 0;
                        while (i1 < len) {
                            builder.append(getRadomChar(i1 == 0, chars));
                            i1++;
                        }
                        String text = builder.toString();
                        if (list.contains(text)) {
                            i--;
                            System.err.println("The proguard dictionary dictionary-character.txt contains " + text + ", skip " + text + "!");
                        } else {
                            list.add(text);
                            out.write(text + (i < lines - 1 ? CRLF : ""));
                        }
                        i++;
                    }
                    list.clear();
                    System.out.println("The proguard dictionary is written successfully! " + file.getAbsolutePath());
                    ProguardGeneratorUtil.closeIOQuietly(out);
                } catch (Exception e) {
                    e.printStackTrace();
                    ProguardGeneratorUtil.closeIOQuietly(out);
                }
            } catch (Throwable th) {
                ProguardGeneratorUtil.closeIOQuietly(out);
                throw th;
            }
        }

        private static char getRadomChar(boolean first, char... chars) {
            char c = chars[(int) (Math.random() * chars.length)];
            if (first && c >= '0' && c <= '9') {
                return getRadomChar(true, chars);
            }
            return c;
        }
    }

    /**
     * 中文
     */
    public static final class ChineseProguard {
        private static final int CHINESE_START = 19968;
        private static final int CHINESE_END = 40869;
        private static final String FILE_NAME = "dictionary-chinese.txt";

        public static void start(File dir) {
            BufferedWriter out = null;
            try {
                try {
                    if (!dir.exists() && dir.mkdirs()) {
                        System.out.println("The proguard directory created successfully! " + dir.getAbsolutePath());
                    }
                    List<String> list = new ArrayList<>();
                    File file = new File(dir, FILE_NAME);
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
                    int i = 0;
                    while (i < 20902) {
                        String text = "\\u" + Integer.toHexString(CHINESE_START + i);
                        if (list.contains(text)) {
                            System.err.println("The proguard dictionary dictionary-chinese.txt contains " + text + ", skip " + text + "!");
                        } else {
                            list.add(text);
                            out.write(text + (i < 20902 - 1 ? CRLF : ""));
                        }
                        i++;
                    }
                    list.clear();
                    System.out.println("The proguard dictionary is written successfully! " + file.getAbsolutePath());
                    ProguardGeneratorUtil.closeIOQuietly(out);
                } catch (Exception e) {
                    e.printStackTrace();
                    ProguardGeneratorUtil.closeIOQuietly(out);
                }
            } catch (Throwable th) {
                ProguardGeneratorUtil.closeIOQuietly(out);
                throw th;
            }
        }

        private static char getRandomChar() {
            return (char) (19968.0d + (Math.random() * 20901.0d));
        }
    }

    /**
     * 输出关键字
     */
    public static final class KeyWordsProguard {
        private static final String FILE_NAME = "dictionary-keywords.txt";
        private static final String[] KEYWORDS = {"private", "protected", "public", "boolean", "byte", "char", "double", "float", "int", "long", "short", "import", "package", "try", "catch", "throw", "throws", "finally", "break", "continue", "return", "do", "while", "if", "else", "for", "instanceof", "switch", "case", "default", "abstract", "class", "enum", "interface", "extends", "implements", "static", "strictfp", "new", "final", "native", "synchronized", "transient", "volatile", "assert", "super", "this", "void", "goto", "const", "true", "false", "null"};

        public static void start(File dir) {
            BufferedWriter out = null;
            try {
                try {
                    if (!dir.exists() && dir.mkdirs()) {
                        System.out.println("The proguard directory created successfully! " + dir.getAbsolutePath());
                    }
                    File file = new File(dir, FILE_NAME);
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
                    int length = KEYWORDS.length;
                    int i = 0;
                    while (i < length) {
                        String text = KEYWORDS[i];
                        out.write(text + (i < length - 1 ? CRLF : ""));
                        i++;
                    }
                    System.out.println("The proguard dictionary is written successfully! " + file.getAbsolutePath());
                    ProguardGeneratorUtil.closeIOQuietly(out);
                } catch (Exception e) {
                    e.printStackTrace();
                    ProguardGeneratorUtil.closeIOQuietly(out);
                }
            } catch (Throwable th) {
                ProguardGeneratorUtil.closeIOQuietly(out);
                throw th;
            }
        }
    }

    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null || closeables.length <= 0) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                }
            }
        }
    }
}