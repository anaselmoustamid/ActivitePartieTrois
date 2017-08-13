package com.example.elitebook_anas.activitepartietrois;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DownloadTask extends AsyncTask<String, Void, List<Article>> {

    public final ArticleListAdapter _adapter;

    private final DateFormat _dateParser = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    public DownloadTask(ArticleListAdapter adapter) {
        _adapter = adapter;
    }

    @Override
    protected List<Article> doInBackground(String... params) {
        String url = params[0];

        try {
            Thread.sleep(new SecureRandom().nextInt(2000) + 1000);

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            InputStream stream = connection.getInputStream();

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);

            NodeList items = document.getElementsByTagName("item");
            List<Article> list = new ArrayList<>(items.getLength());

            for (int i = 0; i < items.getLength(); ++i) {
                Element item = (Element) items.item(i);
                String title = item.getElementsByTagName("title").item(0).getTextContent();
                String link = item.getElementsByTagName("link").item(0).getTextContent();
                String dateString = item.getElementsByTagName("pubDate").item(0).getTextContent();
                Date date = _dateParser.parse(dateString);
                list.add(new Article(title, link, date));
            }

            return list;
        }
        catch (IOException ex) {
            Log.w("DownloadTask", "Exception download " + url + ": " + ex.getMessage());
            return null;
        }
        catch (SAXException | ParserConfigurationException | ParseException ex) {
            Log.w("DownloadTask", "Exception parsing " + url + ": " + ex.getMessage());
            return null;
        }
        catch (InterruptedException ignored) {
            Log.w("DownloadTask", "Interrupted! " + url);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        if (articles != null)
            _adapter.addArticles(articles);
    }
}
