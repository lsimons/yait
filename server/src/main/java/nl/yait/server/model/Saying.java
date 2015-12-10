package nl.yait.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Saying {
    private long id;

    @Length(max = 3)
    private String content;

    public Saying() {
        // Jackson deserialization
    }

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Saying)) {
            return false;
        }

        Saying saying = (Saying)o;

        if (getId() != saying.getId()) {
            return false;
        }
        return !(getContent() != null ? !getContent().equals(saying.getContent()) : saying.getContent() != null);

    }

    @Override
    public int hashCode() {
        int result = (int)(getId() ^ (getId() >>> 32));
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Saying{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
