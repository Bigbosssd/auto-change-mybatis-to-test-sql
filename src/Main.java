import java.io.*;
import java.util.ArrayList;

/**
 * Title: Main
 * Description: 启动程序
 * Company: wondersgroup.com
 *
 * @Author: 徐彬
 * @Date: 21/1/11 11:18
 * @Version: 1.0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "test.xml";
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        ArrayList<String> allColumns;
        String aColumn;
        //删除MyBatis的<if>条件
        for(allColumns = new ArrayList(); (aColumn = bufferedReader.readLine()) != null; ) {
            if (aColumn.indexOf("<if") != -1 || aColumn.indexOf("</if>") != -1) {
                continue;
            }
            String result;
            result = aColumn;
            //替换字段（不断更新、不严谨）
            String s1 = result.replaceAll("#\\{([^}])*,jdbcType=VARCHAR\\}", "'2'");
            String s2 = s1.replaceAll("#\\{([^}])*,jdbcType=DECIMAL\\}", "2");
            String s3 = s2.replaceAll("#\\{([^}])*,jdbcType=TIMESTAMP\\}", "now()");
            String s4 = s3.replace("<set>", "set");
            String s5 = s4.replace("</set>", "");
            String s6 = s5.replace("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >", "(");
            String s7 = s6.replace("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">", "(");
            String s8 = s7.replace("</trim>", ")");
            String s9 = s8.replace("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >", " values (");
            String s10 = s9.replace("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">", " values (");
            String s11 = s10.replace("<set >", "set");
            String s12 = s11.replaceAll("#\\{([^}])*,jdbcType=INTEGER\\}", "2");
            String s13 = s12.replaceAll("#\\{([^}])*,jdbcType=BIGINT\\}", "2");
            String s14 = s13.replaceAll("#\\{[a-zA-Z]+\\}", "2");
            String s15 = s14.replaceAll("in\\(\\$\\{[a-zA-Z]+\\}\\)", "in(1)");
//            String s15 = s14;
            String s16 = s15.replaceAll("#\\{([^}])*,jdbcType=SMALLINT\\}", "2");
            String s17 = s16.replace("&gt;", ">");
            String s18 = s17.replace("&lt;", "<");
            String s19 = s18.replaceAll("#\\{([^}])*,jdbcType = DECIMAL\\}", "2");
            String s20 = s19.replace("<![CDATA[", " ");
            String s21 = s20.replace("]]>", " ");
            String s22 = s21.replace("<where>", "where");
            String s23 = s22.replace("</where>", "");
            allColumns.add(s23);
        }

        File result = new File("test.xml");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(result));
        for (String string : allColumns) {
            bufferedWriter.write(string);
            bufferedWriter.write("\n");
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}

