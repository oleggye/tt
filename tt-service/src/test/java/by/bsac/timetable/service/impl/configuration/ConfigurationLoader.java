package by.bsac.timetable.service.impl.configuration;

import by.bsac.timetable.entity.Cancellation;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ConfigurationLoader {

  public static void main(String[] args) throws IOException, URISyntaxException {
    File file = new File("simple_bean.xml");
    final String fileName = "cancellationSetup.xml";

    String xml = "";

    try (Stream<String> stream = Files.lines(getPath(fileName))) {
      xml = stream.reduce("", (a, b) -> a + b);
    }

    XmlMapper xmlMapper = new XmlMapper();

    List<Cancellation> resultList = xmlMapper.readValue(xml, List.class);

    System.out.println("\n------resultList-------");
    System.out.println(resultList);


    /*ArticleXmlReader reader = new ArticleXmlReader();
    reader.read(new File("classpath:setup/cancellationSetup"));*/

  }

  private static Path getPath(String fileName) throws URISyntaxException {
    ClassLoader classLoader = ConfigurationLoader.class.getClassLoader();
    URL resource = classLoader.getResource(fileName);
    return Paths.get(resource.toURI());
  }

}
