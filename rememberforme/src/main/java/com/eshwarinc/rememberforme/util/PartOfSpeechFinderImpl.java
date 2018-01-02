package com.eshwarinc.rememberforme.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class PartOfSpeechFinderImpl implements IPartOfSpeechFinder {

	private static IPartOfSpeechFinder finder = null;
	
    private SentenceModel sentenceModel;
    private TokenizerModel tokenizerModel;
    private POSModel partOfSpeechModel;

    private SentenceDetectorME sentenceDetector;
    private TokenizerME tokenizer;
    private POSTaggerME partOfSpeechTagger;
    
    private PartOfSpeechFinderImpl() throws IOException {
       	InputStream sentenceModelStream = getInputStream("models/en-sent.bin");
        InputStream tokenizereModelStream = getInputStream("models/en-token.bin");
        InputStream partOfSpeechModelStream = getInputStream("models/en-pos-maxent.bin");
    	
        sentenceModel = new SentenceModel(sentenceModelStream);;
        tokenizerModel = new TokenizerModel(tokenizereModelStream);
        partOfSpeechModel = new POSModel(partOfSpeechModelStream);
        
        sentenceDetector = new SentenceDetectorME(sentenceModel);
    	tokenizer = new TokenizerME(tokenizerModel);
    	partOfSpeechTagger = new POSTaggerME(partOfSpeechModel);
    }

    public static IPartOfSpeechFinder getInstance() {
    	if(finder == null) {
    	    try {
				finder = new PartOfSpeechFinderImpl();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return finder;
    }
    
    private InputStream getInputStream(String resource) throws FileNotFoundException  {
    	InputStream inputS = null;
    	try {
			inputS = new FileInputStream(new File(resource));
		} catch (Exception e) {
			inputS = getClass().getResourceAsStream("/" + resource);
		}
        return inputS;
    }    
    
    private String[] segmentSentences(String document) {
        return sentenceDetector.sentDetect(document);
    }

    private String[] tokenizeSentence(String sentence) {
        return tokenizer.tokenize(sentence);
    }
    
    private String[] tagPartOfSpeech(String[] tokens) {
        return partOfSpeechTagger.tag(tokens);
    }
    
	public List<String> getNouns(String sentence) {
		List<String> nouns = new ArrayList<String>();
		
        String[] tokens = tokenizeSentence(sentence);

        String[] tags = tagPartOfSpeech(tokens);

        for (int i = 0; i < tokens.length; i++) {
        	if(tags[i] != null && tags[i].length() > 0 && tags[i].startsWith("NN")) {
        		nouns.add(tokens[i]);
        	}
        }
        return nouns;
	}
}
