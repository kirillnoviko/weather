

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parser  {

    private static org.jsoup.nodes.Document getPage() throws IOException {
        String url = "http://www.pogoda.spb.ru/";
        Document page = Jsoup.parse(new URL(url), 30000000);
        return page;
    }

    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("cannljf");
    }

    public static void main(String[] args) throws Exception {

        JFrame frame =new JFrame("weather");
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());


        JLabel namee2=new JLabel("weather2");

        GridBagConstraints c =new GridBagConstraints();

        c.gridx=0;
        c.gridy=0;
        c.gridwidth=1;
        c.gridheight=1;
        c.weighty=0;
        c.weightx=0;
        c.anchor=GridBagConstraints.NORTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        frame.add(namee2,c);



        Document page = getPage();
        Element tableWth = page.select("table[class=wt]").first();
        Elements names = tableWth.select("tr[class=wth]");
        Elements value = tableWth.select("tr[valign=top]");
        int index = 0;
        int y=1;

        for (Element name : names) {

            String dateString = name.select("th[id=dt]").text();
            String date = getDateFromString(dateString);

            y++;
            y++;
            JLabel temp=new JLabel();
            temp.setText(date+"        :                         Температура       Давление       Влажность        Ветер");
            c.gridy=y;
            c.gridx=0;
            frame.add(temp,c);



            int iterationCount = 4;
            if (index == 0) {
                Element valueln = value.get(3);
                boolean isMoorning = valueln.text().contains("Утро");
                boolean isvech = valueln.text().contains("День");
                boolean isdd=valueln.text().contains("Вечер");
                if (isMoorning) {
                    iterationCount = 3;
                }
                if (isvech) {
                    iterationCount = 2;
                }
                if(isdd)
                {
                    iterationCount=1;
                }
                for (int i = 0; i < iterationCount; i++) {
                    Element valueLine = value.get(index + i);
                    String ttt="";
                    for (Element td : valueLine.select("td")) {
                        ttt= ttt+ td.text()+"          ";

                    }
                    y++;
                    JLabel temp1=new JLabel();
                    temp1.setText(ttt);

                    c.gridy=y;
                    c.gridx=0;
                    frame.add(temp1,c);

                }

                index=iterationCount;

            } else {

                for (int i = 0; i < 4; i++) {
                     String  tt="";
                    Element valueline = value.get(index + i);
                    for (Element td : valueline.select("td")) {


                        tt= tt+ td.text()+"          ";
//                        System.out.print(td.text() + "   ");
                    }
                    y++;
                    JLabel temp1=new JLabel();
                    temp1.setText(tt);

                    c.gridy=y;
                    c.gridx=0;
                    frame.add(temp1,c);
                    // System.out.println();
                }
                //return iterationCount;
                index=iterationCount;
            }





        }

        frame.setVisible(true);
    }

}
