package im.hxmeet.Xfile.core.util;

import java.util.Comparator;


/**
 * 类 windows 文件排序算法
 * 文件名按你期望的顺序排序，即“file1”、“file2”、“file10”，
 * 而不是“file1”、“file10”、“file2”，这在标准字典排序中会发生。
 */
public class NaturalOrderComparator implements Comparator<String> {

    private static final char ZERO_CHAR = '0';

    /**专门从当前索引开始比较两个子字符串，当检测到数字时对它们进行比较。
     确定作为字符串表示的数字哪个具有最大长度；如果它们的长度相等，则判断哪个数值更大。*/
    private int compareRight(String a, String b) {
        //bias用于存储比较结果的偏差，初始值为0。这意味着在最初的比较中没有偏差。
        int bias = 0, ia = 0, ib = 0;

        for (; ; ia++, ib++) {
            //分别获取字符串 a 和 b 在当前索引的位置的字符。使用 charAt 方法是为了安全地处理字符串索引，避免越界。
            char ca = charAt(a, ia);
            char cb = charAt(b, ib);

            //如果当前字符 ca 和 cb 都不是数字，直接返回当前的偏差值。
            if (!isDigit(ca) && !isDigit(cb)) {
                return bias;
            }
            if (!isDigit(ca)) {
                //表示ca 不是数字而 cb 是数字，返回 -1，表示 a 小于 b。
                return -1;
            }
            if (!isDigit(cb)) {
                return +1;
            }
            if (ca == 0 && cb == 0) {
                //如果两个字符都是结尾的空字符（0），则返回当前的偏差。
                return bias;
            }

            if (bias == 0) {
                if (ca < cb) {
                    bias = -1;
                } else if (ca > cb) {
                    bias = +1;
                }
            }
        }
    }

    @Override
    /**使用两个索引（ia 和 ib）遍历两个字符串的字符。
     统计前导零和空格，并优先比较数字的序列。*/
    public int compare(String a, String b) {
        int ia = 0, ib = 0;
        int nza, nzb;
        char ca, cb;

        while (true) {
            // Only count the number of zeroes leading the last number compared
            nza = nzb = 0;

            ca = charAt(a, ia);
            cb = charAt(b, ib);

            // skip over leading spaces or zeros
            while (Character.isSpaceChar(ca) || ca == ZERO_CHAR) {
                if (ca == ZERO_CHAR) {
                    nza++;
                } else {
                    // Only count consecutive zeroes
                    nza = 0;
                }

                ca = charAt(a, ++ia);
            }

            while (Character.isSpaceChar(cb) || cb == '0') {
                if (cb == '0') {
                    nzb++;
                } else {
                    // Only count consecutive zeroes
                    nzb = 0;
                }

                cb = charAt(b, ++ib);
            }

            // Process run of digits
            if (Character.isDigit(ca) && Character.isDigit(cb)) {
                int bias = compareRight(a.substring(ia), b.substring(ib));
                if (bias != 0) {
                    return bias;
                }
            }

            if (ca == 0 && cb == 0) {
                // The strings compare the same. Perhaps the caller
                // will want to call strcmp to break the tie.
                return compareEqual(a, b, nza, nzb);
            }
            if (ca < cb) {
                return -1;
            }
            if (ca > cb) {
                return +1;
            }

            ++ia;
            ++ib;
        }
    }

//判断一个字符是否是数字，或者是被认为在数字上下文中相关的字符
    private static boolean isDigit(char c) {
        return Character.isDigit(c) || c == '.' || c == ',';
    }

    private static char charAt(String s, int i) {
        return i >= s.length() ? 0 : s.charAt(i);
    }

    /**这个方法处理在当前比较中出现相等的情况。它考虑前导零的数量和字符串的长度，以决定哪个字符串是“更小”或“更大”。*/
    private static int compareEqual(String a, String b, int nza, int nzb) {
        if (nza - nzb != 0) {
            return nza - nzb;
        }

        if (a.length() == b.length()) {
            return a.compareTo(b);
        }

        return a.length() - b.length();
    }

}