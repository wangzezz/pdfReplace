package pub.hlc;

import com.aspose.pdf.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String srcPath = "C:\\Users\\Administrator\\Desktop\\中融慧金\\CA\\入职合同模版样例.pdf";
        String targetPath = "C:\\Users\\Administrator\\Desktop\\out.pdf";
        Map<String, String> map = new HashMap<String, String>();
        map.put("[$合同编号$]", "ZR-20181009-00000164");
        map.put("[$姓名$]", "TroubleA");
        map.put("[$签字日期$]", "2018/10/10 11:24:30");
        test(srcPath, targetPath, map);
    }

    public static void test(String srcPath, String targetPath, Map<String, String> map){
        InputStream license = Main.class.getClassLoader().getResourceAsStream("\\license.xml");
        try {
            new License().setLicense(license);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document pdfDoc = new Document(srcPath);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

            TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(entry.getKey());
            PageCollection pages = pdfDoc.getPages();
            System.out.println("文档总页码数："+pages.size());
            pages.accept(textFragmentAbsorber);
            int i = 0;
            for (TextFragment textFragment :(Iterable<TextFragment>) textFragmentAbsorber.getTextFragments()) {
                textFragment.setText(entry.getValue());
                textFragment.getTextState().setBackgroundColor(com.aspose.pdf.Color.getRed());  //添加红色背景
                System.out.println(++i);
            }
        }
        pdfDoc.save(targetPath);
    }
}
