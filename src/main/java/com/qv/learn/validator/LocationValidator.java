package com.qv.learn.validator;

import org.jqurantree.core.error.JQuranTreeException;
import org.jqurantree.orthography.Document;
import org.jqurantree.orthography.Location;

public class LocationValidator {

    public static boolean validateLocation( Location location ) throws Exception {

        try {
            LocationValidator.validateChapterNumber(location.getChapterNumber());
            LocationValidator.validateVerseNumber(location.getChapterNumber(), location.getVerseNumber());
            LocationValidator.validateTokenNumber(location.getChapterNumber(), location.getVerseNumber(), location.getTokenNumber());
        }
        catch (Exception e){

            throw new Exception(e.getMessage());
        }

        return true;
    }

    static void validateChapterNumber(int chapterNumber) {
        if (chapterNumber < 1 || chapterNumber > 114) {
            throw new JQuranTreeException("Chapter numbers should be between 1 and 114 inclusive.");
        }
    }

    static void validateVerseNumber(int chapterNumber, int verseNumber) {

        int verseCount = Document.getChapter(chapterNumber).getVerseCount();
        if( verseNumber < 0 || verseNumber > verseCount )
            throw new JQuranTreeException("Verse number should be between 1 and " + verseCount + " inclusive.");
    }

    static void validateTokenNumber(int chapterNumber, int verseNumber, int tokenNumber) {
        int tokenCount = Document.getVerse(chapterNumber,verseNumber).getTokenCount();
        if( tokenNumber < 0 || tokenNumber > tokenCount )
            throw new JQuranTreeException("Token number should be between 1 and " + tokenCount + " inclusive.");
    }
}
