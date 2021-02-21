package love.simbot.example.utils;

public class ReUtils {

    /*
    * 判断字符串是否存在数组中
    * */
    public static boolean isNotStr(String[] strArr, String str){
        if(strArr == null || strArr.length == 0){
            return false;
        }
        for (String i: strArr) {
            if(i.equals(str)){
               return true;
            }
        }
        return false;
    }

    /**
     * 清除掉字符串前后的特殊字符
     * @param content  原字符串
     * @param spliter  要清除的字符串,注意只有一位，如"((1,2,3,4))",spliter也只为"("
     * @return
     */
    public static String trimHeadAndEndChar(String content, String spliter){
        if(content.isEmpty() || spliter.isEmpty()){
            return content;
        }
        //要匹配替换正则表达式的特殊字符需要在前面加\进行转义
        if(spliter.equals("*")
                || spliter.equals("\\")
                || spliter.equals("^")
                || spliter.equals("$")
                || spliter.equals("(")
                || spliter.equals(")")
                || spliter.equals("+")
                || spliter.equals(".")
                || spliter.equals("[")
                || spliter.equals("?")
                || spliter.equals("{")
                || spliter.equals("|")){
            spliter = "\\" + spliter;
        }
        String rex = "^" + spliter + "*|" + spliter + "*$";
        //System.out.println(rex);
        return content.replaceAll(rex, "");
    }
}
