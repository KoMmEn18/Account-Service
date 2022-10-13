package account.business;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    @JsonProperty("action")
    private Event event;

    private String subject;

    @NotBlank
    private String object;

    @NotBlank
    private String path;

    public Log(Event event, String subject, String object, String path) {
        this.event = event;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

    @JsonGetter("subject")
    public String getSubject() {
        return subject != null ? subject : "Anonymous";
    }
}
