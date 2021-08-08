package ch.egli.fcbaselbot.data;

import ch.egli.fcbaselbot.model.Crawled;
import ch.egli.fcbaselbot.model.Matches;
import ch.egli.fcbaselbot.model.Season;
import ch.egli.util.Crawling;
import ch.egli.util.Properties;

public class LiveCrawler extends XmlCrawler implements Crawling {

  private final static String URL = Properties.LIVE_URL;

  @Override
  public Crawled get() {
    return crawl(URL, Season.class);
  }

  @Override
  public String pretty() {
    return null;
  }
}
