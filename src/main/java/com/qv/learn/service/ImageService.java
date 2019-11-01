package com.qv.learn.service;

import org.jqurantree.arabic.encoding.EncodingType;
import org.jqurantree.orthography.*;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImageService {

    private static int[] cumTokenCount = new int[115];
    private static Map[] cumTokenCountForChapterByVerse = new HashMap[115];

    static {

        populateCumCount();
    }

    public List<Integer> getTokenNumber(Location location ) {

        List<Integer> tokenNumbers = new ArrayList<Integer>();

        Verse verse = Document.getVerse(location);

        Iterable<Token> tokens = verse.getTokens();
        Iterator<Token> tokenIterator = tokens.iterator();

        int count = 1;
        while ( tokenIterator.hasNext() ) {

            Token token = tokenIterator.next();
            Integer cumTokenNumber = cumTokenCount[location.getChapterNumber() - 1] + (int)cumTokenCountForChapterByVerse[ location.getChapterNumber() ].get( location.getVerseNumber() - 1 ) + token.getTokenNumber();

            if( location.getTokenNumber() == 0 ) {

                tokenNumbers.add(cumTokenNumber);
            }
            else if( count == location.getTokenNumber() ){

                tokenNumbers.add( cumTokenNumber );
                break;
            }
            count++;
        }

        return tokenNumbers;
    }

    private static void populateCumCount() {

        for (int i = 1; i <= 114; i++) {

            Chapter chapter = Document.getChapter(i);
            int tokenCount = chapter.getTokenCount();
            cumTokenCount[i] = cumTokenCount[i-1] + tokenCount;

            int verseCount = chapter.getVerseCount();

            for( int j=1; j<=verseCount; j++ ){

                if( j==1 ) {
                    cumTokenCountForChapterByVerse[i] = new HashMap();
                    cumTokenCountForChapterByVerse[i].put(0, 0);
                }

                int cumCount = (int)cumTokenCountForChapterByVerse[i].get( j-1 ) + chapter.getVerse(j).getTokenCount();
                cumTokenCountForChapterByVerse[i].put( j, cumCount );
            }
        }
    }
}
