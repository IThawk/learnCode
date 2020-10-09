package com.ithawk.learn.middle;

public class IntToRoman {
    /**
     * <P>
     *     罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
     *
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
     *
     * 示例 1:
     *
     * 输入: 3
     * 输出: "III"
     * 示例 2:
     *
     * 输入: 4
     * 输出: "IV"
     * 示例 3:
     *
     * 输入: 9
     * 输出: "IX"
     * 示例 4:
     *
     * 输入: 58
     * 输出: "LVIII"
     * 解释: L = 50, V = 5, III = 3.
     * 示例 5:
     *
     * 输入: 1994
     * 输出: "MCMXCIV"
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/integer-to-roman
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </P>
     */

//    方法一：贪心
//    将给定的整数转换为罗马数字需要找到上述 13 个符号的序列，这些符号的对应值加起来就是整数。根据符号值，此序列必须按从大到小的顺序排列。符号值如下。
//
//
//
//    如概述中所述，表示应该使用尽可能大的符号，从左侧开始工作。因此，使用贪心算法是有意义的。贪心算法是一种在当前时间做出最佳可能决策的算法；在这种情况下，它会取出最大可能的符号。
//
//    为了表示一个给定的整数，我们寻找适合它的最大符号。我们减去它，然后寻找适合余数的最大符号，依此类推，直到余数为0。我们取出的每个符号都附加到输出的罗马数字字符串上。
//
//    例如，假设我们需要将数字设为 671。
//
//        671 中最大的符号是 D（值 500）。
//
//
//    Roman Numeral so far: D
//    Integer remainder: 671 - 500 = 171
//    我们在 171 的基础重复以上步骤，最大的符号是 C（值 100）。
//
//
//    Roman Numeral so far: DC
//    Integer remainder: 171 - 100 = 71
//    在 71 的基础重复以上步骤，最大的符号是 L （值 50）。
//
//
//    Roman Numeral so far: DCL
//    Integer remainder: 71 - 50 = 21
//    在 21 的基础重复以上步骤，最大的符号是 X （值 10）。
//
//
//    Roman Numeral so far: DCLX
//    Integer remainder: 21 - 10 = 11
//    在 11 的基础重复以上步骤，最大的符号是 X （值 10）。
//
//
//    Roman Numeral so far: DCLXX
//    Integer remainder: 11 - 10 = 1
//    最后，用 I 表示 1，完成转换。
//
//
//    Roman Numeral so far: DCLXXI
//    Integer remainder: 1 - 1 = 0
//    在伪代码中，该算法如下：
//
//
//    define function to_roman(integer):
//    roman_numeral = ""
//        while integer is non-zero:
//    symbol = biggest valued symbol that fits into integer
//        roman_numeral = concat roman_numeral and symbol
//        integer = integer - value of symbol
//    return roman_numeral
//    在代码中实现这一点的最简单的方法是从最大到最小循环遍历每个符号，检查当前符号的有多少个副本适合剩余的整数。
//
//
//    define function to_roman(integer):
//    roman_numeral = ""
//        for each symbol from largest to smallest:
//        if value of symbol is greater than integer:
//        continue
//    symbol_count = number of times symbol value fits into integer
//    repeat symbol_count times:
//    roman_numeral = concat roman_numeral and symbol
//    integer = integer - (value of symbol * symbol_count)
//
//        return roman_numeral
//    以下动画显示了算法在 478 上运行的情况：
//
//
//        1 / 19



    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        // Loop through each symbol, stopping if num becomes 0.
        for (int i = 0; i < values.length && num >= 0; i++) {
            // Repeat while the current symbol still fits into num.
            while (values[i] <= num) {
                num -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }


    public String intToRoman1(int num) {

        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[num / 1000] + hundreds[num % 1000 / 100] + tens[num % 100 / 10] + ones[num % 10];
    }

}
