package agixis.event.service;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class EventReactiveRestApi {
    @GetMapping(value = "/sreamEvents/{id}", produces =  MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event>listEvents(@PathVariable String id){
        Flux<Long> interval = Flux.interval(Duration.ofMillis(100));
        Flux<Event> events = Flux.fromStream(Stream.generate(()-> {
            Event event = new Event();
            event.setIsntant((Instant.now()));
            event.setSocieteId(id);
            event.setValue((100+ Math.random()*100));
            return event;
        }));
          return Flux.zip(interval,events).map(data-> {
              return data.getT2();
          });

    }
}

class Event{
    private Instant isntant;
    private double value;
    private String societeId;

    public Instant getIsntant() {
        return isntant;
    }

    public void setIsntant(Instant isntant) {
        this.isntant = isntant;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSocieteId() {
        return societeId;
    }

    public void setSocieteId(String societeId) {
        this.societeId = societeId;
    }
}