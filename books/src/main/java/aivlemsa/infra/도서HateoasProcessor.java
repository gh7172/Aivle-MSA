package aivlemsa.infra;

import aivlemsa.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class 도서HateoasProcessor
    implements RepresentationModelProcessor<EntityModel<도서>> {

    @Override
    public EntityModel<도서> process(EntityModel<도서> model) {
        return model;
    }
}
