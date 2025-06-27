package aivlemsa.infra;

import aivlemsa.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class 집필HateoasProcessor
    implements RepresentationModelProcessor<EntityModel<집필>> {

    @Override
    public EntityModel<집필> process(EntityModel<집필> model) {
        return model;
    }
}
