package aivlemsa.infra;

import aivlemsa.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class 작가HateoasProcessor
    implements RepresentationModelProcessor<EntityModel<작가>> {

    @Override
    public EntityModel<작가> process(EntityModel<작가> model) {
        return model;
    }
}
