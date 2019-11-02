package com.qv.learn.scrapper;


import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Scrapper implements InitializingBean{

    Set<ScrappedData> dataSet;

    @Autowired
    ScrappedDataService scrappedDataService;

    @Override
    public void afterPropertiesSet() throws Exception {

        dataSet = new HashSet<>( scrappedDataService.findAll() );

        for( int i = 1, j = 1; i<=1; i++ ){

            String url = "http://www.recitequran.com/en/" + i + ":" + j;

            // JBrowserDriver part
            JBrowserDriver driver = new JBrowserDriver(Settings
                    .builder().
                            timezone(Timezone.ASIA_DHAKA).build());
            driver.get(url);
            String loadedPage = driver.getPageSource();

            Document doc = Jsoup.parse( loadedPage ); // Jsoup.connect( url ).get();

            Element body = doc.body();
            Element mainContent = body.getElementById( "MainContent" );
            Elements verses = mainContent.getElementsByClass( "mainContent" );

            for( Element verse: verses ){

                Elements ayahContainers = verse.getElementsByClass( "ayahContainer" );
                Element ayahContainer = ayahContainers.get(0);

                Element rView = ayahContainer.getElementById( "RView" );
                Elements ayahTexts = rView.getElementsByClass( "ayahText" );
                Element ayahText = ayahTexts.get(0);

                Elements tokens = ayahText.getElementsByTag( "div" );

                for( Element token: tokens ){

                    if( token.classNames().contains( "vn" ) )
                        continue;

                    Elements imgDiv = token.getElementsByClass( "img" );
                    Elements imges = imgDiv.get(0).getElementsByTag( "img" );
                    Element img = imges.get(0);
                    Elements translateSpans = token.getElementsByTag( "span" );
                    Element translateSpan = translateSpans.get(0);

                    Attributes attributes = token.attributes();
                    String s = attributes.get( "s" );
                    String a = attributes.get( "a" );
                    String w = attributes.get( "w" );
                    String f = attributes.get( "f" );

                    Attributes imageAtrribute = img.attributes();

                    ScrappedData scrappedData = new ScrappedData();
                    scrappedData.setChapter( Integer.parseInt( s ) );
                    scrappedData.setVerse( Integer.parseInt( a ) );
                    scrappedData.setToken( Integer.parseInt( w ) );
                    scrappedData.setAudioId( f );

                    scrappedData.setImageData( imageAtrribute.get("src").getBytes() );
                    scrappedData.setImageAlt( imageAtrribute.get("alt"));

                    scrappedData.setTranslation( translateSpan.text() );

                    dataSet.add( scrappedData );
                }
            }
        }
    }
}
