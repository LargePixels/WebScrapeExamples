package johnnyscrape;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by johnminchuk on 2/20/17.
 */

@Entity
@Data
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 2048)
    private String url;
    private String destination;
    private Boolean downloaded;
    private String namespace;

    @Override
    public String toString() {
        return String.format(
                "Link[id=%d, url='%s', destination='%s'], downloade='%s', namespace='%s'",
                id, url, destination, downloaded, namespace);
    }

}
