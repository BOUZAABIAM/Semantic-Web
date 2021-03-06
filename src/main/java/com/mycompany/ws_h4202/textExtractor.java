/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ws_h4202;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.ConceptsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author DELL
 */
public class textExtractor {

    private static NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
            NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
            "1dfadf0e-f20e-41f2-a58a-225c2fc682ae",
            "J8jyS4y20zyq");
    public static int number = 0;

    private static EntitiesOptions entities = new EntitiesOptions.Builder().limit(1).sentiment(true).build();
    private static ConceptsOptions concepts = new ConceptsOptions.Builder().limit(5).build();

    public static String ExtractTextUrl(String url) throws Exception {
        System.out.println("Request to watson on " + url);
        Features features = new Features.Builder().concepts(concepts).build();
        AnalyzeOptions parameters
                = new AnalyzeOptions.Builder()
                        .url(url)
                        .features(features)
                        .returnAnalyzedText(true)
                        .language("ENGLISH")
                        .build();
        String res = "";
        try {
            AnalysisResults results;
            results = service.analyze(parameters).execute();
            //System.out.println(results.getAnalyzedText());

            enregistrerResultat(results.getAnalyzedText());

            res = results.getAnalyzedText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;

    }

    public static int numb() {
        number++;
        return number;
    }

    public static String chargerResultat(int i) {
        String contenu = "";
        try (BufferedReader br = new BufferedReader(new FileReader("Result " + i + ".txt"))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contenu += sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenu;
    }

    public static void enregistrerResultat(String text) {
        int i = numb();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Result " + i + ".txt"))) {
            bw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
