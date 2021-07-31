package ch.egli.fcbaselbot.data;

import ch.egli.fcbaselbot.model.Crawled;
import ch.egli.fcbaselbot.model.Season;
import java.io.IOException;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public abstract class XmlCrawler {
  public Crawled crawl(String url, Class clazz) {
    Season season = new Season();

    JAXBContext jaxbContext;
    try {
      jaxbContext = JAXBContext.newInstance(clazz);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      var stream = new URL(url).openStream();

      season = (Season) jaxbUnmarshaller.unmarshal(stream);
    } catch (JAXBException | IOException e) {
      e.printStackTrace();
    }

    return season;
  }
}
