package johnnyscrape;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by johnminchuk on 2/20/17.
 */

public interface LinkRepository extends CrudRepository<Link, Long> {

    List<Link> findByUrl(String url);

    List<Link> findByNamespace(String namespace);

    List<Link> findByNamespaceAndDownloaded(String namespace, Boolean downloaded);

}